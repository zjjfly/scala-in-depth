package com.github.zjjfly.sid.ch4

/**
 * refer to this <a href="https://kubuszok.com/2017/tagged-or-anyval/">blog</a>
 *
 * @author <a href="https://github.com/zjjfly"/>zjjfly</a>
 */
class TaggedType {

  trait Name

  trait Surname

  type Tagged[U] = {type Tag = U}
  type @@[T, U] = T with Tagged[U]

  class Tagger[U] {
    def apply[T](t: T): T @@ U = t.asInstanceOf[T @@ U]
  }

  def tag[U](i: String): String @@ U = i.asInstanceOf[String @@ U]

  def logUser(name: String @@ Name, surname: String @@ Surname): Unit = {
    println("")
  }

  def main(args: Array[String]): Unit = {
    val name: String @@ Name = tag[Name]("John")
    val surname: String @@ Surname = tag[Surname]("Smith")
    logUser(name, surname) // compiles
    //    logUser(surname, name)
  }

}


