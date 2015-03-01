package simpler

import java.text.SimpleDateFormat
import java.util.Date

import com.fasterxml.jackson.databind.ObjectMapper
import me.prettyprint.cassandra.serializers.StringSerializer
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate
import me.prettyprint.hector.api.factory.HFactory
import org.apache.spark.SparkContext.rddToPairRDDFunctions
import org.apache.spark.{SparkConf, SparkContext}



object Demo2 {

  def main(args: Array[String]) {

    val sparkConfig = new SparkConf()
    sparkConfig.setMaster("spark://10.211.55.9:7077")

    val sc = new SparkContext(sparkConfig)

    val ordersRDD = sc.textFile("hdfs://wg-mac:9000/user/wanggen/ecp_orders/part*")
      .map(_.split(","))
      .filter(list=>list(8)=="1")
      .map(arr=>(arr(2), arr(11).trim.toDouble/100))
      .reduceByKey(_+_)
      .sortBy(x=>x._2, false, 1)

    val  cluster = HFactory.getOrCreateCluster("groupon-cluster","10.211.55.9:9160")
    val ksp = HFactory.createKeyspace("groupon", cluster)

    val template =
      new ThriftColumnFamilyTemplate[String, String](ksp, "order_statistics",
        StringSerializer.get(),
        StringSerializer.get())

    val  updater = template.createUpdater("worthy-buyer")

    val mapper = new ObjectMapper()

    updater.setString("ordered_"+System.currentTimeMillis(), mapper.writeValueAsString(ordersRDD.collect()))
    template.update(updater)

  }


}
