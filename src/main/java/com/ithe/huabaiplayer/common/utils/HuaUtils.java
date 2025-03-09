package com.ithe.huabaiplayer.common.utils;

import cn.hutool.core.io.FileUtil;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author L
 * @description 花白定义工具类
 */
@Slf4j
public class HuaUtils {


    /**
     * 转换list对象工具
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> convertList(List<T> sourceList, Class<R> targetClass) {
        return sourceList.stream()
                .map(source -> {
                    try {
                        R target = targetClass.getDeclaredConstructor().newInstance();
                        BeanUtils.copyProperties(source, target);
                        return target;
                    } catch (Exception e) {
                        throw new RuntimeException("Error converting object: " + e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }


    public static String validPictureFile(MultipartFile multipartFile) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        // 如果后缀为空 获取文件类型
        final long oNEM = 1024 * 1024 * 10L;

        if (fileSize > oNEM) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 10M");
        }
        if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }

        return fileSuffix;
    }

    public static String validPictureName(String name) {
        // 文件后缀
        String fileSuffix = name.split("\\.")[1];
        if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }
        return fileSuffix;
    }

    public static String validVideoName(String fileSuffix) {
        Set<String> validSuffixes = Set.of(
                ".mp4", ".avi", ".mov", ".wmv", ".flv", ".mkv",
                ".3gp", ".m4v", ".webm", ".mpeg", ".mpg", ".mpe",
                ".ts", ".vob", ".f4v", ".asf", ".divx", ".xvid",
                ".m3u8"
        );
        // 文件后缀
        if (!validSuffixes.contains(fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }
        return fileSuffix;
    }

    /**
     * 判断是否有更新字段工具
     *
     * @param t 更新对象
     * @return
     */
    public static <T> boolean hasNonEmptyFields(T t) {
        int i = 0;
        for (Field field : t.getClass().getDeclaredFields()) {
            i++;// 跳过 id 字段
            field.setAccessible(true);
            try {
                if (i != 1 && !"serialVersionUID".equals(field.getName())) {
                    Object value = field.get(t);
                    if (value != null && !"".equals(value.toString())) {
                        // 如果至少一个字段不为空，表示有更新字段
                        return true;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 如果所有字段（除了 id 外）都为空，表示没有更新字段
        return false;
    }

    /**
     * 计算当前时间到下午12点的持续时间工具的秒数
     *
     * @ return
     */
    public static Duration calculateTimeToTwelvePmBySecond() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHH");
        // 将当前时间格式化为字符串
        String formattedTime = now.format(formatter);
        // 当天的午夜时间
        LocalDateTime midnight = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
        // 计算与当前时间的持续时间
        return Duration.between(now, midnight);
    }

    /**
     * @param type      实体类的名称(全小写，自动首字母大写）
     * @param className 例如："com.hz.computer.user.model.vo.entity." + entityClassName
     * @return Class 对象
     */
    public static <T> Class<T> getEntityClass(String type, String className) {
        //  type 的值是实体类的名称，首字母大写
        String entityClassName = type.substring(0, 1).toUpperCase() + type.substring(1);
        try {
            // 尝试通过反射获取 Class 对象
            return (Class<T>) Class.forName(className + entityClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found for type: " + type, e);
        }
    }

    /**
     * 获取对象的字段值
     *
     * @param object    待获取字段值的对象
     * @param fieldName 字段名
     * @param fieldType 字段类型
     * @param <T>       对象类型参数
     * @param <F>       字段类型参数
     * @return 字段的值
     */
    public static <T, F> F getField(T object, String fieldName, Class<F> fieldType) {
        try {
            Class<?> clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true); // 设置字段可访问
            return fieldType.cast(field.get(object));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // e.printStackTrace();
            log.info(object + "获取" + fieldName + "字段失败");
            return null;
        }
    }

    /**
     * 设置对象的字段值
     *
     * @param object    要设置字段的对象
     * @param fieldName 要设置的字段名
     * @param value     要设置的字段值
     * @param <T>       对象的类型参数
     * @param <F>       字段值的类型参数
     */
    public static <T, F> void setField(T object, String fieldName, F value) {
        try {
            Class<?> clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true); // 设置字段可访问
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 处理异常
            log.error(object + "设置" + fieldName + "字段值失败", e);
        }
    }


    /**
     * 中文转拼音（先去除空格和特殊字符，再转拼音）条码生成
     *
     * @param commodityName 商品类型
     * @param model         商品型号
     * @return 条码
     */
    public static String toPinYinBarcode(String commodityName, String model) {
        String cleanText = cleanText(model);
        String string = convertToPinyin(cleanText);
        // 当条码长度超过 7 个字符，需要压缩  每一翻倍一次 压缩的倍数+1
        int c = 1;
        c += string.length() / 7;
        string = compress(string, c);
        return commodityName + "-" + string;
    }

    public static String compress(String input, int c) {
        StringBuilder compressed = new StringBuilder();
        for (int i = 0; i < input.length(); i += c) {
            compressed.append(input.charAt(i));
        }
        return compressed.toString();
    }

    public static String cleanText(String text) {
        return text.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", ""); // 保留中文、英文和数字字符
    }

    public static String convertToPinyin(String chineseText) {
        StringBuilder pinyinBuilder = new StringBuilder();
        char[] chars = chineseText.toCharArray();

        for (char c : chars) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null) {
                pinyinBuilder.append(pinyinArray[0].charAt(0)); // 选择第一个拼音的首字母
            } else {
                pinyinBuilder.append(c); // 如果字符不是中文，则直接添加原字符
            }
        }

        return pinyinBuilder.toString().toUpperCase();
    }

