package it.brt.helloworld.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    ObjectMapper objectMapper;

    @Value("${allowed-origins}")
    private List<String> allowedOrigins = new ArrayList<>();

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name()) //
                .allowedOriginPatterns(allowedOrigins.toArray(new String[0])) //
                .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CharacterEncodingFilter getCharacterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(StandardCharsets.UTF_8.displayName());
        filter.setForceEncoding(true);
        return filter;
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> registrationCharacterEncodingFilter() {
        FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<CharacterEncodingFilter>();
        registration.setFilter(getCharacterEncodingFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

}
