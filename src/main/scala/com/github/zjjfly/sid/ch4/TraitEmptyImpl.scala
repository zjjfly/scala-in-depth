package com.github.zjjfly.sid.ch4

object TraitEmptyImpl extends App {
  //trait线性化的计算公式
  //linear(Object->Route->NetworkEntity)=
  private val router = new Router with NetworkEntity {
    override def getMacAddress(ip: String): String = "1.1.1.1"

    override def hasIpAddress(addr: String): Boolean = true
  }
  router.handleMsg(Test("hi"), (sender: String, resp: SimulationMessage) => {
    println("response")
  })
}

trait SimulationMessage

case class PingRequest(ip: String, sender: String) extends SimulationMessage

case class PingResponse(macAddress: String) extends SimulationMessage

case class Test(x: String) extends SimulationMessage

trait SimulationContext {
  def response(sender: String, resp: SimulationMessage): Unit
}

trait SimulationEntity {
  def handleMsg(msg: SimulationMessage, ctx: SimulationContext): Unit
}

trait MixableParent extends SimulationEntity {
  override def handleMsg(msg: SimulationMessage,
                         ctx: SimulationContext): Unit = {}
}

trait NetworkEntity extends MixableParent {
  def getMacAddress(ip: String): String

  def hasIpAddress(addr: String): Boolean

  override def handleMsg(msg: SimulationMessage, ctx: SimulationContext): Unit =
    msg match {
      case PingRequest(ip, sender) if hasIpAddress(ip) =>
        ctx response (sender, PingResponse(getMacAddress(ip)))
      case _ =>
        super.handleMsg(msg, ctx)
    }
}

class Router extends SimulationEntity {
  override def handleMsg(msg: SimulationMessage, ctx: SimulationContext): Unit =
    msg match {
      case Test(x) => println(s"YAY!:$x")
      case _       =>
    }
}
