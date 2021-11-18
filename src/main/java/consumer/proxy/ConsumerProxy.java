package consumer.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;


/**
 * 这个类 在rpc框架中 自动生成
 *
 * @author jiimmy
 * @version 1.0
 * @date 2021/5/4 5:02 下午
 */

public class ConsumerProxy implements InvocationHandler {

    private Socket socket;
    private Invoker invoker;

    public  ConsumerProxy(Invoker invoker) {
        this.invoker = invoker;
        try {
            socket = new Socket("127.0.0.1", 9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ObjectOutputStream outStream =
                new ObjectOutputStream(socket.getOutputStream());
        //通过反射来构造 invoker，invoker还包括了，args 这个里 简单实现没有用到
        String rpcInvocation = invoker.getInterfaces().getTypeName() + "," + method.getName();
        outStream.writeObject(rpcInvocation);
        outStream.flush();
        ObjectInputStream inStream =
                new ObjectInputStream(socket.getInputStream());
        return inStream.readLong();
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{invoker.getInterfaces()},
                this
        );
    }
}
