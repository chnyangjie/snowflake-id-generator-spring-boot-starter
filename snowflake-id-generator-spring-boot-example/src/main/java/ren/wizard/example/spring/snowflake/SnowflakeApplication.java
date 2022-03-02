package ren.wizard.example.spring.snowflake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ren.wizard.example.spring.snowflake", "ren.wizard.spring.snowflake"})
public class SnowflakeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnowflakeApplication.class, args);
    }
}
