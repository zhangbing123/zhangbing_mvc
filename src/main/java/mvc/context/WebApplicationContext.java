package mvc.context;

import mvc.factory.BeanFactory;
import mvc.parser.AnnotationParser;
import mvc.tomcat.TomcatStarter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description: 应用上线文  ioc容器
 * @author: zhangbing
 * @create: 2020-10-20 11:02
 **/
public class WebApplicationContext extends BeanFactory {

    private static int startPort = 8080;//启动端口

    //已经处理过的className
    private Set<String> processedList = new HashSet<>();

    private WebApplicationContext(Class<?> aClass) {
        annotationParser = new AnnotationParser(this);
        annotationParser.parser(aClass);
        initBean();
    }

    private void initBean() {

        if (!beanDefinitionMap.isEmpty()) {
            createBean();
        }
    }

    public static void run(Class<?> mainClass, String... args) {
        WebApplicationContext webApplicationContext = new WebApplicationContext(mainClass);
        webApplicationContext.start(webApplicationContext);
    }

    private void start(WebApplicationContext applicationContext) {
        new TomcatStarter(applicationContext, startPort).start();
    }

    public void registeBeanDefinition(String beanName, Class<?> aClass) {
        beanDefinitionMap.put(beanName, aClass);
    }

    public Object registeBean(String beanName, Object instance) {
        iocMap.put(beanName, instance);
        return instance;
    }

    public boolean isContain(String className) {
        for (String name : processedList) {
            if (name.equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static void setPort(int port) {
        startPort = port;
    }

    public Map<String, Object> getIoc() {
        return iocMap;
    }

    public void markProcessed(String className) {
        processedList.add(className);
    }

}
