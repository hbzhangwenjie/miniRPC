package consumer;

import api.TimeService;
import consumer.proxy.ConsumerProxy;
import consumer.proxy.Invoker;

/**
 * @author jiimmy
 * @version 1.0
 * @date 2021/5/4 5:08 下午
 *对 dubbo 源码的 解析：https://blog.csdn.net/z530065424/article/details/121399576?spm=1001.2014.3001.5501
 */

public class Consumer {

    public static void main(String[] args) {
        //服务调用方是没有实现类的 只有一个接口，框架通过@dubboRefrence 为每一个 接口生成一个代理对象
        Invoker invoker = new Invoker(TimeService.class);
        ConsumerProxy consumerProxy = new ConsumerProxy(invoker);
        //这个客户端是类比框架根据接口代理生成的一个实现类，它把网络代理进去 ，使用方无感知
        //在dubbo 中是框架启动时帮我们生成的这个代理类，通过扫描@dubboReference 这2个注解来实现
        TimeService timeService = (TimeService) consumerProxy.getProxy();

        for (int i = 0; i < 10; i++) {
            System.out.println("consumer response:" + timeService.getCurrentTimeMillis());
        }
    }
}
