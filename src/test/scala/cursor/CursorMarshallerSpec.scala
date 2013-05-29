package cursor

import android.database.Cursor
import novoda.android.typewriter.cursor.{MyObject, CursorMarshaller}
import novoda.android.typewriter.TypeWriterSpec

import org.mockito.Mockito._
import org.mockito.Matchers._

class CursorMarshallerSpec extends TypeWriterSpec {

  "a cursor marshaller" should {

    "throw an exception if the cursor is not moved (position -1)" in {
      val c = mock[Cursor]
      when(c.getPosition).thenReturn(-1)
      val marshaller = new CursorMarshaller[MyObject](c, classOf[MyObject])
      evaluating {
        marshaller.marshall(c, classOf[MyObject])
      } should produce[RuntimeException]
    }

    "marshall string into MyObject" in {
      val c = mock[Cursor]
      when(c.getType(anyInt)).thenReturn(Cursor.FIELD_TYPE_STRING)
      when(c.getColumnNames).thenReturn(Array("test"))
      when(c.getString(anyInt)).thenReturn("hello world")
      val marshaller = new CursorMarshaller[MyObject](c, classOf[MyObject])
      val obj = marshaller.marshall(c, classOf[MyObject])
      obj.test should be("hello world")
    }

    "marshall float correctly" in {
      val c = mock[Cursor]
      when(c.getType(anyInt)).thenReturn(Cursor.FIELD_TYPE_FLOAT)
      when(c.getColumnNames).thenReturn(Array("myFloat"))
      when(c.getFloat(anyInt)).thenReturn(1.0f)
      val marshaller = new CursorMarshaller[MyObject](c, classOf[MyObject])
      val obj = marshaller.marshall(c, classOf[MyObject])
      obj.myFloat should be(1.0f)
    }

    "marshall Integer correctly" in {
      val c = mock[Cursor]
      when(c.getType(anyInt)).thenReturn(Cursor.FIELD_TYPE_INTEGER)
      when(c.getColumnNames).thenReturn(Array("myInt"))
      when(c.getInt(anyInt)).thenReturn(1)
      val marshaller = new CursorMarshaller[MyObject](c, classOf[MyObject])
      val obj = marshaller.marshall(c, classOf[MyObject])
      obj.myInt should be(1)
    }

    "marshall long correctly" in {
      val c = mock[Cursor]
      when(c.getType(anyInt)).thenReturn(Cursor.FIELD_TYPE_INTEGER)
      when(c.getColumnNames).thenReturn(Array("_id"))
      when(c.getLong(anyInt)).thenReturn(Long.MaxValue)
      val marshaller = new CursorMarshaller[MyObject](c, classOf[MyObject])
      val obj = marshaller.marshall(c, classOf[MyObject])
      obj.id should be(Long.MaxValue)
    }

  }
}