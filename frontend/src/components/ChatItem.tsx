import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import { getPersonDisplayName } from '../modules/fakeData';
import { ChatData, MessageData } from '../modules/types';

async function fetchLastMessage(
  chatId: number
): Promise<MessageData | undefined> {
  const response = await fetch(`http://localhost:8888/chats/${chatId}/last`);
  return response
    .json()
    .then((json) => json.data)
    .then((data) => MessageData.fromJSON(data));
}

const useLastMessage = (chatId: number) => {
  return useQuery({
    queryKey: ['messages', chatId],
    queryFn: () => fetchLastMessage(chatId),
  });
};

type Props = {
  chat: ChatData;
};

export default function ChatItem({ chat }: Props) {
  const { data, error, isPending } = useLastMessage(chat.id);

  const lastMessageText = error
    ? 'Error'
    : isPending
    ? 'Loading...'
    : getLastMessageText(data);

  function getLastMessageText(message: MessageData | undefined) {
    if (!message) {
      return 'No messages yet';
    }
    return `${getPersonDisplayName(message.sender)}: ${message.message}`;
  }

  return (
    <li className="bg-white">
      <Link to={`/chats/${chat.id}`}>
        <div className="w-full flex items-center justify-between p-6 space-x-6">
          <div className="flex-1 truncate">
            <div className="flex items-center space-x-3">
              <h3 className="text-gray-900 text-sm font-medium truncate">
                {chat.name}
              </h3>
            </div>
            <p className="mt-1 text-gray-500 text-sm truncate">
              {lastMessageText}
            </p>
          </div>
          <img
            className="w-10 h-10 bg-gray-300 rounded-full flex-shrink-0"
            src={chat.person.avatar}
            alt={chat.person.name}
          />
        </div>
      </Link>
    </li>
  );
}
