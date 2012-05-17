import com.jsuereth.ghpages.GhPages.ghpages
import com.jsuereth.git.GitPlugin.git
import com.jsuereth.sbtsite.SitePlugin.site
import sbt._

import Keys._
import AndroidKeys._

import de.johoop.jacoco4sbt._
import JacocoPlugin._

object General {

  val settings = Defaults.defaultSettings ++ Seq(
    name := "typewriter",
    organization := "com.novoda",
    crossPaths := false,
    version := "0.0.1-SNAPSHOT",
    versionCode := 0,
    scalaVersion := "2.9.1",
    //platformName in Android := "android-14",
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
    publishTo := Some("Sonatype Snapshots Nexus" at "https://oss.sonatype.org/content/repositories/snapshots")
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
        "com.google.android.maps" % "maps" % "15_r2" % "test",
        "org.mockito" % "mockito-core" % "1.9.0" % "test",
        "com.google.android" % "android" % "4.0.1.2" % "provided"
      ),
      // needed for google maps.
      resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
    )
}

object AndroidBuild extends Build {

  lazy val main = Project(
    "Typewriter",
    file("."),
    settings = General.settings ++ site.settings ++ site.includeScaladoc() ++ ghpages.settings ++ Seq(
      git.remoteRepo := "git@github.com:charroch/typewriter.git",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.8.RC1" % "test",
        "junit" % "junit" % "4.10" % "test",
        "com.pivotallabs" % "robolectric" % "1.1" % "test",
        "com.novocode" % "junit-interface" % "0.8" % "test",
        "com.google.android.maps" % "maps" % "15_r2" % "test",
        "org.mockito" % "mockito-core" % "1.9.0" % "test",
        "com.google.android" % "android" % "4.0.1.2" % "provided"
      )
    )
  )

  //  lazy val tests = Project(
  //    "tests",
  //    file("tests"),
  //    settings = General.settings ++
  //      AndroidTest.settings ++
  //      General.proguardSettings ++ Seq(
  //      name := "TypewriterTests"
  //    )
  //  ) dependsOn main
}
