import { ISpot, NewSpot } from './spot.model';

export const sampleWithRequiredData: ISpot = {
  id: 12386,
  name: 'potable finally inasmuch',
};

export const sampleWithPartialData: ISpot = {
  id: 12756,
  name: 'dowse',
  spotRow: 7011,
  spotColumn: 3369,
};

export const sampleWithFullData: ISpot = {
  id: 14485,
  name: 'once valiantly provided',
  spotNo: 'ack shocked',
  level: 13351,
  spotRow: 13244,
  spotColumn: 13445,
  occupied: true,
};

export const sampleWithNewData: NewSpot = {
  name: 'inject diligently',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
