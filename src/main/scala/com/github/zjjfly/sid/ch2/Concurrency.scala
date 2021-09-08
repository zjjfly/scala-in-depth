package com.github.zjjfly.sid.ch2

import scala.collection.immutable.{HashMap => ImmutableHashMap}
//使用不可变对象让写并发程序更加简单
class ImmutableService[Key, Value] extends Service[Key,Value]{
  var currentIndex = new ImmutableHashMap[Key, Value]
  var m=Map(1->1)

  def lookUp(k: Key): Option[Value] = currentIndex.get(k)

  def insert(k: Key, v: Value): Unit = synchronized {
    currentIndex = currentIndex + ((k, v))
  }
}
trait Service[Key,Value] {
  def lookUp(k: Key): Option[Value]
  def insert(k: Key, v: Value): Unit
}
