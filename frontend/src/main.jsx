import { createRoot } from "react-dom/client";
import "./index.css";
import Orders from "./components/Orders.jsx";
import Customers from "./components/Customers";
import Items from "./components/Items";
import {
  createBrowserRouter,
  RouterProvider,
  Navigate,
} from "react-router-dom";
import { AuthProvider } from "./components/security/AuthContext.jsx";
import { EditCustomer } from "./components/EditCustomer.jsx";
import { GenerateOrder } from "./components/GenerateOrder.jsx";
import Login from "./components/security/Login.jsx";
import { Bill } from "./components/Bill.jsx";
import ProtectedRoute from "./components/security/ProtectedRoute.jsx";
import { HomePage } from "./components/security/HomePage.jsx";
const router = createBrowserRouter([
  {
    path: "/",
    element: localStorage.getItem("Token") ? (
      <Navigate to="/home" />
    ) : (
      <Login />
    ),
  },
  {
    path: "/home",
    element: (
      <ProtectedRoute>
        <HomePage />
      </ProtectedRoute>
    ),
  },
  {
    path: "/customers",
    element: (
      <ProtectedRoute>
        <Customers />
      </ProtectedRoute>
    ),
  },
  {
    path: "editCustomer/:customerId",
    element: (
      <ProtectedRoute>
        <EditCustomer />
      </ProtectedRoute>
    ),
  },
  {
    path: "/items",
    element: (
      <ProtectedRoute>
        <Items />
      </ProtectedRoute>
    ),
  },
  {
    path: "/orders",
    element: (
      <ProtectedRoute>
        <Orders />
      </ProtectedRoute>
    ),
  },
  {
    path: "/generateOrder",
    element: (
      <ProtectedRoute>
        <GenerateOrder />
      </ProtectedRoute>
    ),
  },
  {
    path: "bill/:orderId",
    element: (
      <ProtectedRoute>
        <Bill />
      </ProtectedRoute>
    ),
  },
]);
createRoot(document.getElementById("root")).render(
  <AuthProvider>
    <RouterProvider router={router}></RouterProvider>
  </AuthProvider>
);
