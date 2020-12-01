package com.demo.springboot.rest;

import com.demo.springboot.dto.CreateMovieDto;
import com.demo.springboot.dto.MovieDto;
import com.demo.springboot.dto.MovieListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieApiController {
    private static final Logger LOG = LoggerFactory.getLogger(MovieApiController.class);

    private final MovieListDto movies;

    public MovieApiController() {
        List<MovieDto> moviesList = new ArrayList<>();
        moviesList.add(new MovieDto(1,
                "Piraci z Krzemowej Doliny",
                1999,
                "https://fwcdn.pl/fpo/30/02/33002/6988507.6.jpg")
        );
        movies = new MovieListDto(moviesList);
    }

    @CrossOrigin
    @RequestMapping(value = "/movies/getAll", method = RequestMethod.GET)
    public ResponseEntity<MovieListDto> getMovies() {
        LOG.info("--- get all movies: {}", movies);
        return ResponseEntity.ok().body(movies);    // = new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/movies/add", method = RequestMethod.POST)
    public ResponseEntity<Void> newMovie(@RequestBody MovieDto newMovie) {
        LOG.info("Film added");
        movies.getMovies().add(newMovie);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @RequestMapping(value = "/movies/update", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateMovie(@RequestParam("id") Integer id, @RequestBody MovieDto updatedMovie) {
        for (int i = 0; i < movies.getMovies().size(); i++) {
            if (id == movies.getMovies().get(i).getMovieId() + 1) {
                LOG.info("Updated movie - id: {}", id);
                movies.getMovies().add(updatedMovie);
                movies.getMovies().remove(i);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @RequestMapping(value = "/movies/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMovie(@RequestParam Integer id) {
        for (int i = 0; i < movies.getMovies().size(); i++) {
            if (id == movies.getMovies().get(i).getMovieId() + 1) {
                LOG.info("deleted movie --- id: {}", id);
                movies.getMovies().remove(i);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}

