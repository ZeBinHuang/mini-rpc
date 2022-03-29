package com.mini.rpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author zebin
 * @date 2022/3/29 11:25
 */
@Data
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {

    @NestedConfigurationProperty
    private ProtocolConfig protocol = new ProtocolConfig();

    @NestedConfigurationProperty
    private RegistryConfig registry = new RegistryConfig();

}