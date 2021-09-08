package com.github.zjjfly.sid.ch3

import scala.annotation.tailrec

object AnnotateOptimization extends App {
  //对期望的优化进行注释

  //一种优化是把模式匹配优化为switch语句.这种优化可以是通过字节码tableswitch实现的
  //它把模式匹配编译成分支表而不是决策树,这样就不用对值做很多比较,而是直接用值去分支表中查找对应的标签,然后JVM直接跳到对应的代码
  //这种优化需要满足三个条件:1.匹配的值必须是已知的整数;2.每个匹配语句都必须简单,不能有类型检查,if语句或抽取器,表达式的值必须在编译期可知,不能再运行时计算;3.应该有多于两个的case
  //下面这个方法就会进行tableswitch优化
  def unannotated(x: Int) = x match {
    case 1 => "one"
    case 2 => "two"
    case z => z + ""
  }
  //加了一个类型检查,编译器就没有进行tableswitch优化
  def noOptimised(x: Int) = x match {
    case 1      => "one"
    case 2      => "two"
    case i: Int => i.toString
  }

  //使用@switch这个注解来让编译器直到需要进行tableswitch优化,如果不能优化,则会发出一个警告(warn)来告诉我们
  //tableswitch的价值是有争议的,所以@switch这个不常用
  import annotation.switch
  def noOptimised3(x: Int) = (x: @switch) match {
    case 1      => "one"
    case 2      => "two"
    case i: Int => i.toString
  }

  //@tailrec是一个用的更多的注解,它会让编译器对方法进行尾递归优化.
  //尾递归优化会把最后一句调用自身的尾递归函数优化为while或for这样的循环.JVM本身是不支持的,所以这需要通过Scala编译器实现
  //要进行尾递归优化有三个条件:1.方法必须是final或私有的,也就是不能多态;2.方法必须注明返回值类型;3.方法必须在某一个分支调用自身
  case class Node(name: String, edges: List[Node] = Nil)

  //使用javap -c -p来查看编译的字节码,发现loop方法实际没有调用自身,而是优化成了循环
  def search(start: Node, p: Node => Boolean): Option[Node] = {
    @tailrec
    def loop(nodeQueue: List[Node], visited: Set[Node]): Option[Node] = {
      nodeQueue match {
        case head :: _ if p(head) => Some(head)
        case head :: tail if !visited.contains(head) =>
          loop(tail ++ head.edges, visited + head)
        case _ :: tail => loop(tail, visited)
        case Nil       => None
      }
    }
    loop(List(start), Set())
  }
}
