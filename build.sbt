
lazy val root = project.in(file(".")).
  aggregate(dot_js, dot_jvm).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val dot_fastparse = crossProject.in(file(".")).
  settings(
    name := "dot-fastparse",
    version := "0.0.1-SNAPSHOT",
    organization := "eu.unicredit",
    scalaVersion := "2.11.7"
  ).
  settings(
    libraryDependencies += "com.lihaoyi" %%% "fastparse" % "0.3.1"
    //libraryDependencies += "com.lihaoyi" %%% "ammonite-repl" % "0.5.0" % "test" cross CrossVersion.full

  ).
  jvmSettings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  ).
  jsSettings()

lazy val dot_jvm = dot_fastparse.jvm
lazy val dot_js  = dot_fastparse.js


//initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""
