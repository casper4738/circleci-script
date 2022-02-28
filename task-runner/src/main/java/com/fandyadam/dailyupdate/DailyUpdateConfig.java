package com.fandyadam.dailyupdate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = {"dailyupdate"}, havingValue = "true")
public class DailyUpdateConfig {

	@Bean
	public ApplicationRunner getRunner() {
		return new DailyUpdate();
	}

}
