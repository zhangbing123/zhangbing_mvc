package mvc.tomcat;

import mvc.context.WebApplicationContext;
import mvc.servlet.DispatcherServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @description: tomcat启动器
 * @author: zhangbing
 * @create: 2020-10-20 14:24
 **/
public class TomcatStarter extends Tomcat {

    private WebApplicationContext applicationContext;

    public static final String CONTEXT_PATH = "";
    public static final String SERVLET_NAME = "dispatcherServlet";

    public TomcatStarter(WebApplicationContext applicationContext, int port) {
        this.applicationContext = applicationContext;
        this.port = port;
    }

    public void start() {
        getHost().setAutoDeploy(false);
        //创建上下文
        StandardContext context = new StandardContext();
        context.setPath(CONTEXT_PATH);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        getHost().addChild(context);
        //创建Servlet
        addServlet(CONTEXT_PATH, SERVLET_NAME, new DispatcherServlet(applicationContext));
        //servlet映射
        context.addServletMappingDecoded("/", SERVLET_NAME);
        try {
            super.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        //异步接受请求
        System.out.println("started on port(s) " + port + " (http) with context path " + context.getPath());
        getServer().await();
    }
}
