package com.ithe.huabaiplayer.common.utils;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @ClassName BeanHuaUtils
 * @Author he long
 * @Date 2024/7/30 11:19
 * @Version 1.0
 **/
@Slf4j
public class BeanHuaUtil extends BeanUtils {
  /**
   * 获取某个类的所有字段
   */
  public static List<String> getAllProperties(Class<?> clazz) {
    return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList();
  }

  /**
   * 获取某个对象的所有字段
   */
  public static List<String> getAllProperties(Object obj) {
    return Arrays.stream(obj.getClass().getDeclaredFields()).map(Field::getName).toList();
  }

  public static String getListCode(Object o) {
    List<String> properties = getAllProperties(o);
    return "public static final List<String> NOTICE_FIELDS =  Arrays.asList("
        + properties.stream().map(s -> "'" + s + "'").toList() + ")";
  }

  public static String getListCode(Class<?> clazz) {
    List<String> properties = getAllProperties(clazz);
    return getCode(properties);
  }

  public static String getCode(List<String> properties) {
    StringJoiner sj = new StringJoiner(", ");
    for (String property : properties) {
      sj.add("\"" + property + "\"");
    }
    return "public static final List<String> NOTICE_FIELDS = Arrays.asList("
        + sj
        + ");";

  }

  /**
   * <p>复制对象并指定需要替换的字段</p>
   *
   * @param source             原对象数据
   * @param toClass            目标对象的类 .Class
   * @param containsProperties 需要替换的字段,若为空的话,则全部替换
   */
  public static <T> T copyNoticeFields(Object source, Class<T> toClass,
                                       List<String> containsProperties) {
    if (null == source) {
      return null;
    } else {
      try {
        T target = toClass.getDeclaredConstructor().newInstance();
        copyNoticeFields(source, target, containsProperties);
        return target;
      } catch (Exception e) {
        log.error("BeanHuaUtils copyHavenValue error", e);
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * <p>复制对象并指定需要替换的字段,不指定全部替换</p>
   *
   * @param source  原对象数据
   * @param toClass 目标对象.Class
   */
  public static <T> T copyNoticeFields(Object source, Class<T> toClass) {
    if (null == source) {
      return null;
    } else {
      try {
        T target = toClass.getDeclaredConstructor().newInstance();
        copyNoticeFields(source, target);
        return target;
      } catch (Exception e) {
        log.error("BeanHuaUtils copyHavenValue error", e);
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * <p>复制对象并指定需要替换的字段</p>
   *
   * @param source             原对象数据
   * @param target             目标对象的类 .Class
   * @param containsProperties 需要替换的字段,若为空的话,则全部替换
   */
  public static void copyNoticeFields(Object source, Object target,
                                      @NotNull List<String> containsProperties)
      throws BeansException {
    // containsProperties 为空全部替换
    if (containsProperties.isEmpty()) {
      copyProperties(source, target);
      return;
    }
    // 获取target 对象 的所有字段字符串
    List<String> allProperties = Arrays.stream(target.getClass().getDeclaredFields())
        .map(Field::getName)
        .toList();

    // 然后过滤掉containsProperties 收集为ignoreProperties
    List<String> list = allProperties.stream().filter(s -> !containsProperties.contains(s))
        .toList();
    String[] ignoreProperties = list.toArray(new String[0]);
    copyProperties(source, target, ignoreProperties);
  }

  /**
   * <p>复制对象并指定需要替换的字段,不指定全部替换</p>
   *
   * @param source 原对象数据
   * @param target 目标对象
   */
  public static void copyNoticeFields(Object source, Object target)
      throws BeansException {
    copyProperties(source, target);
  }

  public static void copyMultiNoticesFields(List<Object> source, Object target,
                                            @NotNull List<String> containsProperties) {
    source.forEach(s -> copyNoticeFields(s, target, containsProperties));
  }

  /**
   * <p>复制对象并指定被忽略的字段,不指定全部替换</p>
   *
   * @param source 原对象数据列表
   * @param target 目标对象
   */
  public static void copyMultiIgnoreFields(List<Object> source, Object target) {
    source.forEach(s -> copyIgnoreFields(s, target));
  }

  /**
   * <p>复制对象并指定被忽略的字段</p>
   *
   * @param source  原对象数据
   * @param toClass 目标对象数据
   * @param ignores 需要替换的字段,若为空的话,则全部替换
   */
  public static <T> T copyIgnoreFields(Object source, Class<T> toClass, List<String> ignores) {
    if (null == source) {
      return null;
    } else {
      try {
        T target = toClass.getDeclaredConstructor().newInstance();
        copyIgnoreFields(source, target, ignores);
        return target;
      } catch (Exception e) {
        log.error("BeanHuaUtils copyHavenValue error", e);
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * <p>复制对象并指定被忽略的字段,不指定全部替换</p>
   *
   * @param source  原对象数据
   * @param toClass 目标对象.Class
   */
  public static <T> T copyIgnoreFields(Object source, Class<T> toClass) {
    if (null == source) {
      return null;
    } else {
      try {
        T target = toClass.getDeclaredConstructor().newInstance();
        copyIgnoreFields(source, target);
        return target;
      } catch (Exception e) {
        log.error("BeanHuaUtils copyHavenValue error", e);
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * <p>复制对象并指定被忽略的字段</p>
   *
   * @param source  原对象数据
   * @param target  目标对象数据
   * @param ignores 需要替换的字段,若为空的话,则全部替换
   */
  public static void copyIgnoreFields(Object source, Object target, List<String> ignores)
      throws BeansException {
    if (ignores.isEmpty()) {
      copyProperties(source, target);
    }
    String[] ignoreProperties = ignores.toArray(new String[0]);
    copyProperties(source, target, ignoreProperties);
  }

  /**
   * <p>复制对象并指定被忽略的字段,不指定全部替换</p>
   *
   * @param source 原对象数据
   * @param target 目标对象数据
   */
  public static void copyIgnoreFields(Object source, Object target)
      throws BeansException {
    copyProperties(source, target);
  }
}
