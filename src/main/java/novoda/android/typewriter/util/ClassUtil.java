package novoda.android.typewriter.util;


public class ClassUtil {

    public static <T> T newInstance(Class<T> klass) {
        try {
            return klass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
