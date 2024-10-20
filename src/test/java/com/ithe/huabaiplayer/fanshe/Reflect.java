package com.ithe.huabaiplayer.fanshe;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @ClassName Reflect
 * @Author hua bai
 * @Date 2024/10/9 16:14
 **/
public class Reflect {
    // 获取到类的Class 实例 和 实例化
    @Test
    public void test1() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<Fen> fenClass = Fen.class;
//        Fen fen = new Fen();
//        Class<? extends Fen> aClass = fen.getClass();
//        var a = new Fen();
//        System.out.println(a instanceof Object);
        try {
            Class<?> aClass1 = Class.forName("com.ithe.huabaiplayer.fanshe.Fen");
            Constructor<?> declaredConstructor = aClass1.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            if (o instanceof Fen) {
                System.out.println("true");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(fenClass.getName());
        System.out.println(fenClass.getClassLoader());
        System.out.println(fenClass.getClass());
        System.out.println(fenClass.getSimpleName());
        System.out.println(Arrays.toString(fenClass.getDeclaredFields()));
        Constructor<Fen> constructor = fenClass.getDeclaredConstructor(int.class, String.class);
        constructor.setAccessible(true);
        Fen fen = constructor.newInstance(1, "huabai");
        System.out.println(fen);
    }

    // 访问字段 设置与获取
    @Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        Fen fen = new Fen();
        Class<? extends Fen> fenClass = fen.getClass();
        Field age = fenClass.getDeclaredField("age");
        age.setAccessible(true);
        age.set(fen, 2);
        var a = age.get(fen);
        System.out.println(fen);
        System.out.println(a);
    }

    // 调用方法 和使用调用的方法
    @Test
    public void test3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Fen fen = new Fen();
        Class<? extends Fen> fenClass = fen.getClass();
        Method[] declaredMethods = fenClass.getDeclaredMethods();
        Method instance = fenClass.getDeclaredMethod("getInstance", int.class, String.class);
        instance.setAccessible(true);
        Object invoke = instance.invoke(null, 1, "2");
        System.out.println(invoke);
    }

    @Test
    public void test4() {
        InvocationHandler handler = (proxy, method, args) -> {
            System.out.println(method);
            if (method.getName().equals("say")) {
                System.out.println("Good morning, " + args[0]);
            }
            return null;
        };
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[]{Hello.class}, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler
        hello.say("Bob");
    }

    interface Hello {
        void say(String name);
    }
}
