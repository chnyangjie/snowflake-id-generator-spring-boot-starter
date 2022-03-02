package ren.wizard.example.spring.snowflake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ren.wizard.spring.snowflake.UuidGenerator;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UuidGenerator uuidGenerator;

    @DeleteMapping("/key")
    public ResponseEntity<Boolean> deleteKey(String key) {
        return ResponseEntity.ok(stringRedisTemplate.delete(key));
    }

    @PostMapping("/key")
    public ResponseEntity<Boolean> upsert(@RequestParam String key, @RequestParam String value, @RequestParam(value = "ttl", defaultValue = "60", required = false) Long ttl) {
        stringRedisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/key")
    public ResponseEntity<String> get(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/id")
    public ResponseEntity<String> getId(@RequestParam(value = "service", defaultValue = "1", required = false) int service) {
        return ResponseEntity.ok(String.valueOf(uuidGenerator.nextId(service)));
    }
}
