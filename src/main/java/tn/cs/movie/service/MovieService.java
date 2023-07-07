package tn.cs.movie.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.cs.movie.domain.Movie;
import tn.cs.movie.repository.MovieRepository;

/**
 * Service Implementation for managing {@link Movie}.
 */
@Service
@Transactional
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Save a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Update a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    public Movie update(Movie movie) {
        log.debug("Request to update Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Partially update a movie.
     *
     * @param movie the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Movie> partialUpdate(Movie movie) {
        log.debug("Request to partially update Movie : {}", movie);

        return movieRepository
            .findById(movie.getId())
            .map(existingMovie -> {
                if (movie.getName() != null) {
                    existingMovie.setName(movie.getName());
                }
                if (movie.getDuration() != null) {
                    existingMovie.setDuration(movie.getDuration());
                }
                if (movie.getDescription() != null) {
                    existingMovie.setDescription(movie.getDescription());
                }
                if (movie.getLanguage() != null) {
                    existingMovie.setLanguage(movie.getLanguage());
                }
                if (movie.getImageUrl() != null) {
                    existingMovie.setImageUrl(movie.getImageUrl());
                }
                if (movie.getPublishDate() != null) {
                    existingMovie.setPublishDate(movie.getPublishDate());
                }

                return existingMovie;
            })
            .map(movieRepository::save);
    }

    /**
     * Get all the movies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        log.debug("Request to get all Movies");
        return movieRepository.findAll();
    }

    /**
     * Get all the movies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Movie> findAllWithEagerRelationships(Pageable pageable) {
        return movieRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one movie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Movie> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
