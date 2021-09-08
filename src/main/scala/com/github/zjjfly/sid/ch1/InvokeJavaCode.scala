package com.github.zjjfly.sid.ch1

/**
 * @author zjjfly
 */
object InvokeJavaCode extends App {
  //Scala调用Java,一般可以直接调用
  val x: SimpleJavaClass = SimpleJavaClass.create("jjzi")
  assert("jjzi" == x.getName)
  val y = new SimpleJavaClass("zjj")
}
