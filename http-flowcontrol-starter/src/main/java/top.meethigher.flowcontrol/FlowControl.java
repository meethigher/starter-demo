package top.meethigher.flowcontrol;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 流量控制
 * 该注解的作用是：每time时间内，访问次数超过max次就会被拦截
 * [demodump/src/main/java/org/example/redislimater/ApiCall.java · 马府强/demo - 码云 - 开源中国](https://gitee.com/fuqiangma/demo/blob/master/demodump/src/main/java/org/example/redislimater/ApiCall.java)
 *
 * @author chenchuancheng
 * @since 2022/12/17 16:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowControl {

    /**
     * 次数限制
     *
     * @return
     */
    long max() default 100;

    /**
     * 时长限制
     *
     * @return
     */
    int time() default 60;

    /**
     * 时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}

