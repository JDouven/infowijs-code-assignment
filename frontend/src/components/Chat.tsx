import dayjs from 'dayjs';
import { ActivityItem, Person } from '../modules/types';
import Message from './Message';

const people: Person[] = [
  {
    name: 'Lindsay Walton',
    imageUrl:
      'https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=3&w=256&h=256&q=80',
  },
];
const activityItems: ActivityItem[] = [
  {
    id: 1,
    person: people[0],
    title: 'Reserveren parkeerplek',
    message:
      "Hallo collega's,\n\nzouden jullie in week 35 de hele week een parkeerplaats voor mij willen reserveren?\n Dank!",
    datetime: dayjs().subtract(2, 'hour').minute(34).second(12),
  },
  {
    id: 2,
    person: people[0],
    message:
      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    datetime: dayjs().subtract(22, 'hour').minute(14).second(10),
  },
  {
    id: 3,
    person: people[0],
    message:
      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    datetime: dayjs().subtract(2, 'day').hour(2).minute(59).second(59),
  },
];

export default function Chat() {
  return (
    <div>
      <ul className="divide-y divide-gray-200 my-4 mx-8">
        {activityItems.map(({ id, ...activityItem }) => (
          <Message key={id} {...activityItem} />
        ))}
      </ul>
    </div>
  );
}
