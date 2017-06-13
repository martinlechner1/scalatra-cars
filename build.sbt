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
  scalatraPackage %% "scalatra-scalate" % ScalatraVersion,
  scalatraPackage %% "scalatra-specs2" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  scalatraPackage %% "scalatra-json" % ScalatraVersion,
  "org.json4s"   %% "json4s-native" % "3.5.2",
  "org.json4s"   %% "json4s-jackson" % "3.5.2",
  scalatraPackage %% "scalatra-swagger" % ScalatraVersion,
  "com.wix" %% "accord-core" % "0.6.1"
)

enablePlugins(JettyPlugin)

