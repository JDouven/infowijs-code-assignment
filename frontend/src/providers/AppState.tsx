import { ReactNode, useState } from 'react';
import { MobileMenuContext } from './MobileMenuContext';
import { NewMessageDialogContext } from './NewMessageDialogContext';

type Props = {
  children: ReactNode;
};

export default function AppState({ children }: Props) {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [newChatDialogOpen, setNewChatDialogOpen] = useState(false);
  return (
    <MobileMenuContext.Provider value={{ mobileMenuOpen, setMobileMenuOpen }}>
      <NewMessageDialogContext.Provider
        value={{
          newMessageDialogOpen: newChatDialogOpen,
          setNewMessageDialogOpen: setNewChatDialogOpen,
        }}
      >
        {children}
      </NewMessageDialogContext.Provider>
    </MobileMenuContext.Provider>
  );
}
