package com.github.zjjfly.sid.ch2

import scala.util.Random

//优先使用表达式
object ExpressionPrivilege extends App {
  //Scala中的一些控制块就是表达式,如if和模式匹配
  val str= if (new Random(10).nextInt(10)>5) "more than 5" else "less than 5"
  def createErrorMessage(errorCode:Int):String=errorCode match {
    case 1=> "Network Failure"
    case 2=> "I/O Failure"
    case _=> "Unknown Error"
  }
  println("hehe")
}
