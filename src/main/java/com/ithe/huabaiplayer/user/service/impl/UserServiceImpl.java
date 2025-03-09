package com.ithe.huabaiplayer.user.service.impl;


import cn.hutool.core.util.ReUtil;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.constant.RedisKeyConstants;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.model.enums.EmailEnums;
import com.ithe.huabaiplayer.common.model.enums.UserRoleEnum;
import com.ithe.huabaiplayer.common.service.MailService;
import com.ithe.huabaiplayer.common.utils.HuaUtils;
import com.ithe.huabaiplayer.common.utils.VerificationCodeUtil;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.interaction.service.CollectionsService;
import com.ithe.huabaiplayer.user.mapper.UserMapper;
import com.ithe.huabaiplayer.user.model.dto.user.UpdatePasswordRequest;
import com.ithe.huabaiplayer.user.model.dto.user.UserRegisterRequest;
import com.ithe.huabaiplayer.user.model.entity.User;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.ithe.huabaiplayer.user.service.UserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author L
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-10-30 15:37:58
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 盐值，混淆密码
     */

    private static final String SALT = "huabai_player_";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private FileFactory fileFactory;
    @Resource
    private MailService mailService;
    @Resource
    private CollectionsService collectionsService;

    private FileStorage fileService() {
        return fileFactory.getFileService();
    }

    /**
     * 用户注册
     *
     * @return 注册成功返回用户id，否则返回-1
     */
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String username = userRegisterRequest.getUsername();
        String email = userRegisterRequest.getEmail();
        String code = userRegisterRequest.getCode();
        // 不能包含特殊符号的正则表达式
        String regex = "^[a-zA-Z0-9_]+$";
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, email, code)) {
            //
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请填写完全~");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        // 使用 Hu 工具进行正则表达式匹配
        boolean isMatch = ReUtil.isMatch(regex, userAccount);
        if (!isMatch) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号包含特殊符号");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 验证验证码
        if (!checkRegisterCode(email, code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        synchronized (userAccount.intern()) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(User::getUserAccount, userAccount);

            boolean exists = this.exists(queryWrapper);
            if (exists) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            if (username.isBlank()) {
                // 如果用户没有填写用户名，则默认生成一个随机用户名
                // 生成uuid8位作为用户名
                username = "huabai_player_" + java.util.UUID.randomUUID().toString().substring(0, 8);
            }
            // 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUsername(username);
            user.setEmail(email);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            Long userId = user.getId();
            // 注册成功了 然后添加默认收藏夹 todo 后续考虑使用mq发送消息进行添加
            collectionsService.addDefaultCollections(userId);
            return userId;
        }
    }


    @Override
    public UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 不能包含特殊符号和中文的正则表达式
        String regex = "^[a-zA-Z0-9_]+$";
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        // 使用 Hu tool 进行正则表达式匹配
        boolean isMatch = ReUtil.isMatch(regex, userAccount);
        if (!isMatch) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号包含特殊符号");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        User user = this.getOne(query().eq(User::getUserAccount, userAccount).eq(User::getUserPassword, encryptPassword));
        if (user == null) {
            log.info("user login failed, userAccount:{}", userAccount);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        return getUserVO(request, user);
    }

    @Override
    public UserVO userLoginByEmail(String email, String code, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(email, code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请输入");
        }
        if (checkLoginCode(email, code)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(User::getEmail, email);
            User user = this.getOne(queryWrapper);
            if (user == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
            }
            return getUserVO(request, user);
        }
        return null;
    }

    @NotNull
    private UserVO getUserVO(HttpServletRequest request, User user) {
        // 判断用户是否被禁用
        if (user.getUserRole().equals(UserRoleEnum.BAN.getValue()) || user.getUserStatus() == 1) {
            log.info("user login failed, userAccount is disabled");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户被禁用");
        }
        // 脱敏
        UserVO userVO = this.getUserVO(user);
        String avatar = userVO.getUserAvatar();
        if (StringUtils.isNotBlank(avatar)) {
            userVO.setUserAvatar(fileService().getAvatarUrl(avatar));
        }
        Long userVOId = userVO.getId();
        redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + userVOId, userVO, 1, TimeUnit.HOURS);
        // 记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, userVOId);
        return userVO;
    }


    /**
     * 根据给定的用户对象获取对应的UserVO对象
     *
     * @param user 用户对象
     * @return 对应的UserVO对象
     */
    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        return UserVO.objToVo(user);
    }


    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public Long isLogin(HttpServletRequest request) {
        // 从session中获取用户登录状态对象
        Object user = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 将获取到的对象转换为Long类型
        return (Long) user;
    }

    /**
     * 获取登录用户信息
     * <p>
     * 从请求中的session中获取用户登录状态对象，将其转换为UserVO类型后返回。
     * 如果userVO为空或者userVO的id为空，则返回null。
     *
     * @param request HTTP请求对象
     * @return 用户信息的UserVO对象，若用户未登录则返回null
     */
    @Override
    public UserVO getLoginUser(HttpServletRequest request) {
        Long userVOId = isLogin(request);
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(RedisKeyConstants.USER_VO + userVOId);
        if (userVO == null) {
            User byId = this.getById(userVOId);
            if (byId == null) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户不存在");
            }
            userVO = this.getUserVO(byId);
            if (userVO.getUserStatus() == 1) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户已被禁用");
            }
            String avatar = userVO.getUserAvatar();
            if (StringUtils.isNotBlank(avatar)) {
                userVO.setUserAvatar(fileService().getAvatarUrl(avatar));
            }
