package com.coffeebreak.animalshelter.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class TelegramBotConfiguration {

    @Bean
    public TelegramBot telegramBot(@Value("${telegram.bot.token}") String token) {
        return new TelegramBot(token);
    }
}
