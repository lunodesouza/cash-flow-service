package com.github.lunodesouza;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
	@Info(title = "CashFlow API", version = "1.0", description = "List or Save Transactions")
)
public class CashFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashFlowApplication.class, args);
	}

}
