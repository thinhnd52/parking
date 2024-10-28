import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '2b41d0fe-3787-4fff-aebf-3643689519c5',
};

export const sampleWithPartialData: IAuthority = {
  name: 'db0f271e-cbb3-403e-af43-07eb135e72ed',
};

export const sampleWithFullData: IAuthority = {
  name: '5417e8c4-0e30-461f-a1f6-f11f9f6ed858',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
