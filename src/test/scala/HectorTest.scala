
import java.util

import me.prettyprint.cassandra.serializers.{ObjectSerializer, StringSerializer}
import me.prettyprint.cassandra.service.ThriftKsDef
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate
import me.prettyprint.hector.api._
import me.prettyprint.hector.api.ddl.ComparatorType
import me.prettyprint.hector.api.exceptions.HectorException
import me.prettyprint.hector.api.factory.HFactory

import scala.collection.mutable
;


/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-02-28.
 */



object HectorTest {

  def main(args: Array[String]) {


    val  cluster = HFactory.getOrCreateCluster("groupon-cluster","localhost:9160")

   /* val cfDef = HFactory.createColumnFamilyDefinition("mykeyspace",
      "person",
      ComparatorType.BYTESTYPE)

    val newKeyspace = HFactory.createKeyspaceDefinition("mykeyspace",
      ThriftKsDef.DEF_STRATEGY_CLASS,
      1,
      util.Arrays.asList(cfDef))
    // Add the schema to the cluster.
    // "true" as the second param means that Hector will block until all nodes see the change.
    cluster.addKeyspace(newKeyspace, true)

//    cluster.updateKeyspace(newKeyspace, true)

    val keyspaceDef = cluster.describeKeyspace("mykeyspace");

    // If keyspace does not exist, the CFs don't exist either. => create them.
    if (keyspaceDef == null) {
//      createSchema()
    }*/


    val ksp = HFactory.createKeyspace("mykeyspace", cluster)

    val template =
      new ThriftColumnFamilyTemplate[String, String](ksp, "person",
      StringSerializer.get(),
      StringSerializer.get())

    val  updater = template.createUpdater("a key")
    updater.setString("domain", "www.datastax.com")
    updater.setLong("time", System.currentTimeMillis())

    val map = mutable.Map("name"->"王根", "age"->24)


    updater.setValue("wanggenDetail", map, ObjectSerializer.get())

    try {
      template.update(updater)
    }

    try {
      val res = template.queryColumns("a key")
      val value = res.getString("domain")

      val map = ObjectSerializer.get().fromBytes(res.getByteArray("wanggenDetail")).asInstanceOf[mutable.Map[String, String]]

      println("my name is: "+map.get("name"))
      println("my name is: "+map.get("name").get)
      println("my name is: "+map.get("name").getClass)

      // value should be "www.datastax.com" as per our previous insertion.
    }

  }

}
