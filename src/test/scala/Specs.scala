import android.database.{Cursor, MatrixCursor}
import novoda.android.typewriter.cursor.{CursorListIterator, TypedCursor, MyObject, CursorMarshaller}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.WordSpec

import org.mockito.Mockito._

class CursorMarshallerSpec extends TypeWriterSpec {

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

    "should use setter method" in {
    }

  }
}


class CursorListIteratorSpec extends TypeWriterSpec {
  "A cursor list iterator" should {

    "ensure it can iterate if position is within size with hasNext" in {
      (0 until 9).foreach {
        i =>
          val cursor = mock[Cursor]
          when(cursor.getCount).thenReturn(10)
          when(cursor.getPosition).thenReturn(i)
          CursorListIterator(cursor).hasNext should be(true)
      }
    }

    "ensure it does not have next if position is count - 1" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(10)
      when(cursor.getPosition).thenReturn(9)
      CursorListIterator(cursor).hasNext should be(false)
    }

    "have previous if cursor is above 1" in {
      (1 until 10).foreach {
        i =>
          val cursor = mock[Cursor]
          when(cursor.getCount).thenReturn(10)
          when(cursor.getPosition).thenReturn(i)
          CursorListIterator(cursor).hasPrevious should be(true)
      }
    }

    "does not have previous if position is 0" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(10)
      when(cursor.getPosition).thenReturn(0)
      CursorListIterator(cursor).hasPrevious should be(false)
    }

    "return the next index" in {
      val cursor = mock[Cursor]
      when(cursor.getPosition).thenReturn(5)
      CursorListIterator(cursor).nextIndex should be(6)
    }

    "return the previous index" in {
      val cursor = mock[Cursor]
      when(cursor.getPosition).thenReturn(5)
      CursorListIterator(cursor).previousIndex() should be(4)
    }

    "not return have previous if index is set" in {
      val cursor = mock[Cursor]
      when(cursor.getPosition).thenReturn(5)
      CursorListIterator(cursor, 5).hasPrevious should be(false)
    }

    "throw an exceptio for modification method" in {
      val cursor = mock[Cursor]
      evaluating(
        CursorListIterator(cursor).add("")
      ) should produce[RuntimeException]

      evaluating(
        CursorListIterator(cursor).set("")
      ) should produce[RuntimeException]

      evaluating(
        CursorListIterator(cursor).remove()
      ) should produce[RuntimeException]
    }

    object CursorListIterator {
      def apply(cursor: Cursor) = new CursorListIterator[Object](cursor, null, null, 0)

      def apply(cursor: Cursor, index: Int) = new CursorListIterator(cursor, null, null, index)
    }
  }
}

class TypedCursorSpec extends TypeWriterSpec {

  "a typed cursor" should {

    "give empty count" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(0)
      when(cursor.getColumnNames).thenReturn(List("one").toArray)
      TypedCursor(cursor).size should be(0)
    }

    "give correct count" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(5)
      when(cursor.getColumnNames).thenReturn(List("one").toArray)
      TypedCursor(cursor).size should be(5)
    }

    object TypedCursor {
      def apply(cursor: Cursor) = new TypedCursor(cursor, classOf[MyObject])
    }
  }
}

trait TypeWriterSpec extends WordSpec with ShouldMatchers with MockitoSugar with RoboSpecs