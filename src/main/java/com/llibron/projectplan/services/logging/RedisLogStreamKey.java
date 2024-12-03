package com.llibron.projectplan.services.logging;

public enum RedisLogStreamKey {

    REQUEST_LOGS_STREAM_KEY("request-logs"),
    RESPONSE_LOGS_STREAM_KEY("response-logs");

    private final String key;

    RedisLogStreamKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
