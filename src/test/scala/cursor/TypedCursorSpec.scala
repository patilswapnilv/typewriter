package cursor

import novoda.android.typewriter.TypeWriterSpec
import android.database.Cursor
import novoda.android.typewriter.cursor.{MyObject, TypedCursor}

import org.mockito.Mockito._
import org.mockito.Matchers._

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

    "have next" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(5)
      when(cursor.getColumnNames).thenReturn(List("test").toArray)
      TypedCursor(cursor).hasNext should be(true)
    }

    "not have next" in {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(0)
      when(cursor.getColumnNames).thenReturn(List("test").toArray)
      TypedCursor(cursor).hasNext should be(false)
    }

    "iterate correctly" in pendingUntilFixed {
      val cursor = mock[Cursor]
      when(cursor.getCount).thenReturn(2)
      when(cursor.getColumnNames).thenReturn(List("test").toArray)
      when(cursor.getString(anyInt)).thenReturn("value test")
      val tc = TypedCursor(cursor)
      tc.next().test should be("value test")
    }

    "modifiable methods" should {
      "throw an exception" in {
        val c = mock[Cursor]
        when(c.getColumnNames).thenReturn(List("one").toArray)
        val tc = new TypedCursor(c, classOf[MyObject]);

        evaluating(tc.add(null)) should produce[RuntimeException]
        evaluating(tc.add(-1, null)) should produce[RuntimeException]
        evaluating(tc.addAll(null)) should produce[RuntimeException]
        evaluating(tc.addAll(-1, null)) should produce[RuntimeException]
        evaluating(tc.clear()) should produce[RuntimeException]
        evaluating(tc.contains(null)) should produce[RuntimeException]
        evaluating(tc.containsAll(null)) should produce[RuntimeException]
        evaluating(tc.indexOf(null)) should produce[RuntimeException]
        evaluating(tc.lastIndexOf(null)) should produce[RuntimeException]
        evaluating(tc.remove()) should produce[RuntimeException]
        evaluating(tc.remove(null)) should produce[RuntimeException]
        evaluating(tc.removeAll(null)) should produce[RuntimeException]
        evaluating(tc.retainAll(null)) should produce[RuntimeException]
        evaluating(tc.set(1, null)) should produce[RuntimeException]
      }
    }

    object TypedCursor {
      def apply(cursor: Cursor) = new TypedCursor(cursor, classOf[MyObject])
    }
  }
}