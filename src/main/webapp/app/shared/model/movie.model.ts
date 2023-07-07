import dayjs from 'dayjs';
import { IStaff } from 'app/shared/model/staff.model';
import { ICategory } from 'app/shared/model/category.model';
import { Langue } from 'app/shared/model/enumerations/langue.model';

export interface IMovie {
  id?: number;
  name?: string | null;
  duration?: string | null;
  description?: string | null;
  language?: keyof typeof Langue | null;
  imageUrl?: string | null;
  publishDate?: string | null;
  membreStaffs?: IStaff[] | null;
  categories?: ICategory[] | null;
}

export const defaultValue: Readonly<IMovie> = {};
