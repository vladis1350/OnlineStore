package by.integrator.telegrambot;

import by.integrator.telegrambot.service.bot.InitializationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TelegramBotApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(TelegramBotApplication.class, args);
		applicationContext.getBean(InitializationService.class).initialize();
	}

}
