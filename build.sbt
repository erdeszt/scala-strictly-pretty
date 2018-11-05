lazy val strictlyPretty = Project("strictly-pretty", file("."))
    .settings(
      name := "strictly-pretty",
      version := "0.1",
      scalaVersion := "2.12.7",
      testFrameworks += new TestFramework("utest.runner.Framework"),
      libraryDependencies ++= Seq(
        "com.lihaoyi" %% "utest" % "0.6.5" % Test
      )
    )
