package com.github.zjjfly.sid.ch3

object Override extends App {
  //总是使用override关键字标记覆盖方法,用它的好处远大于不用的好处
  val kittyDoggy = new Cat with Dog
  val kittyDoggy2 = new Dog with Cat
  println(kittyDoggy.talk)
  println(kittyDoggy2.talk)
}

trait Animal {
  def talk: String
}

trait Cat extends Animal {
  override def talk: String = "Meom"
}

trait Dog extends Animal {
  override def talk: String = "Woof"
}
