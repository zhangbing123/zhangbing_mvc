package mvc.parser;

import mvc.annotation.Autowired;
import mvc.annotation.ComponentScan;
import mvc.annotation.Controller;
import mvc.annotation.Service;
import mvc.context.WebApplicationContext;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 注解解析器
 * @author: zhangbing
 * @create: 2020-10-20 11:26
 **/
public class AnnotationParser extends Parser {


    public AnnotationParser(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }


    @Override
    public String parser(Class<?> aClass) {
        String beanName = aClass.getSimpleName().substring(0, 1).toLowerCase() + aClass.getSimpleName().substring(1);

        if (aClass.isAnnotationPresent(ComponentScan.class) && !applicationContext.isContain(beanName)) {
            //如果有ComponentScan注解
            ComponentScan annotation = aClass.getAnnotation(ComponentScan.class);
            String packages = annotation.packages();
            applicationContext.registeBeanDefinition(beanName, aClass);
            applicationContext.markProcessed(beanName);
            //扫描包
            scanPackage(packages);
        } else if (aClass.isAnnotationPresent(Controller.class)) {
            applicationContext.registeBeanDefinition(beanName, aClass);
        } else if (aClass.isAnnotationPresent(Service.class)) {
            Service annotation = aClass.getAnnotation(Service.class);
            String name = annotation.beanName();
            if (StringUtils.isNotEmpty(name)) {
                beanName = name;
            }
            applicationContext.registeBeanDefinition(beanName, aClass);
        }
        applicationContext.markProcessed(beanName);
        return beanName;
    }

    public List<String> processAutowired(Object o) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        List<String> className = new ArrayList<>();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {

            if (declaredField.isAnnotationPresent(Autowired.class)) {

                Autowired annotation = declaredField.getAnnotation(Autowired.class);

                String s = annotation.beanName();
                if (StringUtils.isEmpty(s)) {
                    s = declaredField.getGenericType().getTypeName();
                }
                Object bean = applicationContext.getBean(s);
                declaredField.setAccessible(true);
                declaredField.set(o, bean);
            }
        }
        return className;
    }

    private void scanPackage(String pack) {
        URL url = this.getClass().getClassLoader().getResource("./" + pack.replaceAll("\\.", "/"));
        String path = url.getPath();
        File files = new File(path);
        try {
            for (File file : files.listFiles()) {
                if (file.isDirectory()) {
                    scanPackage(pack + "." + file.getName());
                } else {
                    //文件目录下文件  获取全路径   UserController.class  ==> com.baiqi.controller.UserController
                    String className = pack + "." + file.getName().replaceAll(".class", "");
                    Class<?> aClass = Class.forName(className);
                    parser(aClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
