import { useEffect, useState } from "react";
import Navbar from "./Navbar";

export const Orders = () => {
  const [orders, setOrders] = useState([]);
  const token = localStorage.getItem("Token");
  const baseUrl = "http://localhost:8081/v1/order";

  const orderHistory = async () => {
    try {
      const response = await fetch(`${baseUrl}/orders`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        setOrders(data);
        console.log(data);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    orderHistory();
  }, []);

  return (
    <div className="bg-black text-white min-vh-100">
      {" "}
      <Navbar />{" "}
      <div className="container py-5">
        {" "}
        <h2 className="text-center mb-4">Order History</h2>{" "}
        <div className="row">
          {" "}
          {orders.map((order, key) => (
            <div key={key} className="col-md-4 mb-4">
              {" "}
              <div className="card bg-dark text-white">
                {" "}
                <div className="card-body">
                  {" "}
                  <h5 className="card-title">Order #{order.orderId}</h5>{" "}
                  <p className="card-text">
                    {" "}
                    Date: {new Date(order.orderDate).toLocaleString()}{" "}
                  </p>{" "}
                  <p className="card-text">
                    {" "}
                    Number of Items: {order.numberOfItems}{" "}
                  </p>{" "}
                  <p className="card-text">Order Price: ${order.orderPrice}</p>{" "}
                </div>{" "}
              </div>{" "}
            </div>
          ))}{" "}
        </div>{" "}
      </div>{" "}
    </div>
  );
};

export default Orders;
