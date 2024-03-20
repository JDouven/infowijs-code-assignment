import { useQuery } from '@tanstack/react-query';
import { Message as MessageData } from '../modules/types';
import LoadingSpinner from './LoadingSpinner';
import Message from './Message';

async function fetchMessages(): Promise<MessageData[]> {
  const response = await fetch('http://localhost:8888/messages');
  return response
    .json()
    .then((json) => json.data)
    .then((data) => data.map(MessageData.fromJSON));
}

const useMessages = () => {
  return useQuery({
    queryKey: ['messages'],
    queryFn: () => fetchMessages(),
  });
};

export default function Chat() {
  const { data, error, isPending } = useMessages();

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
      <ul className="divide-y divide-gray-200 my-4 mx-8">
        {data.map(({ id, ...activityItem }) => (
          <Message key={id} {...activityItem} />
        ))}
      </ul>
    </div>
  );
}
