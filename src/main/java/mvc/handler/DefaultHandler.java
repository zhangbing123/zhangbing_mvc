package mvc.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-10-20 14:34
 **/
public class DefaultHandler implements Handler {

    private String url;

    private Object object;

    private Method method;

    public DefaultHandler(String url, Object object, Method method) {
        this.url = url;
        this.object = object;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public Object invoke(Object... args) {
        try {
            return args == null ? method.invoke(object) : method.invoke(object, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
