name := "Sygments"

organization := "com.scalawilliam"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.6"

jetty()

resolvers += "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases"

resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.python" % "jython-standalone" % "2.5.3",
  "org.eclipse.jetty" % "jetty-webapp" % "9.3.0.M2",
  "org.eclipse.jetty" % "jetty-servlet" % "9.3.0.M2",
  "org.eclipse.jetty" % "jetty-server" % "9.3.0.M2",
  "org.eclipse.jetty" % "jetty-rewrite" % "9.3.0.M2",
  "org.pygments" % "pygments" % "1.6",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
  "org.scalatra" %% "scalatra" % "2.4.0.RC1",
  "org.webjars" % "bootstrap" % "3.3.4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalatra" %% "scalatra-scalatest" % "2.4.0.RC1" % "test",
  "org.webjars" % "angular-ui-bootstrap" % "0.12.1",
  "org.webjars" % "angular-ui" % "0.4.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)
