

import java.util

import com.google.gson.Gson

import scala.collection.mutable
import scala.util.parsing.json.JSON

/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-01-31.
 */
object Tester {

  def main(args: Array[String]) {

//    val mapper = new ObjectMapper()

    val result = new mutable.HashMap[String, Long]()

    result.put("A", 1)
    result.put("B", 2)

    println(new Gson().toJson(result))
    println(new Gson().toJson(mutable.Map(1->2, 2->3)))
    println(new Gson().toJson(List(1,2,3,4,4)))

    val map = new util.HashMap[String, Object]()
    map.put("1", "1")
    map.put("2", "2")
    println(new Gson().toJson(map))


//    println(mapper.writeValueAsString(result))

  }
}
