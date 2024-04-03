import { PersonData } from './types';

export const people: PersonData[] = [
  {
    id: 0,
    email: 'jorisdouven@gmail.com',
    name: 'Joris Douven',
    title: 'Software Engineer',
    avatar: 'https://randomuser.me/api/portraits/men/32.jpg',
  },
  {
    id: 1,
    email: 'collega-a@bedrijf.nl',
    name: 'Silvia van Buren',
    title: 'Front Office',
    avatar: 'https://randomuser.me/api/portraits/women/75.jpg',
    phone: '+31600000000',
  },
  {
    id: 2,
    email: 'hans@bedrijf.nl',
    name: 'Hans van Willigen',
    title: 'Lead Developer',
    avatar: 'https://randomuser.me/api/portraits/men/75.jpg',
  },
];

export function getPersonDisplayName(person: PersonData) {
  const sentByCurrentUser = person.id === 0;
  return sentByCurrentUser ? 'You' : person.name;
}
