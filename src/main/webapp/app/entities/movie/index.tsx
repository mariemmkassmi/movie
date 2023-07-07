import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Movie from './movie';
import MovieDetail from './movie-detail';
import MovieUpdate from './movie-update';
import MovieDeleteDialog from './movie-delete-dialog';

const MovieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Movie />} />
    <Route path="new" element={<MovieUpdate />} />
    <Route path=":id">
      <Route index element={<MovieDetail />} />
      <Route path="edit" element={<MovieUpdate />} />
      <Route path="delete" element={<MovieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MovieRoutes;
