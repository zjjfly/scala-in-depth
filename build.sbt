name := "ScalaInDepth"

version := "0.1"

scalaVersion := "2.12.7"

enablePlugins(GraalVMNativeImagePlugin)

mainClass := Some("com.github.zjjfly.sid.ch2.ExpressionPrivilege")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)
