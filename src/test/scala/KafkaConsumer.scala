import java.util.Properties

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import kafka.consumer.{Consumer, ConsumerConfig}
import simpler.Person

/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-03-05.
 */
object KafkaConsumer {

  val kryo=new Kryo()

  def main(args: Array[String]) {

    kryo.register(Person.getClass)

    val props = getProperties
    val consumerConfig = new ConsumerConfig(props)
    val consumer = Consumer.create(config = consumerConfig)

    val streams = consumer.createMessageStreams(Map("test"->1))

    streams("test")(0).foreach{msg=>
//      val some = kryo.readObject(new Input(msg.message()), SomeClass.getClass)
      try {
        println("Received SomeClass")
        val input = new Input(msg.message())
        val some = kryo.readClassAndObject(input).asInstanceOf[Person]
        println(some)
        input.close()
      }
      catch {
        case e:Exception =>
          e.printStackTrace()
      }
    }


  }

  def getProperties:Properties = {
    val props: Properties = new Properties
    props.put("metadata.broker.list", "10.211.55.9:9092")
    props.put("zookeeper.connect", "10.211.55.9:2181")
    props.put("serializer.class", "kafka.serializer.DefaultEncoder")
    props.put("key.serializer.class", "kafka.serializer.StringEncoder")
    props.put("zk.sessiontimeout.ms", "60000")
    props.put("zk.synctime.ms", "500")
    props.put("request.required.acks", "1")
    props.put("group.id", "test")
    props
  }
}
