import movie from 'app/entities/movie/movie.reducer';
import staff from 'app/entities/staff/staff.reducer';
import category from 'app/entities/category/category.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  movie,
  staff,
  category,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
