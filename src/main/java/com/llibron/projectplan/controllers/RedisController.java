package com.llibron.projectplan.controllers;

import com.llibron.projectplan.services.logging.RedisLogStreamKey;
import com.llibron.projectplan.services.logging.RedisLoggingService;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/redis/logs")
public class RedisController {

    private final RedisLoggingService redisLoggingService;

    public RedisController(RedisLoggingService redisLoggingService) {
        this.redisLoggingService = redisLoggingService;
    }

    @GetMapping("/requests")
    public List<MapRecord<String, Object, Object>> getRequestLogs() {
        return redisLoggingService.getLog(RedisLogStreamKey.REQUEST_LOGS_STREAM_KEY.getKey());
    }

    @GetMapping("/responses")
    public List<MapRecord<String, Object, Object>> getResponseLogs() {
        return redisLoggingService.getLog(RedisLogStreamKey.RESPONSE_LOGS_STREAM_KEY.getKey());
    }

}
