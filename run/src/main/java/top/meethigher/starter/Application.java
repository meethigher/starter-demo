package top.meethigher.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import top.meethigher.flowcontrol.FlowControl;
import top.meethigher.log.Log;
import top.meethigher.starter.config.PersonProperties;
import top.meethigher.starter.service.PersonService;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
class TestController {

    @Resource
    private PersonService personService;

    @GetMapping("/test")
    @FlowControl(max = 5, time = 5)
    @Log
    public String test() {
        return personService.log();
    }

    @GetMapping("/test1/{a}")
    @Log
    public String test1(@PathVariable("a") String a) {
        return a;
    }


    @PostMapping("/test2")
    @Log
    public String test2(@RequestBody Map<String,Object> personProperties) {
        return personProperties.toString();
    }
}
