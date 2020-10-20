package mvc.parser;

import mvc.context.WebApplicationContext;

/**
 * @description: 扫描器
 * @author: zhangbing
 * @create: 2020-10-20 11:42
 **/
public abstract class Parser {

    protected WebApplicationContext applicationContext;

    protected Parser(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    abstract String parser(Class<?> aclass);

//    abstract String parserBean(Class<?> aclass);
}
