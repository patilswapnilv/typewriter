package introspection

import org.scalatest.WordSpec
import reflect.BeanProperty
import novoda.android.typewriter.introspection.RichClass
import novoda.android.typewriter.annotation.Mapper
import novoda.android.typewriter.cursor.MyObject
import org.scalatest.matchers._

trait RichClassMatcher {
  def method(expectedValue: String) =
    new HavePropertyMatcher[RichClass, String] {
      def apply(klass: RichClass) =
        HavePropertyMatchResult(
          klass.hasMethod(expectedValue) == true,
          "method",
          expectedValue,
          klass.getClass.getMethods.toString
        )
    }
}

class RichClassSpec extends WordSpec with ShouldMatchers with RichClassMatcher {
  "a rich class" should {

    "find correct setter" in {
      RichClass().setter("name") should be(getMethod("setName", classOf[String]))
      RichClass().setter("name_camel_case") should be(getMethod("setNameCamelCase", classOf[String]))
    }

    "throw an exception if getMethod not found" in {
      evaluating(
        RichClass().setter("name_not_found")
      ) should produce[NoSuchMethodException]
    }

    "find correct type setter" in {
      RichClass().setter("name") should be(getMethod("setName", classOf[String]))
      RichClass().setter("id") should be(getMethod("setId", classOf[Int]))
      RichClass().setter("short") should be(getMethod("setShort", classOf[Short]))
      RichClass().setter("long") should be(getMethod("setLong", classOf[Long]))
      RichClass().setter("float") should be(getMethod("setFloat", classOf[Float]))
    }

    "map the correct setter using annotation" in {
      RichClass().setter("some_value") should be(getMethod("mapped", classOf[String]))
      new RichClass(classOf[MyObject]).setter("some_value") should be(classOf[MyObject].getMethod("setTest2", classOf[String]))
    }

    "have the getMethod" in {
      List("name_camel_case", "name", "id", "short", "long", "float", "some_value").foreach(
        (methodName) => RichClass() should have(method(methodName))
      )
    }

    def getMethod(mn: String, c: Class[_]) = {
      classOf[RichClass.TestObject].getMethod(mn, c)
    }

    object RichClass {

      case class TestObject(
                             @BeanProperty var nameCamelCase: String,
                             @BeanProperty var name: String,
                             @BeanProperty var id: Int,
                             @BeanProperty var short: Short,
                             @BeanProperty var long: Long,
                             @BeanProperty var float: Float,
                             @BeanProperty @Mapper("some_value") var mappedString: String
                             ) {
        @Mapper("some_value")
        def mapped(s: String) {}
      }

      def apply() = new RichClass(classOf[TestObject])
    }


  }
}