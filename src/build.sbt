name := "kafka-check"

version := "1.0"

scalaVersion := "2.11.0"

val elkVersion = "5.0.1"

val sparkVersion = "2.0.1"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming-kafka-0-8-assembly" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "com.typesafe" % "config" % "1.2.1",
  "org.elasticsearch" % "elasticsearch-spark-20_2.11" % elkVersion % "provided"
)

mergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (mergeStrategy in assembly).value(x)
}