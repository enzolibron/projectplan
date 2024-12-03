package com.llibron.projectplan.services.logging;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisLoggingService {



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String logRequest(String method, String endpoint, String body) {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("method", method);
        requestData.put("endpoint", endpoint);
        requestData.put("body", body);
        return add(requestData, RedisLogStreamKey.REQUEST_LOGS_STREAM_KEY.getKey());
    }

    public void logResponse(String requestLogId, String responseStatus, String responseBody) {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("requestLogId", requestLogId);
        requestData.put("responseStatus", responseStatus);
        requestData.put("responseBody", responseBody);
        add(requestData, RedisLogStreamKey.RESPONSE_LOGS_STREAM_KEY.getKey());
    }

    public List<MapRecord<String, Object, Object>> getLog(String streamKey) {
        return range(streamKey);
    }

    private String add(Map<String, String> requestData, String streamKey) {
        RecordId recordId = redisTemplate.opsForStream().add(StreamRecords.newRecord().in(streamKey).ofMap(requestData));
        return recordId.getValue();
    }

    public List<MapRecord<String, Object, Object>> range(String streamKey) {
        return redisTemplate.opsForStream().range(streamKey, Range.unbounded());
    }
}
