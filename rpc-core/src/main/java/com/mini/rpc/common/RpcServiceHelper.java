package com.mini.rpc.common;

import java.util.HashMap;
import java.util.Map;

public class RpcServiceHelper {

    private static final Map<String, Object> rpcServiceMap = new HashMap<>();

    public static void put(String key, Object service) {
        rpcServiceMap.put(key, service);
    }

    public static Object get(String key) {
        return rpcServiceMap.get(key);
    }

    public static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join("#", serviceName, serviceVersion);
    }

}
