package com.github.zjjfly.sid.ch2

import java.io.File
import java.sql.{Connection, DriverManager}
import javax.net.ssl.SSLSession

//使用Option代替null
object UseOption extends App {
  val x: Option[String] = None
  //x.get会报错
  assert(x.getOrElse("jjzi") == "jjzi")
  val y: Option[String] = Some("jjzi")
  assert(y.get == "jjzi")
  assert(y.getOrElse("zjj") != "zjj")

  //可以像操作集合一样操作Option
  def getTemporaryDirectory(tmp: Option[String]): File = {
    tmp
      .map(new File(_))
      .filter(_.isDirectory)
      .getOrElse(new File(System.getProperty("java.io.tmpDir")))
  }
  //Option中如果有值执行一段代码
  val userName: Option[String] = Option("jjzi")
  for (uname <- userName) {
    println("user name:" + uname)
  }
  //多个Option如果都有值就执行一段代码
  def createConnection(conn_url: Option[String],
                       conn_user: Option[String],
                       conn_pw: Option[String]): Option[Connection] =
    for {
      url <- conn_url
      user <- conn_user
      pw <- conn_pw
    } yield DriverManager.getConnection(url, user, pw)
  //对上面的函数进行泛化
  def lift3[A, B, C, D](
      f: (A, B, C) => D): (Option[A], Option[B], Option[C]) => Option[D] =
    (oa: Option[A], ob: Option[B], oc: Option[C]) => {
      for (a <- oa;
           b <- ob;
           c <- oc) yield f(a, b, c)
    }
  lift3(DriverManager.getConnection)
}
