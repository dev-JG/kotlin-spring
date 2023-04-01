package com.jg.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableCaching
class RedisConfig (
    @Value("\${spring.redis.host}")
    private val host: String,
    @Value("\${spring.redis.port}")
    private val port: Int,
    @Value("\${spring.redis.password}")
    private val password: String
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = host
        redisStandaloneConfiguration.port = port
        redisStandaloneConfiguration.setPassword(password)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String>? {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }
//    @Bean
//    fun cacheManager(): CacheManager {
//        val objectMapper = ObjectMapper()
//            .registerModule(JavaTimeModule())
//            .activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder()
//                    .allowIfBaseType(Any::class.java).build(), ObjectMapper.DefaultTyping.EVERYTHING
//            )
//
//        val redisCacheConfiguration: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
//            .serializeValuesWith(
//                RedisSerializationContext.SerializationPair
//                    .fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper))
//            )
//            .entryTtl(Duration.ofMinutes(1L))
//
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory())
//            .cacheDefaults(redisCacheConfiguration).build()
//    }
}