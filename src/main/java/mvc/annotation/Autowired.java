package mvc.annotation;

import java.lang.annotation.*;

/**
 * @description: ${description}
 * @author: zhangbing
 * @create: 2020-10-20 10:28
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    String beanName() default "";
}
