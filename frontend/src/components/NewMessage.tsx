import { Dialog, Transition } from '@headlessui/react';
import { PaperAirplaneIcon } from '@heroicons/react/20/solid';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';
import { Fragment } from 'react/jsx-runtime';
import { people } from '../modules/fakeData';
import { MessageData, PersonData } from '../modules/types';
import { useNewMessageDialogContext } from '../providers/NewMessageDialogContext';
import { Params } from '../routes/router';

type NewMessageData = {
  message: string;
  person: PersonData;
};

async function postMessage(
  chatId: number,
  message: NewMessageData
): Promise<MessageData> {
  const response = await fetch(`http://localhost:8888/chats/${chatId}`, {
    method: 'POST',
    body: JSON.stringify(message),
  });
  return response.json().then((json) => MessageData.fromJSON(json.data));
}

function useNewMessageMutation(chatId: number) {
  const queryClient = useQueryClient();
  return useMutation<MessageData, Error, NewMessageData>({
    mutationFn: (data) => postMessage(chatId, data),
    onSuccess: (message) => {
      queryClient.setQueryData<MessageData[]>(['messages'], (oldData) =>
        oldData ? [message, ...oldData] : [message]
      );
    },
  });
}

type Inputs = {
  message: string;
};

export default function NewMessage() {
  const { newMessageDialogOpen, setNewMessageDialogOpen } =
    useNewMessageDialogContext();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Inputs>();

  // useParams can return undefined if the route doesn't have any params but we know that's not the case
  const { chatId } = useParams<keyof Params>() as Params;
  const mutation = useNewMessageMutation(parseInt(chatId));

  const onSubmit: SubmitHandler<Inputs> = (data) =>
    mutation.mutate(
      { message: data.message, person: people[0] },
      {
        onSuccess() {
          setNewMessageDialogOpen(false);
          reset();
        },
      }
    );

  return (
    <Transition.Root show={newMessageDialogOpen} as={Fragment}>
      <Dialog
        as="div"
        className="relative z-20"
        onClose={setNewMessageDialogOpen}
      >
        <Transition.Child
          as={Fragment}
          enter="transition-opacity ease-linear duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="transition-opacity ease-linear duration-300"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black/25" />
        </Transition.Child>
        <div className="fixed inset-0 overflow-y-auto">
          <div className="flex min-h-full items-center justify-center p-4 text-center">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 scale-95"
              enterTo="opacity-100 scale-100"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 scale-100"
              leaveTo="opacity-0 scale-95"
            >
              <Dialog.Panel className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                <Dialog.Title
                  as="h3"
                  className="text-lg font-medium leading-6 text-gray-900"
                >
                  New chat message
                </Dialog.Title>
                <form className="mt-2" onSubmit={handleSubmit(onSubmit)}>
                  <label htmlFor="chat" className="sr-only">
                    Your message
                  </label>
                  <div className="flex items-center py-2">
                    <textarea
                      rows={1}
                      className="block mr-2 p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                      placeholder="Your message..."
                      {...register('message', { required: true })}
                    ></textarea>
                    <button
                      type="submit"
                      className="inline-flex justify-center p-2 text-blue-600 rounded-full cursor-pointer hover:bg-blue-100"
                    >
                      <PaperAirplaneIcon className="w-5 h-5" />
                      <span className="sr-only">Send message</span>
                    </button>
                  </div>
                  {errors.message && (
                    <span className="text-red-500 text-xs">
                      Your message can't be empty
                    </span>
                  )}
                  {mutation.error && (
                    <span className="text-red-500 text-xs">
                      An error occurred. Bad luck.
                    </span>
                  )}
                </form>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </div>
      </Dialog>
    </Transition.Root>
  );
}
