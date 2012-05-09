import android.database.{Cursor, MatrixCursor}
import novoda.android.typewriter.cursor.{CursorListIterator, TypedCursor, MyObject, CursorMarshaller}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
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

import org.mockito.Mockito._

class CursorListIteratorSpec extends TypeWriterSpec {
  "A cursor list iterator" should {

    "ensure it can iterate if position is within size with hasNext" in {
      (0 until 9).foreach {
        i =>
          val cursor = mock[Cursor]
          when(cursor.getCount).thenReturn(10)
          when(cursor.getPosition).thenReturn(i)
          new CursorListIterator(cursor, null, 0).hasNext should be(true)
      }
    }

    "ensure it does not have next if position is count - 1" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(10)
      when(cursor.getPosition).thenReturn(9)
      new CursorListIterator(cursor, null, 0).hasNext should be(false)
    }

    "have previous if cursor is above 1" in {
      (1 until 10).foreach {
        i =>
          val cursor = mock[Cursor]
          when(cursor.getCount).thenReturn(10)
          when(cursor.getPosition).thenReturn(i)
          new CursorListIterator(cursor, null, 0).hasPrevious should be(true)
      }
    }

    "does not have previous if position is 0" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(10)
      when(cursor.getPosition).thenReturn(0)
      new CursorListIterator(cursor, null, 0).hasPrevious should be(false)
    }

    "return the next index" in {
      val cursor = mock[Cursor]
      when(cursor.getPosition).thenReturn(5)
      new CursorListIterator(cursor, null, 0).nextIndex should be(6)
    }

    "return the previous index" in {
      val cursor = mock[Cursor]
      when(cursor.getPosition).thenReturn(5)
      new CursorListIterator(cursor, null, 0).previousIndex() should be(4)
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

trait TypeWriterSpec extends WordSpec with ShouldMatchers with MockitoSugar with RoboSpecs