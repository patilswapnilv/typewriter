import sbt._

import Keys._
import AndroidKeys._

import de.johoop.jacoco4sbt._
import JacocoPlugin._

object General {
  val settings = Defaults.defaultSettings ++ Seq(
    name := "Typewriter",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.1",
    platformName in Android := "android-14"
  )

  val proguardSettings = Seq(
    useProguard in Android := false
  )

  lazy val fullAndroidSettings =
    General.settings ++ jacoco.settings ++
      AndroidProject.androidSettings ++
      proguardSettings ++
      AndroidManifestGenerator.settings ++ Seq(
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.8.RC1" % "test",
        "junit" % "junit" % "4.10" % "test",
        "com.pivotallabs" % "robolectric" % "1.1" % "test",
        "com.novocode" % "junit-interface" % "0.8" % "test",
        "com.google.android.maps" % "maps" % "15_r2" % "test"
      ),
      // needed for google maps.
      resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project(
    "Typewriter",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project(
    "tests",
    file("tests"),
    settings = General.settings ++
      AndroidTest.settings ++
      General.proguardSettings ++ Seq(
      name := "TypewriterTests"
    )
  ) dependsOn main
}
