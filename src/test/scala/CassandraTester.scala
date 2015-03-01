import com.datastax.driver.core.Cluster

/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-02-28.
 */
object CassandraTester {

  def main(args: Array[String]) {

    val cluster = Cluster.builder()
      .addContactPoint("127.0.0.1")
      .build()

    val conn = cluster.connect("mykeyspace")


  }

}
