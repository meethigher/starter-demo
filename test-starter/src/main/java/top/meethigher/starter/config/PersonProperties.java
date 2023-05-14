package top.meethigher.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * @see <a href="https://blog.csdn.net/agonie201218/article/details/126162237">Spring boot starter 如何给配置添加idea 提示功能 spring-boot-configuration-processor_自定义starter让idea配置提示_Young丶的博客-CSDN博客</a>
 * @author chenchuancheng github.com/meethigher
 * @since 2023/5/14 10:44
 */
@ConfigurationProperties(prefix = "person")
public class PersonProperties {

    /**
     * 姓名
     */
    private String name = "尸祖降臣";

    /**
     * 身价
     */
    private Double money = 1.0;

    /**
     * 生日
     */
    private String birth = "1970-01-01 00:00:00";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
