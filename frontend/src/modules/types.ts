import { Dayjs } from 'dayjs';

export type Person = {
  name: string;
  imageUrl: string;
  title?: string;
  email?: string;
  telephone?: string;
};

export type ActivityItem = {
  id: number;
  message: string;
  person: Person;
  datetime: Dayjs;
  title?: string;
};
