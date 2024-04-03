import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import LoadingSpinner from '../components/LoadingSpinner';
import Message from '../components/Message';
import NewMessage from '../components/NewMessage';
import { MessageData } from '../modules/types';
import { Params } from './router';

async function fetchMessages(chatId: number): Promise<MessageData[]> {
  const response = await fetch(`http://localhost:8888/chats/${chatId}`);
  return response
    .json()
    .then((json) => json.data)
    .then((data) => data.map(MessageData.fromJSON));
}

const useMessages = (chatId: number) => {
  return useQuery({
    queryKey: ['messages'],
    queryFn: () => fetchMessages(chatId),
  });
};

export default function Chat() {
  // useParams can return undefined if the route doesn't have any params but we know that's not the case
  const { chatId } = useParams<keyof Params>() as Params;
  const { data, error, isPending } = useMessages(parseInt(chatId));

  if (error) {
    return <div>Something went wrong</div>;
  }

  if (isPending) {
    return (
      <div className="flex justify-center py-8">
        <LoadingSpinner />
      </div>
    );
  }

  return (
    <div>
      <NewMessage />
      <ul className="divide-y divide-gray-200 my-4 mx-8">
        {data.map(({ id, ...activityItem }) => (
          <li key={id}>
            <Message key={id} {...activityItem} />
          </li>
        ))}
      </ul>
    </div>
  );
}
