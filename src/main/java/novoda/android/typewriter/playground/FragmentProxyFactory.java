package novoda.android.typewriter.playground;

import java.lang.reflect.Proxy;

public class FragmentProxyFactory {

    public static <T> T getProxy(Class<T> intf, final T obj) {
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                new Class[]{intf},
                new FragmentInvocationHandler());
    }
}
