
import { createContext, useState } from "react";

// Create the AuthContext
export const AuthContext = createContext();

// Provide the AuthContext to components
export const AuthProvider = ({ children }) => {
  const [authData, setAuthData] = useState({
    jwt: null,
    username: null,
  });

  return (
    <AuthContext.Provider value={{ authData, setAuthData }}>
      {children}
    </AuthContext.Provider>
  );
};
