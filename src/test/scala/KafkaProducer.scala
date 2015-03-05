import java.util.Properties

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Output
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import simpler.Person


object KafkaProducer {

  val kryo=new Kryo()

  var producer: Producer[String, Array[Byte]] = null

  def main(args: Array[String]) {

    kryo.register(Person.getClass)
    val output = new Output(1024)
    val someObject = new Person("王根", 24)
    kryo.writeClassAndObject(output, someObject)


    val props = new Properties()
    props.put("metadata.broker.list", "10.211.55.9:9092")
    props.put("serializer.class", "kafka.serializer.DefaultEncoder")

    producer = new Producer[String, Array[Byte]](new ProducerConfig(props))
    producer.send(new KeyedMessage[String, Array[Byte]]("test", output.getBuffer))
    output.close()


//    for(line <- Source.fromFile("/Users/wanggen/groupon-web-access.log").getLines()){
//      producer.send(new KeyedMessage[String, String]("test", line))
//      Thread.sleep(800)
//    }

    producer.close()

  }

}
