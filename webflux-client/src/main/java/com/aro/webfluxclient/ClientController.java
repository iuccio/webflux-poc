package com.aro.webfluxclient;

import com.aro.model.Movie;
import com.aro.model.MovieEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
class ClientController {

  @Autowired
  private WebClient client;

  @GetMapping("/movies")
  public Flux<Movie> getMovies() {
	log.info("Get Movies:");
	return client
			.get()
			.uri("/movies")
			.retrieve()
			.bodyToFlux(Movie.class).log();
  }

  @GetMapping(value = "/movie-events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<MovieEvent> getMovieEvents(@RequestParam String movieTitle) {
	log.info("Get movie events");
	return client
			.get()
			.uri("/movies")
			.retrieve()
			.bodyToFlux(Movie.class)
			.log()
			.filter(movie -> movie.getTitle().equalsIgnoreCase(movieTitle))
			.flatMap(movie ->
					client.get()
							.uri("/movies/{id}/events", movie.getId())
							.retrieve()
							.bodyToFlux(MovieEvent.class)).log();
  }

}