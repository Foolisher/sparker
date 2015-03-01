import java.io.{FileReader, StringReader}

import au.com.bytecode.opencsv.CSVReader
import org.supercsv.cellprocessor.ift.CellProcessor
import org.supercsv.io.{CsvBeanReader, CsvMapReader}
import org.supercsv.prefs.CsvPreference
import org.supercsv.util.CsvContext

/**
 * <pre>
 * 功能描述: 
 * </pre>
 * @author wanggen on 2015-01-31.
 */
object Tester {

  def main(args: Array[String]) {

    val reader = new CSVReader(new FileReader("/Users/wanggen/Downloads/part-m-00000"))



  }
}
