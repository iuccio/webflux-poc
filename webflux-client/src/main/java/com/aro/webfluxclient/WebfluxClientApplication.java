package com.aro.webfluxclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class WebfluxClientApplication {

  @Bean
  WebClient webClient() {
	return WebClient
			.create("http://localhost:8081")
			.mutate()
			.build();
  }

  public static void main(String[] args) {
	SpringApplication.run(WebfluxClientApplication.class, args);
  }
}
