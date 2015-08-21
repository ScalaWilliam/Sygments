name := "Sygments"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).dependsOn(play, core).aggregate(play, core)

lazy val play = project.enablePlugins(PlayScala).dependsOn(core)

lazy val core = project
