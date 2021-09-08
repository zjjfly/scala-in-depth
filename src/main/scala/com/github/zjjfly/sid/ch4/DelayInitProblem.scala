package com.github.zjjfly.sid.ch4

//混入DelayInit特质的类的构造器代码会被编译器封装成一个函数传入delayedInit方法
//DelayInit会产生一些奇怪的问题,Scala已经考虑把它去掉
trait Application extends DelayedInit {
  var x: Option[() => Unit] = None

  override def delayedInit(cons: => Unit): Unit = {
    x = Some(() => cons)
  }

  def main(args: Array[String]): Unit = {
    x.foreach(_())
  }
}

object DelayInitProblem extends Application {
    println("I'am initialized!")
}
