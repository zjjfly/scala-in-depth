package com.github.zjjfly.sid.ch2

object PolymorphicEquality extends App {
  val x = new InstantaneousTime {
    override val repr: Int = 1
  }
  val y = new Event {
    override val name: String = "jjzi"
    override val repr: Int = 1
  }
  assert(x != y)
  assert(y != x)
}
//一个好的相等性的实现
//Equal用于处理多态对象的相等性
trait InstantaneousTime extends Equals {
  val repr: Int

  override def canEqual(that: Any): Boolean =
    that.isInstanceOf[InstantaneousTime]

  override def hashCode(): Int = repr.##

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: InstantaneousTime => {
      //先比较是否是同一个对象,这样更高效
      if (this eq that) true
      else
        //把比较hashCode作为一个early false check
        //调用##而不是hashCode方法,因为##支持值类型
        //调用要比较的对象的canEqual,不能调换顺序,否则会出错
        (that.## == this.##) && (that canEqual this) && (this.repr == that.repr)
    }
    case _ => false
  }
}
trait Event extends InstantaneousTime {
  val name: String
  //当重写equals的时候,也要同时重写canEqual,这样避免了父类和子类的判等方法对于相同的两个对象给出不同的结果
  override def canEqual(that: Any): Boolean = that.isInstanceOf[Event]

  override def equals(obj: Any): Boolean = obj match {
    case that: Event => {
      if (this eq that) {
        true
      } else {
        //这里没有使用hashCode,因为检测repr的值的性能也很高
        (that canEqual this) && (this.repr == that.repr) && (this.name == that.name)
      }
    }
    case _ => false
  }
}
