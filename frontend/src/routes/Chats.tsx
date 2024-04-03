import { useQuery } from '@tanstack/react-query';
import ChatItem from '../components/ChatItem';
import LoadingSpinner from '../components/LoadingSpinner';
import { ChatData } from '../modules/types';

async function fetchChats(): Promise<ChatData[]> {
  const response = await fetch('http://localhost:8888/chats');
  return response
    .json()
    .then((json) => json.data)
    .then((data) => data.map(ChatData.fromJSON));
}

const useChats = () => {
  return useQuery({
    queryKey: ['chats'],
    queryFn: () => fetchChats(),
  });
};

export default function Chats() {
  const { data, error, isPending } = useChats();

  if (error) {
    console.error(error);
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
    <>
      <h1 id="primary-heading" className="sr-only">
        Chats
      </h1>
      <div>
        <ul className="divide-y divide-gray-200">
          {data.map((chat) => (
            <ChatItem key={chat.id} chat={chat} />
          ))}
        </ul>
      </div>
    </>
  );
}
