import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Staff from './staff';
import StaffDetail from './staff-detail';
import StaffUpdate from './staff-update';
import StaffDeleteDialog from './staff-delete-dialog';

const StaffRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Staff />} />
    <Route path="new" element={<StaffUpdate />} />
    <Route path=":id">
      <Route index element={<StaffDetail />} />
      <Route path="edit" element={<StaffUpdate />} />
      <Route path="delete" element={<StaffDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StaffRoutes;
