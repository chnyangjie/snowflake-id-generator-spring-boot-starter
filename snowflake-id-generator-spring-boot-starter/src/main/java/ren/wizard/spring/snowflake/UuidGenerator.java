package ren.wizard.spring.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

public class UuidGenerator {
    public static final Logger LOGGER = LoggerFactory.getLogger(UuidGenerator.class);
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisScript<Long> script;
    private final String clusterId;

    public UuidGenerator(StringRedisTemplate stringRedisTemplate, String luaScript, int clusterId) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.script = RedisScript.of(luaScript, Long.class);
        this.clusterId = String.valueOf(100 + Math.abs(clusterId % 16)).substring(1);
        LOGGER.info("snowflake, script loaded, sha: {}", this.script.getSha1());

    }

    public Long nextId(int projectId) {
        int sProjectId = 100 + Math.abs(projectId % 16);
        String key = "KEY:" + String.valueOf(sProjectId).substring(1) + ":" + this.clusterId;
        LOGGER.debug("snowflake, generate next id, key: {}, sproject: {}, project: {}, cluster: {}", key, sProjectId, projectId, clusterId);
        Long result = this.stringRedisTemplate.execute(this.script, Collections.singletonList(key));
        LOGGER.debug("snowflake, generate next id, key: {}, sproject: {}, project: {}, cluster: {} , result: {}", key, sProjectId, projectId, clusterId, result);
        return result;
    }
}
