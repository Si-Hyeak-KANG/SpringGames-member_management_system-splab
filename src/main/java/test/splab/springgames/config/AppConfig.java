package test.splab.springgames.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.splab.springgames.aspect.LogAop;

@Configuration
public class AppConfig {

    @Bean
    public LogAop logAop() {
        return new LogAop();
    }

}
