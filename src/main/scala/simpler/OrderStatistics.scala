package simpler

import org.apache.spark.SparkContext.rddToPairRDDFunctions
import org.apache.spark.{SparkConf, SparkContext}



/**
 * @author wanggen on 2015-02-27.
 */
object OrderStatistics {

  def main(args: Array[String]) {

    val sparkConfig = new SparkConf()

    val sc = new SparkContext(sparkConfig)

    val ordersRDD = sc.textFile("hdfs://wg-mac:9000/user/wanggen/ecp_orders/part*")
      .map(_.split(","))
      .filter(list=>list(8)=="1")
      .map(arr=>(arr(2), arr(11).trim.toDouble/100))
      .reduceByKey(_+_)
      .sortBy(x=>x._2, false, 1)
      .saveAsTextFile("hdfs://wg-mac:9000/user/wanggen/ecp_orders-statistics3")

  }


}
