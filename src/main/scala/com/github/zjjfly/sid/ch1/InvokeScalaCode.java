package com.github.zjjfly.sid.ch1;

/**
 * @author zjjfly
 */
public class InvokeScalaCode {
    public static void main(String[] args) {
        //Java调用Scala
        //调用scala的object中的方法
        ScalaUtils.log("Hello");
        //object会被编译成一个单例,其中有一个静态成员MODULE$指向这个唯一实例
        ScalaUtils$.MODULE$.log("World");
        //object中的变量会被编译为方法
        System.out.println(ScalaUtils.MAX_LOG_SIZE());
        System.out.println(ScalaUtils$.MODULE$.MAX_LOG_SIZE());
        //调用需要函数作为参数的Scala方法
        System.out.println(FunctionUtils.testFunction(v1 -> (Integer) v1 + 1));
    }
}
