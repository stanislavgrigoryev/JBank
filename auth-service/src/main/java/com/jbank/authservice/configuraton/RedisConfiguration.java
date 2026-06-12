package com.jbank.authservice.configuraton;

import com.jbank.authservice.entity.RefreshToken;
import com.jbank.authservice.properties.SecurityJwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Collections;


@Configuration
@EnableRedisRepositories(keyspaceConfiguration = RedisConfiguration.RefreshTokenKeyspaceConfiguration.class,
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
@RequiredArgsConstructor
public class RedisConfiguration {

    private final SecurityJwtProperties jwtProperties;


    @Bean
    public JedisConnectionFactory redisConnectionFactory(DataRedisProperties dataRedisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        redisStandaloneConfiguration.setHostName(dataRedisProperties.getHost());
        redisStandaloneConfiguration.setPort(dataRedisProperties.getPort());

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    public class RefreshTokenKeyspaceConfiguration extends KeyspaceConfiguration {
        private static final String REFRESH_TOKEN_KEYSPACE = "refresh_tokens";

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            KeyspaceSettings keyspaceSettings = new KeyspaceSettings(RefreshToken.class, REFRESH_TOKEN_KEYSPACE);
            keyspaceSettings.setTimeToLive(jwtProperties.getRefreshTokenExpiration().getSeconds());

            return Collections.singletonList(keyspaceSettings);
        }
    }
}
