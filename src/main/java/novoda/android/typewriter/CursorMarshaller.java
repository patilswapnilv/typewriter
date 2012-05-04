package novoda.android.typewriter;

import android.database.Cursor;

public class CursorMarshaller {

    public <T> T marshall(Cursor cursor, Class<T> what) {

    }
}


//
//    public static <T> T marshal(Cursor cur, Class<T> what) throws IllegalAccessException, InstantiationException {
//        Field[] fields = what.getFields();
//        T obj = what.newInstance();
//        List<String> columnNames = Arrays.asList(cur.getColumnNames());
//        for (Field field : fields) {
//            if (columnNames.contains(field.getName())) {
//                field.set(obj, cur.getString(cur.getColumnIndex(field.getName())));
//            }
//        }
//        return obj;
//    }
//
//    public static class Example {
//        public Example() {}
//        public String edition_id;
//        public String section_id;
//        public String _id;
//        public String some_other;
//
//        @Override
//        public String toString() {
//            return "Example{" +
//                    "edition_id='" + edition_id + '\'' +
//                    ", section_id='" + section_id + '\'' +
//                    ", _id='" + _id + '\'' +
//                    ", some_other='" + some_other + '\'' +
//                    '}';
//        }
//    }
