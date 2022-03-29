package com.mini.rpc.serialization;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author zebin
 * @date 2022/3/15 21:25
 */
public class XmlSerialization implements RpcSerialization {

    private static volatile XmlSerialization xmlSerialization;

    private XmlSerialization() {}

    public static XmlSerialization getInstance() {
        if (xmlSerialization == null) {
            synchronized (XmlSerialization.class) {
                if (xmlSerialization == null) {
                    return new XmlSerialization();
                }
            }
        }
        return xmlSerialization;
    }

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        XStream xStream = new XStream();
        return xStream.toXML(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        XStream xStream = new XStream();
        return (T)xStream.fromXML(Arrays.toString(data));
    }
}
