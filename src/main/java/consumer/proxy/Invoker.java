package consumer.proxy;

/**
 * @author jiimmy
 * @version 1.0
 * @date 2021/5/10 8:32 下午
 */

public class Invoker {
    Class<?> interfaces;

    public Invoker(Class<?> interfaces) {
        this.interfaces = interfaces;
    }

    public Class<?> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class<?> interfaces) {
        this.interfaces = interfaces;
    }
}
