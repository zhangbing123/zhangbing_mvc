package com.zb;

import mvc.annotation.ComponentScan;
import mvc.context.WebApplicationContext;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-10-20 10:17
 **/
@ComponentScan(packages = "com.zb")
public class MvcApplication {

    public static void main(String[] args) {
        WebApplicationContext.setPort(8085);
        WebApplicationContext.run(MvcApplication.class, args);
    }
}