    // 获取首个字的拼音的第一个字母且大写
    public static String getFirstPinYin(String chineseText) {
        StringBuilder pinyinBuilder = new StringBuilder();
        char[] chars = chineseText.toCharArray();

        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(chars[0]);
        if (pinyinArray != null) {
            pinyinBuilder.append(pinyinArray[0].charAt(0)); // 选择第一个拼音的首字母
        } else {
            pinyinBuilder.append(chars[0]);
        }
        return pinyinBuilder.toString().toUpperCase();
    }

    /**
     * 字符串转化为Base64
     */
    public static String toBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    /**
     * 二进制文件转化为Base64
     */
    public static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64字符串解码为字符串
     */
    public static String fromBase64String(String string) {
        return new String(Base64.getDecoder().decode(string));
    }

    /**
     * Base64字符串解码为二进制文件
     */
    public static byte[] fromBase642Bytes(String string) {
        return Base64.getDecoder().decode(string);
    }

    /**
     * 生成6位的时间戳
     */
    public static String generateTimestamp() {
        // 生成6位时间戳
        long timestamp = System.currentTimeMillis() % 1000000;
        return String.format("%06d", timestamp);
    }

    /**
     * 检验上传的文件是否满足Excel格式
     *
     * @param file 文件
     * @return 是否满足
     */
    public static boolean validFileIsExcel(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx");
    }

    public static String reBuilderPath(String fullPath, String name) {
        // 分隔 fullPath 并替换最后一个元素为 name
        String[] parts = fullPath.split("/");
        if (parts.length > 0) {
            parts[parts.length - 1] = name;
        }
        // 重新组装路径
        return String.join("/", parts);
    }

    /**
     * 获取最大的位数
     */
    public static int getRadix(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            max = Math.max(max, num);
        }
        int radix = 0;
        while (max > 0) {
            radix++;
            max /= 10;
        }
        return radix;
    }

    /**
     * 获取该数在第几位的值
     */
    public static int getRadix(int num, int radix) {
        int result = 0;
        for (int i = 1; i <= radix; i++) {
            result = num % (10);
            num = num / 10;
        }
        return result;
    }

    /**
     * 基数排序优化版 （数组）
     *
     * @param arr 数组
     */
    public static void radixSort(int[] arr) {
        radixSort(arr, getRadix(arr), true);
    }

    public static void radixSort(int[] arr, boolean isAsc) {
        radixSort(arr, getRadix(arr), isAsc);
    }

    /**
     * 基数排序优化版 （数组）
     *
     * @param arr   数组
     * @param radix 最大数的深度
     */
    public static void radixSort(int[] arr, int radix, boolean isAsc) {
        int len = arr.length;
        final int base = 10;
        int[] bucket = new int[len];
        for (int i = 1; i <= radix; i++) {
            int[] count = new int[base];
            for (int a : arr) {
                count[getRadix(a, i)]++;
            }
            inRadix(isAsc, base, count);
            // 放入辅助桶 通过对应位数的排序
            for (int j = len - 1; j >= 0; j--) {
                int x = getRadix(arr[j], i);
                bucket[count[x] - 1] = arr[j];
                count[x]--;
            }
            System.arraycopy(bucket, 0, arr, 0, arr.length);
        }
    }

    private static void inRadix(boolean isAsc, int base, int[] count) {
        if (isAsc) {
            // 求前缀和
            for (int j = 1; j < base; j++) {
                count[j] += count[j - 1];
            }
        } else {
            // 求后缀和
            for (int j = base - 2; j >= 0; j--) {
                count[j] += count[j + 1];
            }
        }
    }
}
