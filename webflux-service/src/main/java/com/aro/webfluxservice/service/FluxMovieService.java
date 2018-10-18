package com.aro.webfluxservice.service;

import com.aro.model.Movie;
import com.aro.model.MovieEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FluxMovieService {

  public List<Movie> movies() {
	Movie asd = Movie.builder().id("1").title("asd").build();
	Movie yxc = Movie.builder().id("2").title("yxc").build();
	Movie qwe = Movie.builder().id("3").title("qwe").build();
	List<Movie> movies = Arrays.asList(asd, yxc, qwe);
	return movies;
  }

  public Flux<MovieEvent> events(String movieId) {
	log.info("Movie id: " + movieId);
	return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date())))
			.delayElements(Duration.ofSeconds(1)).log();
  }

  public Mono<Movie> byId(String id) {
	Movie foundMovie = movies().stream().filter(movie -> movie.getId().equals(id)).findFirst().orElse(null);
	return Mono.just(foundMovie);
  }

  public Flux<Movie> all() {
	return Flux.just(movies().get(0),movies().get(1),movies().get(2));
  }
}
