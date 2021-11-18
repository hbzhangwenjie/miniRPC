package provide;

import static provide.Provider.beanFactory;

import api.TimeService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 主要是类比rpc服务端的网络和序列化部分
 *
 * @author jiimmy
 * @version 1.0
 * @date 2021/5/4 4:29 下午
 */

public class Server extends Thread {

    @Override
    public void run() {
        System.out.println("server start...");
        try {
            // 打开服务端Socket
            ServerSocket serverSocket = new ServerSocket(9000);
            // 接受客户端的请求
            Socket socket = serverSocket.accept();
            while (null != socket) {
                //   ObjectInputStream是java 提供的序列化/反序列化的一种工具
                ObjectInputStream inStream = null;
                try {
                    inStream =
                            new ObjectInputStream(socket.getInputStream());
                } catch (Exception e) {

                }
                if (inStream == null) {
                    continue;
                }
                String rpcInvocation = null;
                try {
                    rpcInvocation = (String) inStream.readObject();
                } catch (Exception e) {
                }
                if (rpcInvocation == null) {
                    continue;
                }
                //这里 没有实现 exchange 层
                System.out.println("server receive...:" + rpcInvocation);
                //invoker 在框架中是一个类，这里用string简单代替
                String service = rpcInvocation.split(",")[0];
                String method = rpcInvocation.split(",")[1];
                //根据客户端传入的请求 找到具体的方法，使用实现类去执行，在框架中是找到invoker invoker中代理了实现类
                // api.TimeService,在框架中通过类型和名字找到唯一的bean
                //还有入参 这里简单实现没有写。另外调用方可以不要这个接口吗，可以的。泛化调用
                TimeService timeService = (TimeService) beanFactory.get(service);
                //通过反射执行服务端实现类的方法
                Class clazz = timeService.getClass();
                Method[] methods = clazz.getMethods();
                for (Method targetMethod : methods) {
                    if (method.equals(targetMethod.getName())) {
                        long currentTimeMillis = (long) targetMethod.invoke(timeService);
                        //序列化
                        ObjectOutputStream outStream =
                                new ObjectOutputStream(socket.getOutputStream());
                        // 返回结果
                        outStream.writeLong(currentTimeMillis);
                        outStream.flush();
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
