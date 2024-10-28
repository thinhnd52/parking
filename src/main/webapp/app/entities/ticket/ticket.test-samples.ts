import dayjs from 'dayjs/esm';

import { ITicket, NewTicket } from './ticket.model';

export const sampleWithRequiredData: ITicket = {
  id: 4052,
  ticketNo: 'finally flu',
  lastName: 'Lowe',
  phone: '1-676-401-2326',
};

export const sampleWithPartialData: ITicket = {
  id: 19577,
  ticketNo: 'courageously inasmuch heating',
  lastName: 'Ankunding',
  phone: '459-284-6149 x8594',
  room: 'likely',
  licensePlate: 'bide off why',
  note: 'carefully',
};

export const sampleWithFullData: ITicket = {
  id: 3304,
  ticketNo: 'unblinking of',
  lastName: 'Hermann',
  firstName: 'Devante',
  phone: '1-586-797-6724',
  room: 'deploy limp',
  arrivalDate: dayjs('2024-10-28T02:16'),
  departureDate: dayjs('2024-10-28T04:36'),
  model: 'INFINITI',
  color: 'BLUE',
  licensePlate: 'next',
  status: 'PARKED',
  damanged: false,
  note: 'after spork',
};

export const sampleWithNewData: NewTicket = {
  ticketNo: 'supposing now psst',
  lastName: 'Cormier',
  phone: '1-715-994-9813',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
