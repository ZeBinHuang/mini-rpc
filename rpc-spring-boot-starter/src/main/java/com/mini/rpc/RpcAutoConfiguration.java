package com.mini.rpc;

import com.mini.rpc.consumer.RpcConsumerPostProcessor;
import com.mini.rpc.provider.RpcProvider;
import com.mini.rpc.provider.RpcProviderPostProcessor;
import com.mini.rpc.provider.registry.RegistryFactory;
import com.mini.rpc.provider.registry.RegistryService;
import com.mini.rpc.provider.registry.RegistryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zebin
 * @date 2022/3/29 10:14
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
@Slf4j
@Import(RpcConsumerPostProcessor.class)
public class RpcAutoConfiguration {

    @Autowired
    private RpcProperties rpcProperties;

    @ConditionalOnProperty(
            prefix = "rpc.protocol.",
            name = {"port"}
    )
    @Bean
    public BeanPostProcessor rpcProviderPostProcessor() throws Exception {
        log.info("register RpcProviderPostProcessor");
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistry().getType());
        RegistryService registryService = RegistryFactory.getInstance(rpcProperties.getRegistry().getAddress(), type);
        return new RpcProviderPostProcessor(rpcProperties.getProtocol().getPort(), registryService);
    }

    @ConditionalOnProperty(
            prefix = "rpc.protocol.",
            name = {"port"}
    )
    @Bean
    public RpcProvider rpcProvider() throws Exception {
        return new RpcProvider(rpcProperties.getProtocol().getPort());
    }

}
