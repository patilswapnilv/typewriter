package cursor

import android.database.Cursor
import org.mockito.Mockito._
import novoda.android.typewriter.cursor.CursorListIterator
import novoda.android.typewriter.TypeWriterSpec

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
      def apply(cursor: Cursor) = new CursorListIterator[Object](cursor, null, 0)

      def apply(cursor: Cursor, index: Int) = new CursorListIterator(cursor, null, index)
    }
  }
}
