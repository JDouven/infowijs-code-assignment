import { createContext, useContext } from 'react';

type MobileMenuContextValue = {
  mobileMenuOpen: boolean;
  setMobileMenuOpen: (isOpen: boolean) => void;
};

export const MobileMenuContext = createContext<
  MobileMenuContextValue | undefined
>(undefined);

export const useMobileMenuContext = () => {
  const mobileMenuContext = useContext(MobileMenuContext);
  if (mobileMenuContext === undefined) {
    throw new Error('useMobileMenuContext must be inside a MobileMenuContext');
  }
  return mobileMenuContext;
};
