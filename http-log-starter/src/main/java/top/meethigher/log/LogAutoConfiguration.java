package top.meethigher.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.meethigher.log.handler.LogHandler;

/**
 * @author chenchuancheng github.com/meethigher
 * @since 2023/5/14 12:03
 */
@Configuration
@ConditionalOnClass(Log.class)
public class LogAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(LogAutoConfiguration.class);

    @Bean
    public LogHandler logHandler() {
        LogHandler logHandler = new LogHandler();
        log.info("自动装配bean--日志切面处理器");
        return logHandler;
    }
}
