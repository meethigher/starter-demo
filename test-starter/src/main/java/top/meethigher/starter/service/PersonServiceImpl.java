package top.meethigher.starter.service;

import top.meethigher.starter.config.PersonProperties;

public class PersonServiceImpl implements PersonService {


    private final PersonProperties personProperties;

    public PersonServiceImpl(PersonProperties personProperties) {
        this.personProperties = personProperties;
    }

    @Override
    public String log() {
        String template = String.format("姓名：%s，身价：%s，生日：%s",
                personProperties.getName(),
                personProperties.getMoney(),
                personProperties.getBirth());
        System.out.println(template);
        return template;
    }
}
