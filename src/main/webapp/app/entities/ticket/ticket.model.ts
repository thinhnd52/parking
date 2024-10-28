import dayjs from 'dayjs/esm';
import { ISpot } from 'app/entities/spot/spot.model';
import { IDriver } from 'app/entities/driver/driver.model';
import { CarBrand } from 'app/entities/enumerations/car-brand.model';
import { Color } from 'app/entities/enumerations/color.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ITicket {
  id: number;
  ticketNo?: string | null;
  lastName?: string | null;
  firstName?: string | null;
  phone?: string | null;
  room?: string | null;
  arrivalDate?: dayjs.Dayjs | null;
  departureDate?: dayjs.Dayjs | null;
  model?: keyof typeof CarBrand | null;
  color?: keyof typeof Color | null;
  licensePlate?: string | null;
  status?: keyof typeof Status | null;
  damanged?: boolean | null;
  note?: string | null;
  spot?: ISpot | null;
  driver?: IDriver | null;
}

export type NewTicket = Omit<ITicket, 'id'> & { id: null };
