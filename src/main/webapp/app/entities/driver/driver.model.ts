export interface IDriver {
  id: number;
  lastName?: string | null;
  firstName?: string | null;
  phone?: string | null;
  licenseNo?: string | null;
}

export type NewDriver = Omit<IDriver, 'id'> & { id: null };
