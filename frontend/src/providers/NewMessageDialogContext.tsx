import { createContext, useContext } from 'react';

type NewMessageDialogContextValue = {
  newMessageDialogOpen: boolean;
  setNewMessageDialogOpen: (isOpen: boolean) => void;
};

export const NewMessageDialogContext = createContext<
  NewMessageDialogContextValue | undefined
>(undefined);

export const useNewMessageDialogContext = () => {
  const newMessageDialogContext = useContext(NewMessageDialogContext);
  if (newMessageDialogContext === undefined) {
    throw new Error(
      'useNewMessageDialogContext must be inside a NewMessageDialogContext'
    );
  }
  return newMessageDialogContext;
};
