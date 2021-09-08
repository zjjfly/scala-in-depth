package com.github.zjjfly.sid.ch4

object EarlyDefinition extends App {
  val x = new Property {
    override val name: String = "HI"
  }
  //Scala初始化的顺序是超类早于子类,所以这里toString在name重载之前就已经初始化了
  assert(x.toString == "Property(null)")
  //使用早期成员定义可以很好解决这个问题
  val y = new { val name = "HI" } with Property
  assert(y.toString == "Property(HI)")
}
trait Property {
  val name: String

  override val toString: String = s"Property($name)"
}
