import { IMovie } from 'app/shared/model/movie.model';

export interface ICategory {
  id?: number;
  name?: string | null;
  description?: string | null;
  movies?: IMovie[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
