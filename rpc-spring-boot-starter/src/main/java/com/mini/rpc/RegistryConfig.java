package com.mini.rpc;

import lombok.Data;

/**
 * @author zebin
 * @date 2022/3/29 18:56
 */
@Data
public class RegistryConfig {

    private String address = "127.0.0.1:2181";

    private String type = "ZOOKEEPER";
}
