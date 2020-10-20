package mvc.handler;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-10-20 15:02
 **/
public interface Handler {

    Object invoke(Object... args);
}
