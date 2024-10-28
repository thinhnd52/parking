import { IDriver, NewDriver } from './driver.model';

export const sampleWithRequiredData: IDriver = {
  id: 16603,
  lastName: 'Hoppe',
};

export const sampleWithPartialData: IDriver = {
  id: 15694,
  lastName: 'Rice',
  licenseNo: 'pulp loosely',
};

export const sampleWithFullData: IDriver = {
  id: 15753,
  lastName: 'Witting',
  firstName: 'Tremayne',
  phone: '1-519-751-2539 x0453',
  licenseNo: 'ah ouch hmph',
};

export const sampleWithNewData: NewDriver = {
  lastName: 'Conroy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
