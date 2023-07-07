package tn.cs.movie.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.cs.movie.domain.Movie;
import tn.cs.movie.service.extended.MovieServiceExtended;

import java.util.List;

@RestController
@RequestMapping("/api/extended")
public class MovieResourceExtended {
    private final Logger log = LoggerFactory.getLogger(MovieResourceExtended.class);
    private final MovieServiceExtended movieServiceExtended;

    public MovieResourceExtended(MovieServiceExtended movieServiceExtended) {
        this.movieServiceExtended = movieServiceExtended;
    }

    @GetMapping("/movies")
    public List<Movie> getAllMoviesByFilter( @RequestParam(required = false, name = "search")String search) {
        log.debug("REST request to get all Movies");
        return movieServiceExtended.getMoviesBySearch(search);
    }
    @PostMapping("/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        log.debug("Create  Movies");
        Movie createdMovied =  movieServiceExtended.createMovie(movie);
        return ResponseEntity.status(201).body(createdMovied);
    }

}
