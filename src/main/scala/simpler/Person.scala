
package simpler

/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-03-05.
 */
case class Person(name:String, age:Int) {

  def this()={
    this("没有名字", 0)
  }

  override def toString: String ={
    s"name:$name, age:$age"
  }

}
