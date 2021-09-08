package com.github.zjjfly.sid.ch3

//一些编码规范
object CodingConventions extends App {
  //编码规范不止是语言本身的规定,还要考虑到与其他人的交流
  //它的目的可以归为三点:代码可读性,代码一致性,预防错误

  //1.代码块的开花括号是否另起一行,这在很多语言中是无所谓的,但Scala不是,因为scala不使用分号来表示一行的结束
  //如果另起一行,scala2.7及以下版本会编译出错.在2.8之后的版本中可以这样复现问题:
//  def foo() //这行会被当成是一个abstract方法的定义
//
//  {
//    println("foo") 这一个代码块会当成是类的构造方法的一部分
//  }
  //避免出现这种问题的方法是,在定义方法的时候总是使用=
  // format: OFF
  def foo(): Unit =

  {
    println("foo")
  }
  // format: ON
  foo()

  //如果代码需要分多行,使用dangling operator(空悬操作符)来结束一行
  //空悬操作符指一行代码的最后一个非空字符是一个操作符,它有助于编译器判断语句真正结束的位置
  val x = 5
  def hi =
    "Hi," + x +
      "!" +
      "\n"
}
