package mvc.handler;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-10-20 14:49
 **/
public class HandlerExecutionChain {

    private Handler handler;

    public HandlerExecutionChain(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

    public Object invoke(String... args){
        return handler.invoke(args);
    }
}
