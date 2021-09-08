package com.github.zjjfly.sid.ch4

import java.net.Socket
import java.nio.ByteBuffer

/**
  * Java提倡组合优于继承,Scala的trait让继承拥有和组合一样的优点
  * 但trait有两个缺点,一个是破坏封装,二是要访问构造器
  *
  * @author zjjfly
  */
object CompositionAndInheritance extends App {}

//以实现一个具有日志打印功能的数据访问服务为例演示trait如何破坏封装性
trait Logger {
  def log(category: String, msg: String): Unit = {
    println(msg)
  }
}

trait DataAccess {
  val logger: Logger

  def query[A](in: String): A = {
    logger.log("QUERY", in)
    ???
  }
}
//上面这种做法让数据访问服务有了完整的日志打印能力,但是破坏了封装性
trait DataAccess2 {
  def query[A](in: String): A = {
    ???
  }
}

trait LoggedDataAccess {
  val logger: Logger
  val dao: DataAccess

  def query[A](in: String): A = {
    logger.log("Query", in)
    ???
  }
}
//上面这种方式没有破坏封装性,但是LoggedDataAccess没有实现DataAccess2接口,它们无法以多态的方式互相替换
trait LoggedDataAccess2 extends DataAccess with Logger {
  override def query[A](in: String): A = {
    log("Query", in)
    ???
  }
}
//上面这种方式使得LoggedDataAccess2对于DataAccess和Logger都是多态的,但它可以在期望Logger的地方也可以使用,这很怪异

//下面是另一种在Martin Ordersky论文中提出的方式,可以避免上面所有的缺点
trait RemoteLogger extends Logger {
  val socket: Socket = new Socket()
  override def log(category: String, msg: String): Unit = {
    socket.getChannel.write(ByteBuffer.wrap(msg.getBytes()))
  }
}

trait NullLogger extends Logger {
  override def log(category: String, msg: String): Unit = {}
}

trait HasLogger {
  val logger: Logger
}

trait HasRemoteLogger extends HasLogger {
  override val logger: RemoteLogger = new RemoteLogger {}
}

trait HasNullLogger extends HasLogger {
  override val logger: Logger = new NullLogger {}
}

trait DataAccess3 extends HasLogger {
  def query[A](in: String): List[A] = {
    logger.log("Query", in)
    List()
  }
}

//上面这种方式比较繁琐,有一种更简单的方法,使用构造器参数来实现
class DataAccess4(val logger: Logger = new Logger {}) {
  def query[A](in: String): List[A] = {
    logger.log("Query", in)
    List()
  }
}

object DataAccess4

//<init>方法是构造器在java字节码中的名称
//这实现了以父类的构造器的默认值作为自己的默认值
class DoubleDataAccess(logger: Logger = DataAccess4.`<init>$default$1`)
    extends DataAccess4(logger) {}
//这种方法的缺点是无法很好的继承协作
