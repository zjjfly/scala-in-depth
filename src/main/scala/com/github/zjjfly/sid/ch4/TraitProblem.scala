package com.github.zjjfly.sid.ch4

/**
  * trait虽然允许在其中写具体实现,但这样做会引发一些问题
  * @author zjjfly
  */
trait Foo {
  def someMethod(): Int = 5
  //这个方法是后来加入的,但在Scala中你不需要重新编译混入了这个trait的类的代码
  //但当你在另一个类中使用这些类,并调用这个新方法的时候,虽然编译没有问题,但在运行时会抛出异常
  //出现的原因是JVM允许类没有完全实现接口的方法都可以进行link
  def newMethod() = "HI"
}

class Main extends Foo {}

object ScalaMain extends App {
  val foo: Foo = new Main
  println(foo.someMethod())
  //如果没有重新编译Main,下面的代码会报错
  println(foo.newMethod())
  //防止出错的办法是使用反射
  val clazz = Class.forName("com.github.zjjfly.sid.ch4.Foo")
  val method = clazz.getMethod("newMethod")
  println(method.invoke(foo))
}
