package tfip.modserver.librarysearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static tfip.modserver.librarysearch.Constants.*;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    private String pw;

    @Bean("redisDB")
    @Scope("singleton")
    public RedisTemplate<String, String> createRedisTemplate(){
        Logger logger = LoggerFactory.getLogger(RedisTemplate.class);
        logger.info("redishost: " + redisHost);
        logger.info("redisPort: " + redisPort);

        String k = System.getenv(ENV_REDIS_PW);
        if (null!=k && !k.isBlank()){
            pw = k;
        }
        else{
            pw = "";
        }

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        // config.setDatabase(redisDatabase);
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setPassword(pw);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config,jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
