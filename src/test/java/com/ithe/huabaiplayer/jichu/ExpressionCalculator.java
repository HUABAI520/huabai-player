package com.ithe.huabaiplayer.jichu;

import java.util.Stack;

public class ExpressionCalculator {
    public static int calculate(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            if (Character.isSpace(expression.charAt(i))) {
                i++;
                continue;
            }
            if (Character.isDigit(expression.charAt(i))) {
                int j = i;
                while (j < expression.length() && Character.isDigit(expression.charAt(j))) {
                    j++;
                }
                int value = Integer.parseInt(expression.substring(i, j));
                values.push(value);
                i = j;
            } else if (expression.charAt(i) == '(') {
                ops.push(expression.charAt(i));
                i++;
            } else if (expression.charAt(i) == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    int val2 = values.pop();
                    int val1 = values.pop();
                    char op = ops.pop();
                    values.push(applyOp(op, val1, val2));
                }
                ops.pop();
                i++;
            } else {
                while (!ops.isEmpty() && hasPrecedence(ops.peek(), expression.charAt(i))) {
                    int val2 = values.pop();
                    int val1 = values.pop();
                    char op = ops.pop();
                    values.push(applyOp(op, val1, val2));
                }
                ops.push(expression.charAt(i));
                i++;
            }
        }

        while (!ops.isEmpty()) {
            int val2 = values.pop();
            int val1 = values.pop();
            char op = ops.pop();
            values.push(applyOp(op, val1, val2));
        }

        return values.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    private static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }

    public static void main(String[] args) {
        String expression = "3 + 5 * 2 / ( 1 - 4 ) ^ 2";
        expression = expression.replaceAll("\\^", "**"); // Replace caret with double asterisk for exponentiation
        int result = calculate(expression);
        System.out.println("Result: " + result);
    }
}