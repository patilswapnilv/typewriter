package novoda.android.typewriter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import novoda.android.typewriter.cursor.CursorMarshaller;
import novoda.android.typewriter.cursor.TypedCursor;

import java.util.Arrays;

public class TypedActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        CursorMarshaller<Contact> marshaller = new CursorMarshaller<Contact>();

        long now = System.currentTimeMillis();
        while (cursor.moveToNext()) {
            cursor.getString(cursor.getColumnIndexOrThrow("display_name"));
        }
        Log.i("TEST", "NORMAL: " + (System.currentTimeMillis() - now));

        cursor.moveToFirst();

        TypedCursor<Contact> typedCursor = new TypedCursor<Contact>(cursor, Contact.class);
        now = System.currentTimeMillis();
        for (Contact c : typedCursor) {
           // Log.i("TEST", "second" + c.display_name);
        }
        Log.i("TEST", "better: " + (System.currentTimeMillis() - now));

    }

    public static class Contact {
        public Contact(){}
        public String display_name;
    }
}
