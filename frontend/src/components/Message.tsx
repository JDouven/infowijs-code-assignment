import { Dayjs } from 'dayjs';
import { Person } from '../modules/types';

type Props = {
  message: string;
  person: Person;
  datetime: Dayjs;
};

export default function Message({ message, person, datetime }: Props) {
  return (
    <li className="py-4">
      <div className="flex space-x-3">
        <img className="h-6 w-6 rounded-full" src={person.imageUrl} alt="" />
        <div className="flex-1 space-y-1">
          <div className="flex items-center justify-between">
            <h3 className="text-sm font-medium">{person.name}</h3>
            <p className="text-sm text-gray-500" title={datetime.format('LLL')}>
              {datetime.fromNow()}
            </p>
          </div>
          <p className="text-sm text-gray-500">{message}</p>
        </div>
      </div>
    </li>
  );
}
