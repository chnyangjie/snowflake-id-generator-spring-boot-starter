package ren.wizard.spring.snowflake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@ConditionalOnBean(value = StringRedisTemplate.class, name = "id-generator")
@ConditionalOnProperty(value = "id-generator.snowflake.enable", havingValue = "true")
public class SnowflakeIdGeneratorAutoConfiguration {
    @Autowired
    @Qualifier("id-generator")
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    private UuidGenerator getUuidGenerator(){
        return new UuidGenerator();
    }
}
