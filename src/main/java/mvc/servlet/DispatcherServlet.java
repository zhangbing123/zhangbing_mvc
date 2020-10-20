package mvc.servlet;

import com.alibaba.fastjson.JSON;
import mvc.context.WebApplicationContext;
import mvc.handler.HandlerExecutionChain;
import mvc.handler.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: DispatcherServlet 核心Servlet
 * @author: zhangbing
 * @create: 2020-10-20 10:44
 **/
public class DispatcherServlet extends HttpServlet {

    private WebApplicationContext context;

    private HandlerMapping handlerMapping;


    public DispatcherServlet(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatcher(req, resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HandlerExecutionChain handlerChain = handlerMapping.getHandler(req);

        if (handlerChain == null) {
            resp.getWriter().print("<h1>404 NOT FOUND!</h1>");
        } else {
            Object invoke = handlerChain.invoke(null);

            try {
                resp.setContentType("text/html;charset=UTF-8");
                if (invoke instanceof String) {
                    resp.getWriter().print((String) invoke);
                } else if (invoke instanceof Object) {
                    resp.getWriter().print(JSON.toJSON(invoke));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void init() throws ServletException {
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        this.handlerMapping = new HandlerMapping(context.getIoc());
    }
}
