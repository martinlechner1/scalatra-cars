import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import ScalateKeys._

val ScalatraVersion = "2.5.1"
val scalatraPackage = "org.scalatra"

ScalatraPlugin.scalatraSettings

scalateSettings

organization := "com.github.martinlechner1"

name := "CarApi"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.11"

resolvers += Classpaths.typesafeReleases

javaOptions ++= Seq(
  "-Xdebug",
  "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
)

libraryDependencies ++= Seq(
  scalatraPackage %% "scalatra" % ScalatraVersion,
  scalatraPackage %% "scalatra-json" % ScalatraVersion,
  scalatraPackage %% "scalatra-scalate" % ScalatraVersion,
  scalatraPackage %% "scalatra-specs2" % ScalatraVersion % "test",
  scalatraPackage %% "scalatra-swagger" % ScalatraVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container;compile",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.json4s"   %% "json4s-native" % "3.5.2",
  "org.json4s"   %% "json4s-jackson" % "3.5.2",
  "com.wix" %% "accord-core" % "0.6.1",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "io.getquill" %% "quill-jdbc" % "1.2.1"
)

enablePlugins(JettyPlugin)

