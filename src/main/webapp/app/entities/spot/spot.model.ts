export interface ISpot {
  id: number;
  name?: string | null;
  spotNo?: string | null;
  level?: number | null;
  spotRow?: number | null;
  spotColumn?: number | null;
  occupied?: boolean | null;
}

export type NewSpot = Omit<ISpot, 'id'> & { id: null };
