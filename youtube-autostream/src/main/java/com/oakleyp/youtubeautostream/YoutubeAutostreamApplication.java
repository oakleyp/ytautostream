package com.oakleyp.youtubeautostream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaRepositories("com.oakleyp.youtubeautostream.data.repository")
@Slf4j
public class YoutubeAutostreamApplication {
	public static void main(String[] args) {
		log.info("App started");
		SpringApplication.run(YoutubeAutostreamApplication.class, args);
	}
}
