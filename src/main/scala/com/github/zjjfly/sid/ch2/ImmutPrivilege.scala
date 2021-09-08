package com.github.zjjfly.sid.ch2

//优先选择不可变性
//不可变性是函数式编程的基石,它值的是对象一旦创建后就不会再改变状态
//它在写判等和并发的时候特别有用,因为它可以保证一个对象的hashCode在它的整个生命周期都是不变的
object ImmutPrivilege extends App {
  val x = new Point2(1, 1)
  val y = new Point2(1, 2)
  val pointToString = Map(x -> "1,1", y -> "1,2")
  x.move(1, 1)
  assert(pointToString(x) != null)
  println(x.##)//##取hash值
}
class Point2(val x: Int, val y: Int) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[Point2]

  override def equals(other: Any): Boolean = other match {
    case that: Point2 =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
  def move(dx: Int, dy: Int): Point2 = new Point2(this.x + dx, this.y + dy)
  override def toString = s"Point2($x, $y),hashCode:${hashCode()}"
}
