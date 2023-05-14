package top.meethigher.flowcontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.meethigher.cache.impl.LeastRecentlyUsedCacheStore;
import top.meethigher.flowcontrol.handler.FlowControlHandler;

/**
 * @author chenchuancheng github.com/meethigher
 * @since 2023/5/14 12:03
 */
@Configuration
@ConditionalOnClass(FlowControl.class)
public class FlowControlAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(FlowControlAutoConfiguration.class);

    @Bean
    public FlowControlHandler flowControlHandler() {
        FlowControlHandler flowControlHandler = new FlowControlHandler(new LeastRecentlyUsedCacheStore<>(80));
        log.debug("自动装配bean--流量控制处理器");
        return flowControlHandler;
    }
}
