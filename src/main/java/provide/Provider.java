package provide;

import java.util.HashMap;

/**
 * @author jiimmy
 * @version 1.0
 * @date 2021/5/4 4:42 下午
 */

public class Provider {

    public static final HashMap<String, Object> beanFactory = new HashMap<>();

    public static void main(String[] args) {
        //类比框架加载服务端的实现类, 服务暴露 框架中通过扫描注解（@dubboService）帮我们做 我们只需要 关注TimeServiceImpl
        beanFactory.put("api.TimeService", new TimeServiceImpl());
        //类比rpc框架 启动 监听一个端口
        Server server = new Server();
        server.start();
    }
}
