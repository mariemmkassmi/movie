package tn.cs.movie.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.cs.movie.domain.Category;
import tn.cs.movie.domain.Movie;
import tn.cs.movie.repository.extended.MovieRepositoryExtended;
import tn.cs.movie.service.MovieService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Transactional
public class MovieServiceExtended extends MovieService {

    private final MovieRepositoryExtended movieRepositoryExtended;
    private final CategoryServiceExtended categoryServiceExtended;
    //private final StaffServiceExtended StaffServiceExtended;

    private final Logger log = LoggerFactory.getLogger(MovieServiceExtended.class);

    public MovieServiceExtended(MovieRepositoryExtended movieRepositoryExtended, CategoryServiceExtended categoryServiceExtended
                               //,  tn.cs.movie.service.extended.StaffServiceExtended staffServiceExtended
                                ) {
        super(movieRepositoryExtended);
        this.movieRepositoryExtended = movieRepositoryExtended;
        this.categoryServiceExtended = categoryServiceExtended;
      //  this.StaffServiceExtended = staffServiceExtended;

    }

    public List<Movie> getMoviesBySearch(String search) {
        return movieRepositoryExtended.getMoviesBySearch(search);
    }

    public Movie createMovie(Movie movie ) {


        // check the categories, if the category doesn't exist then we should create it
        Set<Category> updatedCategories=  movie.getCategories().stream().map(category -> {
            if (isNull(category.getId())) {
                // create the category
            return categoryServiceExtended.save(category);
            }
            return category;
            }
            ).collect(Collectors.toSet());

        movie.setCategories(updatedCategories);
/*        Set<Staff> updatedStaff=  movie.getMembreStaffs().stream().map(staff -> {
                if (isNull(staff.getId())) {
                    // create the staff
                    return StaffServiceExtended.save(staff);
                }
                return staff;
            }
        ).collect(Collectors.toSet());

        movie.setMembreStaffs(updatedStaff);

 */

        // check the staffs, if the staff doesn't exist then we should create it
        // create a movie
        return movieRepositoryExtended.save(movie);
    }
    public Movie Movieupdateed(Movie movie) {

        log.debug("Request to update Movie : {}", movie);
        return movieRepositoryExtended.save(movie);
    }
}
