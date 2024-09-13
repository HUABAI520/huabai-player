package com.ithe.huabaiplayer.common.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Optional;

public class SpElUtils {

    // SpEl表达式解析器
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    // 参数名发现器
    private static final DefaultParameterNameDiscoverer DISCOVERER = new DefaultParameterNameDiscoverer();


    public static String getMethodKey(Method method) {
        return method.getDeclaringClass() + "#" + method.getName();
    }

    /**
     * 解析SpEl表达式
     *
     * @param method 方法
     * @param args   入参参数值
     * @param spEl   SpEl表达式
     * @return 解析后的字符串
     */
    public static String parseSpEl(Method method, Object[] args, String spEl) {
        String[] params = Optional.ofNullable(DISCOVERER.getParameterNames(method)).orElse(new String[]{});// 获取方法的参数名
        EvaluationContext context = new StandardEvaluationContext();// 创建SpEL表达式的上下文
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]); // 把方法的参数名和参数值放入上下文
        }
        //解析SpEl表达式
        return PARSER.parseExpression(spEl).getValue(context, String.class);
    }

/*    public static void main(String[] args) {
        EvaluationContext context = new StandardEvaluationContext();// 创建SpEL表达式的上下文
        context.setVariable("huabai", 123456);
        Expression expression = PARSER.parseExpression("#huabai");
        int a = (int) expression.getValue(context);
        System.out.println(a);
    }*/

}
