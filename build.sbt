name := "scala-in-depth"

version := "0.1"

scalaVersion := "2.12.7"

enablePlugins(GraalVMNativeImagePlugin)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

Compile / mainClass := Some("com.github.zjjfly.sid.ch2.ExpressionPrivilege")
