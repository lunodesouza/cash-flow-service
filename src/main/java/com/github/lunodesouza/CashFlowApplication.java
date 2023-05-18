package com.github.lunodesouza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.github.lunodesouza"})
@EntityScan("com.github.lunodesouza.domain")
@EnableJpaRepositories("com.github.lunodesouza.repository")
public class CashFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashFlowApplication.class, args);
	}

}
