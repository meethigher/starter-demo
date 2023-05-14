package top.meethigher.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.meethigher.starter.config.PersonProperties;
import top.meethigher.starter.service.PersonService;
import top.meethigher.starter.service.PersonServiceImpl;

/**
 * @see <a href="https://blog.csdn.net/qq_29550537/article/details/90734539">关于springboot自动化配置，创建一个spring-boot-starter的简单实现_新风s的博客-CSDN博客</a>
 * @author chenchuancheng github.com/meethigher
 * @since 2023/5/14 11:01
 */
@Configuration
@EnableConfigurationProperties(PersonProperties.class)
@ConditionalOnClass(PersonProperties.class)
public class TestStarterAutoConfiguration {


    private final PersonProperties personProperties;

    public TestStarterAutoConfiguration(PersonProperties personProperties) {
        this.personProperties = personProperties;
    }

    @Bean
    public PersonService personService(PersonProperties personProperties) {
        return new PersonServiceImpl(personProperties);
    }
}
