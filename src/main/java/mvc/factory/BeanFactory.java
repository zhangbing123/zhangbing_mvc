package mvc.factory;

import mvc.parser.AnnotationParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-10-20 11:47
 **/
public class BeanFactory {

    //beanName名称
    protected Map<String, Class> beanDefinitionMap = new ConcurrentHashMap<>();

    protected AnnotationParser annotationParser;
    //定义Ioc容器
    protected Map<String, Object> iocMap = new ConcurrentHashMap<>();

    protected void createBean() {
        Set<String> beanNames = beanDefinitionMap.keySet();
        try {
            for (String beanName : beanNames) {
                getBean(beanName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Object o = iocMap.get(beanName);
        if (o == null) {

            if (!beanDefinitionMap.containsKey(beanName)) {
                beanName = processDependentOn(beanName);
            }

            Class aClass = beanDefinitionMap.get(beanName);

            if (aClass == null) throw new RuntimeException("not found the bean：" + beanName);

            if (aClass.isInterface()) {
                beanName = getInterfaceImpBeanName(aClass);
                aClass = beanDefinitionMap.get(beanName);
            }

            o = aClass.newInstance();
            iocMap.put(beanName, o);
            processAutowired(o);

        }
        return o;
    }

    private String processDependentOn(String beanName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName(beanName);
        return getInterfaceImpBeanName(aClass);
    }


    private void processAutowired(Object o) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        annotationParser.processAutowired(o);
    }

    //如果是接口  则取其实现类的beanName
    private String getInterfaceImpBeanName(Class aClass) throws IllegalAccessException, InstantiationException {
        List<String> classList = new ArrayList<>();
        for (Map.Entry<String, Class> entry : beanDefinitionMap.entrySet()) {
            if (aClass.isAssignableFrom(entry.getValue())) {
                classList.add(entry.getKey());
            }
        }

        if (classList.size() > 1) {
            throw new RuntimeException("the bean class:" + aClass.getName() + " is conflict");
        }

        return classList.get(0);
    }

}
