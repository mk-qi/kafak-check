package teg.cfd.omc

import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Duration, Milliseconds, Seconds, StreamingContext, Time}
import com.typesafe.config.ConfigFactory

import org.apache.spark.streaming.kafka._


object KafkaCheck {

  def main(args: Array[String]): Unit = {

    val conf = ConfigFactory.load()

    val sparkConf = new SparkConf().setAppName(conf.getString("spark.appname"))
    val ssc = new StreamingContext(sparkConf, Milliseconds(conf.getLong("batchDurationMs")))


    val zkQuorum = "10.208.142.84:2181"
    val group = "App-reduce_ration"
    val topics = "taged"
    val numThreads = 2


    val topicpMap = conf.getString("kafka.topics").split(",").map((_, numThreads.toInt)).toMap

    /*val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    ) */

    val kafkaStream = KafkaUtils.createStream(ssc, zkQuorum, group, topicpMap).map(_._2)

    kafkaStream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}

