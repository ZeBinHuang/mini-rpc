package com.mini.rpc.serialization;

public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(byte serializationType) {
        SerializationTypeEnum typeEnum = SerializationTypeEnum.findByType(serializationType);

        switch (typeEnum) {
            case HESSIAN:
                return HessianSerialization.getInstance();
            case JSON:
                return JsonSerialization.getInstance();
            case XML:
                return XmlSerialization.getInstance();
            default:
                throw new IllegalArgumentException("serialization type is illegal, " + serializationType);
        }
    }
}
