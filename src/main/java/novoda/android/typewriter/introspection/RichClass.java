package novoda.android.typewriter.introspection;

import novoda.android.typewriter.annotation.Mapper;
import novoda.android.typewriter.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RichClass {

    private final Class klass;

    private List<Method> methods;

    private Map<String, String> fieldMapper;

    private Map<String, Method> methodMapper;

    public RichClass(Class klass) {
        this.klass = klass;
        methods = Arrays.asList(klass.getMethods());

        fieldMapper = new HashMap<String, String>();
        for (Field f : klass.getDeclaredFields()) {
            Mapper s = f.getAnnotation(Mapper.class);
            if (s != null) {
                fieldMapper.put(s.value(), f.getName());
            }
        }

        methodMapper = new HashMap<String, Method>();
        for (Method m : methods) {
            Mapper mm = m.getAnnotation(Mapper.class);
            if (mm != null) {
                methodMapper.put(mm.value(), m);
            }
        }
    }

    public Method setter(String what) throws NoSuchMethodException {
        if (methodMapper.containsKey(what)) {
            return methodMapper.get(what);
        }

        String methodName = StringUtil.asCamelifySetMethod(what);
        if (fieldMapper.containsKey(what)) {
            methodName = fieldMapper.get(what);
        }

        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        throw new NoSuchMethodException("can not find method " + what);
    }
}
