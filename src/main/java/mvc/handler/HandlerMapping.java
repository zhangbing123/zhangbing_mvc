package mvc.handler;

import mvc.annotation.Controller;
import mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:处理器映射器
 * @author: zhangbing
 * @create: 2020-10-20 14:54
 **/
public class HandlerMapping {

    private Map<String, DefaultHandler> handlerMapping = new ConcurrentHashMap<>();

    public HandlerMapping(Map<String, Object> iocMap) {

        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Object bean = entry.getValue();
            Class<?> aClass = bean.getClass();

            if (aClass.isAnnotationPresent(Controller.class)) {

                StringBuilder basePath = new StringBuilder("");

                if (aClass.isAnnotationPresent(RequestMapping.class)) {
                    basePath.append("/").append(aClass.getAnnotation(RequestMapping.class).value());
                }

                for (Method method : aClass.getMethods()) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        basePath.append("/").append(method.getAnnotation(RequestMapping.class).value());
                        String url = basePath.toString().replaceAll("//", "/");
                        handlerMapping.put(url, new DefaultHandler(url, bean, method));
                    }
                }
            }
        }

    }

    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        DefaultHandler defaultHandler = handlerMapping.get(request.getRequestURI());
        if (defaultHandler == null) {
            return null;
        }

        return new HandlerExecutionChain(defaultHandler);
    }
}
