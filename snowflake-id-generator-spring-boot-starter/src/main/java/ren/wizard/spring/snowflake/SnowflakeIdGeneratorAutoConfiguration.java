package ren.wizard.spring.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnProperty(value = "spring.snowflake.redis.enable")
public class SnowflakeIdGeneratorAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(SnowflakeIdGeneratorAutoConfiguration.class);

    @Bean
    @ConditionalOnBean(value = StringRedisTemplate.class, name = "id-generator")
    @ConditionalOnMissingBean(value = UuidGenerator.class)
    UuidGenerator getUuidGenerator(@Qualifier("id-generator") StringRedisTemplate stringRedisTemplate) {
        LOGGER.info("snowflake, init uuid generator");
        String luaScript = null;
        try (InputStream i = getClass().getResourceAsStream("/snowflake.lua")) {
            if (i == null) {
                throw new RuntimeException("snowflake, stream is null");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(i, StandardCharsets.UTF_8));
            luaScript = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
        } catch (Exception e) {
            LOGGER.error("snowflake, load lua script failed", e);
        }
        return new UuidGenerator(stringRedisTemplate, luaScript, 0);
    }
}
