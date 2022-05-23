package com.jaworski.sportdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManager;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SportDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportDiaryApplication.class, args);
        final EntityManager entityManager = EntityManagerProvider.get();
        EntityManagerProvider.startTransaction(entityManager);
    }
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/some-mvc-messages", "classpath:/some-other-mvc-messages",
                "classpath:/another-projects/mvc-messages", "classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }
}
