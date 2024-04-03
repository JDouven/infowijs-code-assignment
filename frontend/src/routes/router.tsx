import { createBrowserRouter } from 'react-router-dom';
import Chat from './Chat';
import Chats from './Chats';
import Root from './Root';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    children: [
      {
        path: '/',
        element: <Chats />,
      },
      {
        path: '/chats/:chatId',
        element: <Chat />,
      },
    ],
  },
]);

type Params = {
  chatId: string;
};

export { router };
export type { Params };
