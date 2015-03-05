package simpler.producer_and_consumer

import java.util.Properties

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import kafka.consumer.{Consumer, ConsumerConfig}

/**
 * @author wanggen on 2015-03-05.
 */
object KafkaConsumer {

  val kryo = new Kryo()
  kryo.register(Person.getClass)

  def main(args: Array[String]) {


    val props = getProperties
    val consumerConfig = new ConsumerConfig(props)
    val consumer = Consumer.create(config = consumerConfig)

    val streams = consumer.createMessageStreams(Map("test" -> 1))

    streams("test")(0).foreach { msg =>

      handleMsg(msg.message(), (p: Person) => {
        println(p)
      })


      def handleMsg[T](bytes: Array[Byte], function: (T) => Unit): Unit = {
        val input = new Input(bytes)
        try {
          val some = kryo.readClassAndObject(input).asInstanceOf[T]
          function.apply(some)
        } catch {
          case e: Exception => e.printStackTrace()
        } finally input.close()
      }

    }


  }

  def getProperties: Properties = {
    val props = new Properties
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
