package top.meethigher.log.handler;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author chenchuancheng github.com/meethigher
 * @since 2023/5/14 12:34
 */
@Aspect
public class LogHandler {

    private final Logger log = LoggerFactory.getLogger(LogHandler.class);

    private ThreadLocal<Long> timeThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> reqArgsThreadLocal = new ThreadLocal<>();

    /**
     * 切入点
     */
    @Pointcut("@annotation(top.meethigher.log.Log)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        timeThreadLocal.set(System.currentTimeMillis());
        reqArgsThreadLocal.set(Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null || attributes.getRequest() == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("请求响应信息如下:\n地址 => {}\n设备 => {}\n请求类型 => {}\n访问IP => {}\n请求参数 => {}\n返回值 => {}\n响应时间 => {}毫秒",
                request.getMethod()+" "+ URLDecoder.decode(request.getRequestURI(), "utf-8"),
                request.getHeader("User-Agent"),
                request.getContentType(),
                getRemoteAddr(request),
                reqArgsThreadLocal.get(),
                ret,
                System.currentTimeMillis() - timeThreadLocal.get());
    }


    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    private String getRemoteAddr(HttpServletRequest request) {
        //由于使用了nginx，我在nginx配置的x-forwarded-for。所以此处要拿代理ip
        if (ObjectUtils.isEmpty(request.getHeader("x-forwarded-for"))) {
            return request.getRemoteAddr();
        } else {
            return request.getHeader("x-forwarded-for");
        }
    }

}
