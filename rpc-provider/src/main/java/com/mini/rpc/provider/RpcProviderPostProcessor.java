package com.mini.rpc.provider;

import com.mini.rpc.common.RpcServiceHelper;
import com.mini.rpc.common.ServiceMeta;
import com.mini.rpc.provider.annotation.RpcService;
import com.mini.rpc.provider.registry.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.net.InetAddress;

/**
 * @author zebin
 * @date 2022/3/29 10:24
 */
@Slf4j
public class RpcProviderPostProcessor implements BeanPostProcessor {

    private final String serverAddress;
    private final int serverPort;
    private final RegistryService registryService;

    public RpcProviderPostProcessor(int serverPort, RegistryService registryService) throws Exception {
        this.serverAddress = InetAddress.getLocalHost().getHostAddress();
        this.serverPort = serverPort;
        this.registryService = registryService;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            // 类的全限定名
            String serviceName = rpcService.serviceInterface().getName();
            String serviceVersion = rpcService.serviceVersion();

            try {
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServiceAddr(serverAddress);
                serviceMeta.setServicePort(serverPort);
                serviceMeta.setServiceName(serviceName);
                serviceMeta.setServiceVersion(serviceVersion);

                registryService.register(serviceMeta);
                RpcServiceHelper.put(RpcServiceHelper.buildServiceKey(serviceMeta.getServiceName(), serviceMeta.getServiceVersion()), bean);
            } catch (Exception e) {
                log.error("failed to register service {}#{}", serviceName, serviceVersion, e);
            }
        }
        return bean;
    }
}
