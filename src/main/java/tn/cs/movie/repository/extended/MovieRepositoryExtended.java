package tn.cs.movie.repository.extended;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.cs.movie.domain.Movie;
import tn.cs.movie.repository.MovieRepository;
import tn.cs.movie.repository.MovieRepositoryWithBagRelationships;

import java.util.List;

@Repository
public interface MovieRepositoryExtended extends MovieRepositoryWithBagRelationships, MovieRepository {

    @Query("SELECT m FROM Movie m left join m.categories  category " +
        "left join m.membreStaffs staff " +
        "WHERE (" +
        "(:search is null ) or (m.name like %:search%)" +
       " or (CAST(m.publishDate AS string) LIKE %:search% )" +
        " or (category.name like %:search%)" +
        "or (staff.firstName like %:search%)" +
        "or (staff.lastName like %:search%)" +
        " ) ")
    List<Movie> getMoviesBySearch(@Param("search") String search);


}


