package com.example.calculator.model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class BaseCalculator {

    private class Ele {
        public boolean isNum;
        public double o;
        public char op;
        public Ele(double o,boolean isNum){
            this.isNum=isNum;
            this.o=o;
        }

        public Ele(char op,boolean isNum) {
            this.isNum = isNum;
            this.op = op;
        }
    }

    //Map结构方便后面取运算符的优先级
    private final Map<Character, Integer> operMap = new HashMap<Character, Integer>() {{
        put('+', 0);
        put('-', 0);
        put('×', 1);
        put('/', 1);
        put('(', 3);
        put(')', 3);
        put('E', 2);
    }};

    //简单四则运算
    private double operate(double a, char oper, double b) {
        switch (oper) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '×':
                return a * b;
            case '/':
                if (b == 0) {
                    return Double.MAX_VALUE; //处理异常
                }
                return a / b;
            case 'E':
                return Double.parseDouble(String.valueOf(a+"E"+(int)b));
            default:
                return 0;
        }
    }

    public double cal(String math) {
        if (math.length() == 0) {
            return Double.MAX_VALUE;
        } else {
            Stack<Character> operStack = new Stack<>(); //oper栈
            Queue<Ele> eleQueue = new LinkedList<>();
            Stack<Double> numStack = new Stack<>();

            //中缀变后缀
            int firstIndex = 0;
            int i=0;
            while (i < math.length()) {
                char charOfMath = math.charAt(i);
                if (charOfMath == '(') {
                    operStack.push(charOfMath);
                    i++;
                } else if (charOfMath == ')') {
                    while (operStack.peek() != '(') {
                        eleQueue.offer(new Ele(operStack.pop(), false));
                    }
                    operStack.pop();
                    i++;
                }else if(isNum(charOfMath)){
                    firstIndex=i;
                    while (++i<math.length()&&isNum(math.charAt(i)));
                    double num;
                    try {
                        num = Double.parseDouble(math.substring(firstIndex, i));
                    } catch (NumberFormatException e) {
                        return Double.MAX_VALUE;
                    }
                    eleQueue.offer(new Ele(num,true));
                }else{
                    //说明-是单目运算符
                    if (charOfMath == '-') {
                        if (i == 0) {
                            firstIndex=0;
                            while (++i<math.length()&&isNum(math.charAt(i)));
                            double num;
                            try {
                                num = Double.parseDouble(math.substring(firstIndex, i));
                            } catch (NumberFormatException e) {
                                return Double.MAX_VALUE;
                            }
                            eleQueue.offer(new Ele(num,true));
                            continue;
                        } else if (math.charAt(i - 1)=='(') {
                            //表示这是一个单目运算符
                            firstIndex=i;
                            while (++i<math.length()&&isNum(math.charAt(i)));
                            double num;
                            try {
                                num = Double.parseDouble(math.substring(firstIndex, i));
                            } catch (NumberFormatException e) {
                                return Double.MAX_VALUE;
                            }
                            eleQueue.offer(new Ele(num,true));
                            continue;
                        }


                    }
                    while (operStack.size() >= 0) {
                        if (operStack.size() == 0 || operStack.peek() == '(') {
                            operStack.push(charOfMath);
                            break;
                        } else {
                            if (operMap.get(operStack.peek()) < operMap.get(charOfMath)) {
                                operStack.push(charOfMath);
                                break;
                            } else {
                                eleQueue.offer(new Ele(operStack.pop(), false));
                            }
                        }
                    }
                    i++;
                }
            }

            while (operStack.size()>0){
                eleQueue.offer(new Ele(operStack.pop(),false));
            }
            //检查后缀并运算
            while (eleQueue.size() > 0) {
                if (eleQueue.element().isNum) {
                    numStack.push(eleQueue.poll().o);
                } else {
                    char op = eleQueue.poll().op;
                    double second= numStack.pop();
                    double first = numStack.pop();
                    numStack.push(operate(first, op, second));
                }
            }
            return numStack.peek();
        }
    }

    private boolean isNum(char c){
        return (c-'0'>=0&&c-'0'<=9)||c=='.';
    }
}
