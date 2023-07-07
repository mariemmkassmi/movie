package tn.cs.movie.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tn.cs.movie.domain.Movie;
import tn.cs.movie.repository.MovieRepository;
import tn.cs.movie.service.MovieService;
import tn.cs.movie.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.cs.movie.domain.Movie}.
 */
@RestController
@RequestMapping("/api")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieService movieService;

    private final MovieRepository movieRepository;

    public MovieResource(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    /**
     * {@code POST  /movies} : Create a new movie.
     *
     * @param movie the movie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movie, or with status {@code 400 (Bad Request)} if the movie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws URISyntaxException {
        log.debug("REST request to save Movie : {}", movie);
        if (movie.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Movie result = movieService.save(movie);
        return ResponseEntity
            .created(new URI("/api/movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movies/:id} : Updates an existing movie.
     *
     * @param id the id of the movie to save.
     * @param movie the movie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movie,
     * or with status {@code 400 (Bad Request)} if the movie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable(value = "id", required = false) final Long id, @RequestBody Movie movie)
        throws URISyntaxException {
        log.debug("REST request to update Movie : {}, {}", id, movie);
        if (movie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Movie result = movieService.update(movie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /movies/:id} : Partial updates given fields of an existing movie, field will ignore if it is null
     *
     * @param id the id of the movie to save.
     * @param movie the movie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movie,
     * or with status {@code 400 (Bad Request)} if the movie is not valid,
     * or with status {@code 404 (Not Found)} if the movie is not found,
     * or with status {@code 500 (Internal Server Error)} if the movie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/movies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Movie> partialUpdateMovie(@PathVariable(value = "id", required = false) final Long id, @RequestBody Movie movie)
        throws URISyntaxException {
        log.debug("REST request to partial update Movie partially : {}, {}", id, movie);
        if (movie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Movie> result = movieService.partialUpdate(movie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movie.getId().toString())
        );
    }

    /**
     * {@code GET  /movies} : get all the movies.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
     */
    @GetMapping("/movies")
    public List<Movie> getAllMovies(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Movies");
        return movieService.findAll();
    }

    /**
     * {@code GET  /movies/:id} : get the "id" movie.
     *
     * @param id the id of the movie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Optional<Movie> movie = movieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movie);
    }

    /**
     * {@code DELETE  /movies/:id} : delete the "id" movie.
     *
     * @param id the id of the movie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
