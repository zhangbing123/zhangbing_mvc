package mvc.annotation;

import java.lang.annotation.*;

/**
 * @description: ${description}
 * @author: zhangbing
 * @create: 2020-10-20 10:29
 **/
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value();
}
