package ru.sd.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.sd.app.services.BookIdProvider;

@Configuration
@ComponentScan(basePackages = "ru.sd.app")
public class AppContextConfig {

    @Bean
    public BookIdProvider bookIdProvider() {
        BookIdProvider bookIdProvider = new BookIdProvider();
        bookIdProvider.initBookIdProvider();
        bookIdProvider.destroyBookIdProvider();

        return bookIdProvider;
    }
}