//            redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + userVOId, userVO, 1, TimeUnit.HOURS);
        }
        if (userVO.getUserStatus() == 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户已被禁用");
        }
        // 更新对应的userVO对象时间
        redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + userVOId, userVO, 1, TimeUnit.HOURS);
        // 返回userVO对象
        return userVO;
    }


    /**
     * 用户退出
     *
     * @param request HTTP请求对象
     * @return
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 如果登录状态存在
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) != null) {
            // 移除登录状态属性
            request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
            return true;
        }
        throw new BusinessException(ErrorCode.OPERATION_ERROR, "退出失败");
    }

    /**
     * 更新用户信息
     *
     * @return true：更新成功，false：更新失败  非管理员不能修改状态和角色
     */
    @Override
    public Boolean updateUser(UserVO userVO, HttpServletRequest request) {
        Long userId = userVO.getId();

        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id不能为空");
        }
        userVO.setUserRole(null);
        userVO.setUserStatus(null);
        // 判断登录用户修改的用户信息是否是本人或者登录用户是管理员,也可以管理员和用户修改接口分开写
        if (userId.equals(this.isLogin(request))) {
            //查询数据库是否存在该用户
            User byId = this.getById(userVO.getId());
            if (byId == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
            }
            // 更新用户信息
            BeanUtils.copyProperties(userVO, byId);
            boolean b = this.updateById(byId);
            User user = this.getById(userId);
            UserVO userVO1 = this.getUserVO(user);
            String avatar = userVO1.getUserAvatar();
            if (StringUtils.isNotBlank(avatar)) {
                userVO1.setUserAvatar(fileService().getAvatarUrl(avatar));
            }
            redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + userId, userVO1, 1, TimeUnit.HOURS);
            return b;
        } else {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
    }

    @Override
    public Boolean updateUserAdmin(UserVO userVO, HttpServletRequest request) {
        Long userId = userVO.getId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id不能为空");
        }
        User byId = this.getById(userVO.getId());
        if (byId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        User user = UserVO.objToVo(userVO);
        boolean b = this.updateById(user);
        if (!b) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败");
        }
        redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + userId, this.getUserVO(this.getById(userId)), 1, TimeUnit.HOURS);
        return true;
    }


    /**
     * 判断用户是否是管理员
     *
     * @param request HTTP请求对象
     * @return 如果用户角色等于 UserConstant.ADMIN_ROLE，则返回 true，否则返回 false
     */
    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        UserVO userVO = (UserVO) userObj;
        return userVO != null && UserConstant.ADMIN_ROLE.equals(userVO.getUserRole());
    }

    /**
     * 传入登录用户信息，判断用户是否是管理员
     *
     * @return true：是管理员，false：不是管理员
     */
    @Override
    public Boolean isAdmin(UserVO userVO) {
        return userVO != null && UserConstant.ADMIN_ROLE.equals(userVO.getUserRole());
    }

    @Override
    public Boolean isAdmin(Long userVOId) {
        return userVOId != null && UserConstant.ADMIN_ROLE.equals(Objects.requireNonNull((UserVO) redisTemplate.opsForValue().get(RedisKeyConstants.USER_VO + userVOId)).getUserRole());
    }

    @Override
    public boolean updateAvatar(HttpServletRequest request, String findUrl) {
        Long id = this.isLogin(request);
        // 判断登录用户修改的用户信息是否是本人或者登录用户是管理员,也可以管理员和用户修改接口分开写
        //查询数据库是否存在该用户
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        user.setUserAvatar(findUrl);
        boolean b = this.updateById(user);
        if (b) {
            redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + id, this.getUserVO(user), 1, TimeUnit.HOURS);
        }
        return b;
    }


    /**
     * 用户上传/更新头像
     *
     * @return url
     */
    @Override
    public String uploadFile(MultipartFile multipartFile, UserVO user) {
        String fileSuffix = HuaUtils.validPictureFile(multipartFile);
        String avatar = user.getUserAvatar();
        if (StringUtils.isNotBlank(avatar)) {
            // 删除之前的头像
            String origin = fileService().getOrigin(avatar);
            if (StringUtils.isNotBlank(origin)) {
                try {
                    fileService().deleteAvatar(origin);
                } catch (Exception e) {
                    log.error("在更新头像的时候删除头像失败了~", e);
                }
            }
        }
        // 获取当前6位时间戳
        String timestamp = HuaUtils.generateTimestamp();
        String fileName = timestamp + "." + fileSuffix;
        String username = user.getUsername();
        Long id = user.getId();
        // 获取用户名称第一个的字母
        String pinYin = HuaUtils.getFirstPinYin(username);
        String avatarNew = pinYin + "/" + id + "/" + fileName;
        updateChain().set(User::getUserAvatar, avatarNew)
                .eq(User::getId, id).update();
        try {
            fileService().uploadAvatar(avatarNew, multipartFile);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传头像失败");
        }
        String url = fileService().getAvatarUrl(avatarNew);
        user.setUserAvatar(url);
        redisTemplate.opsForValue().set(RedisKeyConstants.USER_VO + id, user, 1, TimeUnit.HOURS);
        return url;
    }

    @Override
    public Boolean sendRegistrationEmail(String to) {
        if (StringUtils.isBlank(to)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱不能为空");
        }
        // 查看邮箱是否已经被注册
        if (queryChain().eq(User::getEmail, to).exists()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱已被注册");
        }
        try {
            String codeGen = VerificationCodeUtil.generateVerificationCode();
            mailService.sendHtmlMailMessage(to, EmailEnums.REGISTER.getSubject(),
                    EmailEnums.REGISTER.getTemplate().replace("${code}", codeGen));
            redisTemplate.opsForValue().set(RedisKeyConstants.REGISTRATION_CODE_PREFIX + to, codeGen, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "邮件发送失败,请重试~");
        }
        return true;
    }

    @Override
    public Boolean sendLoginEmail(String to) {
        if (StringUtils.isBlank(to)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱不能为空");
        }
        // 查看邮箱是否存在
        if (!queryChain().eq(User::getEmail, to).exists()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱不存在，请注册~");
        }
        try {
            String codeGen = VerificationCodeUtil.generateVerificationCode();
            mailService.sendHtmlMailMessage(to, EmailEnums.LOGIN.getSubject(),
                    EmailEnums.LOGIN.getTemplate().replace("${code}", codeGen));
            redisTemplate.opsForValue().set(RedisKeyConstants.LOGIN_CODE_PREFIX + to, codeGen, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "邮件发送失败,请重试~");
        }
        return true;
    }

    @Override
    public Boolean sendUpdateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱不能为空");
        }
        // 查看邮箱是否存在
        if (!queryChain().eq(User::getEmail, email).exists()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱不存在，请注册~");
        }
        try {
            String codeGen = VerificationCodeUtil.generateVerificationCode();
            mailService.sendHtmlMailMessage(email, EmailEnums.RESET_PASSWORD.getSubject(),
                    EmailEnums.RESET_PASSWORD.getTemplate().replace("${code}", codeGen));
            redisTemplate.opsForValue().set(RedisKeyConstants.RESET_PASSWORD_CODE_PREFIX + email, codeGen, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "邮件发送失败,请重试~");
        }
        return true;
    }

    @Override
    public Boolean updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        String userPassword = updatePasswordRequest.getUserPassword();
        String checkPassword = updatePasswordRequest.getCheckPassword();
        String email = updatePasswordRequest.getEmail();
        String code = updatePasswordRequest.getCode();
        if (StringUtils.isAnyBlank(userPassword, checkPassword, email, code)) {
            //
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请填写完全~");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }
        if (checkUpdateCode(email, code)) {
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            updateChain().set(User::getUserPassword, encryptPassword)
                    .eq(User::getEmail, email).update();
            return true;
        }
        return false;
    }

    // 校验注册验证码
    private boolean checkRegisterCode(String to, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get(RedisKeyConstants.REGISTRATION_CODE_PREFIX + to);
        if (StringUtils.isBlank(redisCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码已过期");
        }
        if (!redisCode.equals(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        // 删除redis
        redisTemplate.delete(RedisKeyConstants.REGISTRATION_CODE_PREFIX + to);
        return true;
    }

    // 校验登录验证码
    private boolean checkLoginCode(String to, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get(RedisKeyConstants.LOGIN_CODE_PREFIX + to);
        if (StringUtils.isBlank(redisCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码已过期");
        }
        if (!redisCode.equals(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        redisTemplate.delete(RedisKeyConstants.LOGIN_CODE_PREFIX + to);
        return true;
    }

    // 校验修改密码验证码
    private boolean checkUpdateCode(String to, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get(RedisKeyConstants.RESET_PASSWORD_CODE_PREFIX + to);
        if (StringUtils.isBlank(redisCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码已过期");
        }
        if (!redisCode.equals(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        redisTemplate.delete(RedisKeyConstants.RESET_PASSWORD_CODE_PREFIX + to);
        return true;
    }
}









