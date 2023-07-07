import { IMovie } from 'app/shared/model/movie.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IStaff {
  id?: number;
  role?: keyof typeof Role | null;
  firstName?: string | null;
  lastName?: string | null;
  movies?: IMovie[] | null;
}

export const defaultValue: Readonly<IStaff> = {};
