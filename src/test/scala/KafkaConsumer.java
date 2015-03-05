///*
// * Copyright (c) 2014 杭州端点网络科技有限公司
// */
//
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.message.MessageAndMetadata;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * <pre>
// *   功能描述:
// * </pre>
// *
// * @author wanggen on 2015-03-05.
// */
//public class KafkaConsumer {
//
//
//    public static void main(String[] args) {
//
//        Properties props = getProperties();
//        ConsumerConfig consumerConfig = new ConsumerConfig(props);
//
//        ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
//
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put("test", new Integer(1));
//
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//
//        List<KafkaStream<byte[], byte[]>> lists = consumerMap.get("test");
//        KafkaStream<byte[], byte[]> stream = lists.get(0);
//        ConsumerIterator<byte[], byte[]> it = stream.iterator();
//        //        PersonKryoDecoder decoder = new PersonKryoDecoder();
//        while (it.hasNext()) {
//            MessageAndMetadata<byte[], byte[]> msg = it.next();
////            System.out.println(stream + "  " + new String(msg.key()) + decoder.fromBytes(msg.message()));
//        }
//    }
//
//
//        public static Properties getProperties() {
//            Properties props = new Properties();
//            props.put("metadata.broker.list", "localhost:9092");
//            props.put("zookeeper.connect", "localhost:2181");
//            props.put("serializer.class", "kafka.serializer.DefaultEncoder");
//            props.put("key.serializer.class", "kafka.serializer.StringEncoder");
//            // props.put("partitioner.class", );
//            props.put("zk.sessiontimeout.ms", 60000);
//            props.put("zk.synctime.ms", 500);
//            props.put("request.required.acks", "1");
//            props.put("group.id", "1");
//            return props;
//        }
//
//}
