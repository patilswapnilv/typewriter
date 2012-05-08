import android.database.MatrixCursor
import novoda.android.typewriter.cursor.{TypedCursor, MyObject, CursorMarshaller}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec

class CursorMarshallerSpec extends WordSpec with ShouldMatchers with RoboSpecs {

  "a cursor marshaller" should {

    "throw an exception if the cursor is not moved" in {
      val c = new MatrixCursor(List("test").toArray)
      c.addRow(List("hello world".asInstanceOf[AnyRef]).toArray)
      val marshaller = new CursorMarshaller[MyObject]
      evaluating {
        marshaller.marshall(c, classOf[MyObject])
      } should produce[RuntimeException]
    }

    "marshall into MyObject" in {
      val c = new MatrixCursor(List("test").toArray)
      c.addRow(List("hello world".asInstanceOf[AnyRef]).toArray)
      val marshaller = new CursorMarshaller[MyObject]

      c.moveToFirst()
      val obj = marshaller.marshall(c, classOf[MyObject])
      obj.test should be("hello world")
    }
  }
}

class CursorListIteratorSpec extends TypeWriterSpec {
  "A cursor list iterator" should {
    "give correct size" in {

    }
  }
}

class TypedCursorSpec extends WordSpec with ShouldMatchers with RoboSpecs {
  "a typed cursor" should {
    "produce a correct type" in {
      val cursor = new MatrixCursor(List("test").toArray)
      val typedcursor = new TypedCursor[MyObject](cursor, classOf[MyObject])
      typedcursor.size() should be(0)
    }
  }
}

trait TypeWriterSpec extends WordSpec with ShouldMatchers with RoboSpecs