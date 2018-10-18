package com.aro.webfluxservice.controller;

import com.aro.model.Movie;
import com.aro.model.MovieEvent;
import com.aro.webfluxservice.service.FluxMovieService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class FluxMovieController {

  private final FluxMovieService fluxMovieService;

  FluxMovieController(FluxMovieService fluxMovieService) {
	this.fluxMovieService = fluxMovieService;
  }

  @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<MovieEvent> crossTheStreams(@PathVariable String id) {
	return fluxMovieService.events(id);
  }

  @GetMapping("/{id}")
  public Mono<Movie> byId(@PathVariable String id) {
	return fluxMovieService.byId(id);
  }

  @GetMapping
  public Flux<Movie> all() {
	return fluxMovieService.all();
  }

}
