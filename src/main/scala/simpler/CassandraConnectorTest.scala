package simpler

import java.sql.{DriverManager, Driver}

import com.datastax.spark.connector.cql.{CassandraConnectorConf, CassandraConnector}
import org.apache.spark.SparkContext.rddToPairRDDFunctions
import org.apache.spark.{SparkConf, SparkContext}

import com.datastax.spark.connector._

/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-01-31.
 */
object CassandraConnectorTest {

  def main(args: Array[String]) {

    val conf = new SparkConf()
    conf.setAppName("Java API demo")
//    conf.setMaster(args(0))
    conf.set("spark.cassandra.connection.host", args(1))
    conf.set("spark.cassandra.connection.rpc.port", "9160")

    val sc = new SparkContext(conf)

//    CassandraConnector(conf).withSessionDo(session=>{
//
//      try {
//        if (session.execute("select count(*) from groupon.ecp_orders;").one().getLong("count") > 0) return
//      }
//      finally{}
//
//      Class.forName("com.mysql.jdbc.Driver").newInstance()
//      val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupon?user=root&password=anywhere")
//
//      val stat = conn.createStatement()
//      val resultSet = stat.executeQuery("select * from ecp_orders")
//      println("Fetch size: "+resultSet.getFetchSize)
//      while(resultSet.next()){
//
//        session.execute(
//                        s"""
//                          insert into groupon.ecp_orders(id, buyer_name, seller_name, fee, has_paid)
//                          values ( ${resultSet.getLong("id")},
//                                  '${resultSet.getString("buyer_name")}',
//                                  '${resultSet.getString("seller_name")}',
//                                   ${resultSet.getInt("fee")},
//                                   ${resultSet.getInt("has_paid")}
//                                );
//                        """.stripMargin
//        )
//      }
//      stat.close()
//      conn.close()
//
//    })

    val rdd = sc.cassandraTable("groupon", "ecp_orders")

    print("订单总金额: "+rdd.filter(row=>row.getInt("has_paid")==1)
      .map(row=>(row.getString("buyer_name"), row.getInt("fee"))).reduceByKey(_ + _)
      .collect().sortWith((a,b)=>a._2>b._2) // .foreach(println)
    )


  }

}
