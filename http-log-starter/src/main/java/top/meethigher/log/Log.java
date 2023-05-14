package top.meethigher.log;

import java.lang.annotation.*;

/**
 *
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/5/14 12:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
