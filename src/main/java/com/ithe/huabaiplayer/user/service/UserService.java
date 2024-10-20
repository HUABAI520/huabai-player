package com.ithe.huabaiplayer.user.service;



import com.ithe.huabaiplayer.user.model.dto.user.UserRegisterRequest;
import com.ithe.huabaiplayer.user.model.entity.User;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author L
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-10-30 15:37:58
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @return 返回用户id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userAccount  账户
     * @param userPassword 密码
     * @return 脱敏后的用户信息
     */
    UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据User对象获取UserVO对象
     *
     * @param user 用户对象
     * @return UserVO对象
     */
    UserVO getUserVO(User user);


    List<UserVO> getUserVO(List<User> userList);

    Long isLogin(HttpServletRequest request);

    /**
     * 获取登录用户信息
     *
     * @param request HTTP请求对象
     * @return 登录用户对象
     */
    UserVO getLoginUser(HttpServletRequest request);

    /**
     * 用户登出方法
     *
     * @param request HTTP请求对象
     * @return 如果用户成功登出返回true，否则返回false
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 更新用户信息
     *
     * @param userVO
     * @param request
     * @return
     */
    Boolean updateUser(UserVO userVO, HttpServletRequest request);

    /**
     * 判断是否是管理员，如果是管理员返回true，否则返回false
     *
     * @param request
     * @return
     */
    Boolean isAdmin(HttpServletRequest request);

    /**
     * 判断是否是管理员，如果是管理员返回true，否则返回false
     *
     * @param userVO
     * @return
     */
    Boolean isAdmin(UserVO userVO);

    Boolean isAdmin(Long userVOId);

    boolean updateAvatar(HttpServletRequest request, String filepath);

    Boolean updateUserAdmin(UserVO userVO, HttpServletRequest request);

    String uploadFile(MultipartFile multipartFile, UserVO user);
}
