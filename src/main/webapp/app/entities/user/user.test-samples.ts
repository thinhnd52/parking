import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 22423,
  login: 'Vh|0JI@uJtQ',
};

export const sampleWithPartialData: IUser = {
  id: 7653,
  login: '@bg6',
};

export const sampleWithFullData: IUser = {
  id: 25897,
  login: 's4J@ijqX\\In\\;L0AXdl\\deLeDMJ\\PYo8O\\+m6Mf-k',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
