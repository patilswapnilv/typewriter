package introspection

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import reflect.BeanProperty
import novoda.android.typewriter.introspection.RichClass
import novoda.android.typewriter.annotation.Mapper

class RichClassSpec extends WordSpec with ShouldMatchers {
  "a rich class" should {

    "find correct setter" in {
      RichClass().setter("name") should be(method("setName", classOf[String]))
      RichClass().setter("name_camel_case") should be(method("setNameCamelCase", classOf[String]))
    }

    "throw an exception if method not found" in {
      evaluating(
        RichClass().setter("name_not_found")
      ) should produce[NoSuchMethodException]
    }

    "find correct type setter" in {
      RichClass().setter("name") should be(method("setName", classOf[String]))
      RichClass().setter("id") should be(method("setId", classOf[Int]))
      RichClass().setter("short") should be(method("setShort", classOf[Short]))
      RichClass().setter("long") should be(method("setLong", classOf[Long]))
      RichClass().setter("float") should be(method("setFloat", classOf[Float]))
    }

    "map the correct setter using annotation" in {
      RichClass().setter("some_value") should be(method("mapped", classOf[String]))

    }

    def method(mn: String, c: Class[_]) = {
      classOf[RichClass.TestObject].getMethod(mn, c)
    }

    object RichClass {

      case class TestObject (
        @BeanProperty var nameCamelCase: String ,
        @BeanProperty var name: String ,
        @BeanProperty var id: Int ,
        @BeanProperty var short: Short ,
        @BeanProperty var long: Long,
        @BeanProperty var float: Float ,
        @BeanProperty @Mapper("some_value") var mappedString: String
      ) {
        @Mapper("some_value")
        def mapped(s:String) {}
      }

      def apply() = new RichClass(classOf[TestObject])
    }
  }
}