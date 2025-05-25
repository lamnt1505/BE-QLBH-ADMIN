package com.vnpt.mini_project_java.util;

import java.util.Base64;

public class Base64Util {
    public String encode(String data) {
        return Base64.getUrlEncoder().encodeToString(data.getBytes());
    }

    public String decode(String encodedData) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedData);
        return new String(decodedBytes);
    }
}
