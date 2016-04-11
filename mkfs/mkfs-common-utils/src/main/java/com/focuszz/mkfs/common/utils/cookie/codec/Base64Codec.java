package com.focuszz.mkfs.common.utils.cookie.codec;

import org.apache.commons.codec.binary.Base64;

public class Base64Codec implements Codec {

    public String encode(String value) {
        return value == null ? null : new String(Base64.encodeBase64(value.getBytes()));
    }

    public String decode(String value) {
        return value == null ? null : new String(Base64.decodeBase64(value.getBytes()));
    }

}
