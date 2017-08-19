package top.yuyufeng.rpc.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class RegisterServicesCenter {
    private static ConcurrentHashMap<String, Class> registerServices = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Class> getRegisterServices() {
        return registerServices;
    }

    public static void setRegisterServices(ConcurrentHashMap<String, Class> registerServices) {
        RegisterServicesCenter.registerServices = registerServices;
    }
}
