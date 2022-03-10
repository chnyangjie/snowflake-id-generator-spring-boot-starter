# snowflake-id-generator-spring-boot-starter
Snowflake ID Generator Spring Boot starter

## How To Use
pom.xml
```xml
<repositories>
        <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
        </repository>
</repositories>
```
pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.github.chnyangjie</groupId>
        <artifactId>snowflake-id-generator-spring-boot-starter</artifactId>
        <version>{version-of-id-generator}</version>
    </dependency>
</dependencies>
```


## TODO
1. add configuration properties
2. use configuration properties to init redis instance when `id-generator` bean is not init