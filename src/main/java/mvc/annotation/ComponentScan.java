package mvc.annotation;

import java.lang.annotation.*;

/**
 * @description: ${description}
 * @author: zhangbing
 * @create: 2020-10-20 11:23
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

    String packages();
}
