package cells.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties("app.cache")
public class CacheConfig {
    public String cacheKey;
    public Duration expirationDuration;
}
