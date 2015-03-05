package simpler

import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date

import akka.event.slf4j.Logger
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.io.Files
import kafka.serializer.StringDecoder
import org.apache.commons.io.FileUtils
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}

import scala.collection.mutable

object Demo3 {

  val log = Logger.apply("Demo3")

  val mapper = new ObjectMapper()

  def main(args: Array[String]) {

    log.info("App Demo3 starting ... ")

    val sparkConf = new SparkConf()
      .setAppName(this.getClass.getSimpleName)
      .set("spark.streaming.receiver.writeAheadLog.enable", "true")

    var kafkaParams: Map[String, String] = null
    var ssc: StreamingContext = null
    var tempDirectory: File = null

    kafkaParams = Map(
      "zookeeper.connect" -> "10.211.55.9:2181",
      "group.id" -> "1",
      "auto.offset.reset" -> "smallest"
    )

    ssc = new StreamingContext(sparkConf, Milliseconds(500))
    tempDirectory = Files.createTempDir()
    ssc.checkpoint(tempDirectory.getAbsolutePath)


    val stream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Map("test" -> 1), StorageLevel.MEMORY_ONLY)


    stream.map(_._2).foreachRDD { r =>
      val result = new mutable.HashMap[String, Long]()
      val ret = r.collect().flatMap(line => line.trim.split("\\s+"))
      if (ret.size > 0)
        Files.append(s"${new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())} ${ret.toList}\n",
          new File("/usr/dev/workspace/sparker/data/src.txt"), Charset.forName("UTF-8"))
      log.info(s"Received data: ${ret.toList}")
      ret.foreach { v =>
        val count = result.getOrElseUpdate(v, 0) + 1
        result.put(v, count)
        log.info(s"Mapped data: $result, ${result.size}, ${result.isEmpty}")
      }
      if(result.size > 0)
        Files.append(s"${new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())} $result\n",
          new File("/usr/dev/workspace/sparker/data/res.txt"), Charset.forName("UTF-8"))
    }

    sys.addShutdownHook{
      ssc.stop(stopSparkContext = true, stopGracefully = true)
      log.info("Demo3 stopping ...")
      if (tempDirectory != null && tempDirectory.exists()) {
        FileUtils.deleteDirectory(tempDirectory)
        tempDirectory = null
      }
    }

    ssc.start()
    ssc.awaitTermination()

  }

}
