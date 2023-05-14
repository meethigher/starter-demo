package top.meethigher.flowcontrol.handler;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.meethigher.cache.CacheStore;
import top.meethigher.flowcontrol.FlowControl;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 流量控制处理器
 *
 * @author chenchuancheng
 * @since 2022/12/17 16:26
 */
@Aspect
public class FlowControlHandler {

    private final Logger log = LoggerFactory.getLogger(FlowControlHandler.class);


    private final CacheStore<String, Integer> flowControlCache;

    public FlowControlHandler(CacheStore<String, Integer> flowControlCache) {
        this.flowControlCache = flowControlCache;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(top.meethigher.flowcontrol.FlowControl)")
    public void webFlowControl() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webFlowControl()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null || attributes.getRequest() == null) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        FlowControl flowControl = signature.getMethod().getAnnotation(FlowControl.class);
        long max = flowControl.max();
        int time = flowControl.time();
        TimeUnit timeUnit = flowControl.timeUnit();
        HttpServletRequest request = attributes.getRequest();
        String ip = getRemoteAddr(request);
        String url = request.getRequestURI();
        String flowControlString = String.format("%s_%s", ip, url);
        Integer integer = flowControlCache.get(flowControlString);
        if (integer != null) {
            //流量控制核心逻辑
            if (integer >= max) {
                log.warn("{} 每 <{} {}> 最多访问{}次，现已达最大次数限制", flowControlString, time, timeUnit, max);
                throw new RuntimeException("访问已达最大次数限制，稍后再试!");
            } else {
                flowControlCache.supply(flowControlString, ++integer);
            }
        } else {
            integer = 1;
            flowControlCache.put(flowControlString, integer, time, timeUnit);
        }
        log.info("{} 每 <{} {}> 最多访问{}次，现已访问{}次", flowControlString, time, timeUnit, max, integer == null ? 0 : integer);
    }


    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    private  String getRemoteAddr(HttpServletRequest request) {
        //由于使用了nginx，我在nginx配置的x-forwarded-for。所以此处要拿代理ip
        if (ObjectUtils.isEmpty(request.getHeader("x-forwarded-for"))) {
            return request.getRemoteAddr();
        } else {
            return request.getHeader("x-forwarded-for");
        }
    }

}
