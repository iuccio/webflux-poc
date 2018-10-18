package com.aro.webfluxservice.configuration;

import com.aro.model.Movie;
import com.aro.model.MovieEvent;
import com.aro.webfluxservice.service.FluxMovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
class WebRoutesConfiguration {

  @Bean
  RouterFunction<?> routes(FluxMovieService fluxMovieService) {
	return RouterFunctions

			.route(GET("/movies"),
					serverRequest -> ServerResponse.ok()
							.body(fluxMovieService.all(), Movie.class))
			.andRoute(GET("/movies/{id}"),
					serverRequest -> ServerResponse.ok()
							.body(fluxMovieService.byId(serverRequest.pathVariable("id")), Movie.class))
			.andRoute(GET("/movies/{id}/events"), serverRequest ->
					ServerResponse.ok()
							.contentType(MediaType.TEXT_EVENT_STREAM)
							.body(fluxMovieService.events(serverRequest.pathVariable("id")), MovieEvent.class));
  }

}

