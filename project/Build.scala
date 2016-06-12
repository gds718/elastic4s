import com.typesafe.sbt.pgp.PgpKeys
import sbt._
import sbt.plugins.JvmPlugin
import sbt.Keys._

object Build extends AutoPlugin {

  override def trigger = AllRequirements
  override def requires = JvmPlugin

  object autoImport {
    val org = "qiaobutang"

    val ScalaVersion = "2.11.7"
    val ScalatestVersion = "2.2.5"
    val MockitoVersion = "1.9.5"
    val JacksonVersion = "2.6.1"
    val Slf4jVersion = "1.7.12"
    val ScalaLoggingVersion = "2.1.2"
    val ElasticsearchVersion = "2.3.0"
    val Log4jVersion = "1.2.17"
    val CommonsIoVersion = "2.4"
  }

  import autoImport._

  override def projectSettings = Seq(
    organization := org,
    scalaVersion := ScalaVersion,
    crossScalaVersions := Seq("2.11.7", "2.10.5"),
    publishMavenStyle := true,
    resolvers += Resolver.mavenLocal,
    fork in Test := true,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    publishArtifact in Test := false,
    parallelExecution in Test := false,
    sbtrelease.ReleasePlugin.autoImport.releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    sbtrelease.ReleasePlugin.autoImport.releaseCrossBuild := true,
    scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
    javacOptions := Seq("-source", "1.7", "-target", "1.7"),
    libraryDependencies ++= Seq(
      "org.elasticsearch" % "elasticsearch" % ElasticsearchVersion,
      "org.scalactic" %% "scalactic" % "2.2.5",
      "org.slf4j" % "slf4j-api" % Slf4jVersion,
      "commons-io" % "commons-io" % CommonsIoVersion % "test",
      "log4j" % "log4j" % Log4jVersion % "test",
      "org.slf4j" % "log4j-over-slf4j" % Slf4jVersion % "test",
      "org.mockito" % "mockito-all" % MockitoVersion % "test",
      "org.scalatest" %% "scalatest" % ScalatestVersion % "test"
    ),
    credentials += Credentials("Sonatype Nexus Repository Manager", "repo.qiaobutang.com", "qbt", "52b2ca75e4b068789b5bc5eb"),
    publishTo <<= version {
      (v: String) =>
        val nexus = "http://repo.qiaobutang.com:9876/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "nexus/content/repositories/snapshots")
        else
          Some("releases" at nexus + "nexus/content/repositories/releases")
    }
  )
}
