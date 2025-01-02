import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
  const isAuthenticated = !!localStorage.getItem("Token");
  console.log(isAuthenticated);

  return isAuthenticated ? children : <Navigate to="/" />;
};

export default ProtectedRoute;
