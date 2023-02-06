package jmaster.io.project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * WebMvcConfigurer : de tao message
 */
@SpringBootApplication
@EnableJpaAuditing // giup can thiep db, tu gen
@EnableScheduling // thu vien nâng cao hơn: quartz
@EnableAsync
@EnableCaching // cau hinh cache:
public class Project2Application implements WebMvcConfigurer {

    @Bean
    JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(Project2Application.class, args);
    }

    // LocaleResolver : trinh giai quyet ngon ngu
    // de luu ngon ngu da chon vao session
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("en"));
        return slr;
    }

    // giong filter(bo loc)
    // duyet request gui len ,  de tra ra ngo ngu phu hop
    @Bean // hello?lang=vi
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // cau hinh params ngon ngu gui len de doi ngon ngu
        return lci;
    }

    // them localeChangeInterceptor vao registry cua Spring
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

//    @Scheduled(fixedDelay = 5000)
//    @Scheduled(cron = "*/5 * * * * *")
//    public void hello() {
//        System.out.println("HELLO");
//    }

}
