lazy val commonSettings = Seq(
  organization := "bao.ho",
  scalaVersion := "2.11.12"
)

lazy val root = (project in file("."))

  .settings(
    commonSettings,
    name := "scala_play_bitcoin_price_forecasting",
    version := "0.1",
    resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
    javaOptions += "-Djava.library.path=./lib",
    fork in run := true,
    libraryDependencies ++= Seq(jdbc,
      ehcache,
      ws,
      specs2 % Test,
      guice,
      dependencies.scalaGuice,
      dependencies.postgres,
      dependencies.playJson,
      dependencies.slickHikaricp,
      dependencies.akkaActor,
      dependencies.akkaStream,
      dependencies.scalatestplusPlay,
      dependencies.playCache,
      dependencies.playRedis,
      dependencies.jacksonCore,
      dependencies.jacksonDatabind,
      dependencies.jacksonModuleScala,
      dependencies.jep
    )
  )
  .dependsOn(model, util)
  .enablePlugins(PlayScala)

lazy val util = (project in file("util"))
  .settings(
    commonSettings,
    name := "util",
    version := "0.1",
    libraryDependencies ++= Seq(
      dependencies.scalatest,
      dependencies.scalactic,
      dependencies.scalaLogging,
      dependencies.scalaLoggingSlf4j,
      dependencies.scalaLoggingApi,
      dependencies.jacksonCore,
      dependencies.jacksonDatabind,
      dependencies.jacksonModuleScala
    )
  )

lazy val model = (project in file("model"))
  .settings(
    commonSettings,
    name := "model",
    version := "0.1",
    libraryDependencies ++= Seq(
      dependencies.playJson,
      dependencies.slick,
      dependencies.guice,
      dependencies.jacksonCore,
      dependencies.jacksonDatabind,
      dependencies.jacksonModuleScala
    )
  )


lazy val scheduler = (project in file("scheduler"))
  .settings(
    commonSettings,
    name := "scheduler",
    version := "0.1",
    libraryDependencies ++= Seq(
      ws,
      dependencies.guice,
      dependencies.playAhcWsStandalone,
      dependencies.playWsStandalone,
      dependencies.playWsStandaloneJson,
      dependencies.playWsStandaloneXml,
      dependencies.postgres,
      dependencies.playJson,
      dependencies.scalatest,
      dependencies.akkaActor,
      dependencies.akkaStream,
      dependencies.slick,
      dependencies.slickHikaricp,
      dependencies.jacksonCore,
      dependencies.jacksonDatabind,
      dependencies.jacksonModuleScala
    )
  )
  .dependsOn(model, util)

lazy val dependencyVersion =
  new {
    val playWsStandaloneV = "2.0.0-M2"
    val postgresV = "42.0.0"
    val playJsonV = "2.6.9"
    val guiceV = "4.2.0"
    val playSlickV = "3.0.3"
    val scalatestV = "3.0.4"
    val akkaV = "2.5.14"
    val scalaLoggingV = "3.9.0"
    val scalaLoggingSlf4jV = "2.1.2"
    val scalaLoggingApiV = "2.1.2"
    val slickV = "3.2.3"
    val scalaGuiceV = "4.2.1"
    val sparkV = "2.3.1"
    val jacksonModuleScalaV = "2.8.7"
    val scalatestplusPlayV = "3.1.2"
    val playCacheV = "2.6.17"
    val playRedisV = "2.1.1"
    val swaggerPlay2V = "1.6.0"
    val scoptV = "3.3.0"
    val guavaV = "15.0"
    val jepV = "3.8.2"
    val scalapyV = "0.2.0+3-c1eb96fb"
    val scalapyTensorflowV = "0.1.0"
  }

lazy val dependencies =
  new {
    val scalatest = "org.scalatest" %% "scalatest" % dependencyVersion.scalatestV % "test"
    val scalatestplusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % dependencyVersion.scalatestplusPlayV % Test
    val playCache = "com.typesafe.play" %% "play-cache" % dependencyVersion.playCacheV
    val playRedis = "com.github.karelcemus" %% "play-redis" % dependencyVersion.playRedisV
    val swaggerPlay2 = "io.swagger" %% "swagger-play2" % dependencyVersion.swaggerPlay2V
    val scalactic = "org.scalactic" %% "scalactic" % dependencyVersion.scalatestV
    val postgres = "org.postgresql" % "postgresql" % dependencyVersion.postgresV
    val playAhcWsStandalone = "com.typesafe.play" %% "play-ahc-ws-standalone" % dependencyVersion.playWsStandaloneV
    val playWsStandalone = "com.typesafe.play" %% "play-ws-standalone" % dependencyVersion.playWsStandaloneV
    val playWsStandaloneJson = "com.typesafe.play" %% "play-ws-standalone-json" % dependencyVersion.playWsStandaloneV
    val playWsStandaloneXml = "com.typesafe.play" %% "play-ws-standalone-xml" % dependencyVersion.playWsStandaloneV
    val playJson = "com.typesafe.play" %% "play-json" % dependencyVersion.playJsonV
    val guice = "com.google.inject" % "guice" % dependencyVersion.guiceV
    val playSlick = "com.typesafe.play" %% "play-slick" % dependencyVersion.playSlickV
    val playSlickEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % dependencyVersion.playSlickV
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % dependencyVersion.akkaV
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % dependencyVersion.akkaV
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % dependencyVersion.scalaLoggingV
    val scalaLoggingSlf4j = "com.typesafe.scala-logging" % "scala-logging-slf4j_2.11" % dependencyVersion.scalaLoggingSlf4jV
    val scalaLoggingApi = "com.typesafe.scala-logging" % "scala-logging-api_2.11" % dependencyVersion.scalaLoggingApiV
    val slick = "com.typesafe.slick" %% "slick" % dependencyVersion.slickV
    val slickHikaricp = "com.typesafe.slick" %% "slick-hikaricp" % dependencyVersion.slickV
    val scalaGuice = "net.codingwell" %% "scala-guice" % dependencyVersion.scalaGuiceV
    val sparkCore = "org.apache.spark" % "spark-core_2.11" % dependencyVersion.sparkV
    val sparkSql = "org.apache.spark" % "spark-sql_2.11" % dependencyVersion.sparkV
    val sparkMllib = "org.apache.spark" % "spark-mllib_2.11" % dependencyVersion.sparkV
    val jacksonCore = "com.fasterxml.jackson.core" % "jackson-core" % dependencyVersion.jacksonModuleScalaV
    val jacksonDatabind = "com.fasterxml.jackson.core" % "jackson-databind" % dependencyVersion.jacksonModuleScalaV
    val jacksonModuleScala = "com.fasterxml.jackson.module" %% "jackson-module-scala" % dependencyVersion.jacksonModuleScalaV
    val scopt = "com.github.scopt" %% "scopt" % dependencyVersion.scoptV
    val guava = "com.google.guava" % "guava" % dependencyVersion.guavaV
    val jep = "black.ninia" % "jep" % dependencyVersion.jepV
    val scalapy = "me.shadaj" % "scalapy_2.12" % dependencyVersion.scalapyV
    val scalapyTensorflow = "me.shadaj" % "scalapy-tensorflow_2.12" % dependencyVersion.scalapyTensorflowV

  }







