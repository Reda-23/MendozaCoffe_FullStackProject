import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export const GenerateOrder = () => {
  const navigate = useNavigate();
  const [waiters, setWaiters] = useState([]);
  const baseUrl = "http://localhost:8085/v1/waiter";
  const token = localStorage.getItem("Token");
  const [waiter, setWaiter] = useState();
  const [order, setOrder] = useState({});
  const [items, setItems] = useState([]);
  const [orderCreated, setOrderCreated] = useState(false);

  const getAllWaiters = async () => {
    try {
      const response = await fetch(baseUrl + "/waiters", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        setWaiters(data);
      }
    } catch (error) {
      console.log(error);
    }
  };
  const getAllItems = async () => {
    try {
      const response = await fetch("http://localhost:8081/v1/item" + "/items", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        setItems(data);
      }
    } catch (error) {
      console.log(error);
    }
  };
  const generateOrder = async () => {
    try {
      const response = await fetch(
        "http://localhost:8081/v1/order" + `/${waiter}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.ok) {
        const data = await response.json();
        setOrder(data);
        setOrderCreated(true);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const addItemToOrder = async (id) => {
    const oId = order.orderId;
    try {
      const response = await fetch(
        `http://localhost:8081/v1/order/${oId}/${id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.ok) {
        const data = await response.json();
        setOrder(data);
        console.log(order);
      }
    } catch (error) {
      console.log(error);
    }
  };
  const goToBill = (orderId) => {
    navigate(`/bill/${orderId}`);
    console.log(order.items);
  };

  useEffect(() => {
    getAllWaiters();
    getAllItems();
  }, []);

  return (
    <div
      style={{
        backgroundColor: "black",
        color: "white",
        overflow: "hidden",
        height: "100vh",
        padding: "20px",
      }}>
      <div className="container-fluid vh-100 mt-2">
        <div className="row h-100">
          <div className="col-md-7 border-end">
            <h2 className="text-center m-2">Order Set-Up</h2>
            <h5>| Select Waiter |</h5>
            {waiters.map((waiter, key) => (
              <div
                onChange={(e) => setWaiter(e.target.value)}
                key={key}
                className="form-check-inline m-4">
                <input
                  value={waiter.waiterId}
                  className="form-check-input"
                  type="radio"
                  name="flexRadioDefault"
                  id="flexRadioDefault1"
                />
                <label className="form-check-label" htmlFor="flexRadioDefault1">
                  {waiter.name} | {waiter.schedule}
                </label>
              </div>
            ))}
            <button
              onClick={() => generateOrder()}
              type="button"
              className="btn btn-light"
              disabled={orderCreated}>
              Continue
            </button>
            <h5 className="m-3">
              |<i className="fas fa-plus fa-fw me-2"></i>Add Item To Your Order
              |
            </h5>
            {orderCreated && (
              <div className="container custom-container">
                <div className="row">
                  {" "}
                  {items.map((item, key) => (
                    <div className="col-md-4 mb-3" key={key}>
                      <div className="item list-group-item">
                        <button
                          onClick={() => addItemToOrder(item.itemId)}
                          className="btn btn-outline-light">
                          <i className="fas fa-coffee fa-fw me-2"></i>
                          {item.name} | {item.price}dh
                        </button>
                      </div>
                    </div>
                  ))}{" "}
                </div>
              </div>
            )}
          </div>
          <div className="col-md-4">
            <h2 className="text-end m-2">Order Details</h2>
            {orderCreated && (
              <div className="container mt-5">
                <div className="card mb-3 bg-transparent text-white">
                  <div className="card-header">
                    ---------------------------------------------------
                  </div>
                  <div className="card-body">
                    <h5 className="card-title">Order ID: {order.orderId}</h5>
                    <p className="card-text">
                      Order Date: {new Date(order.orderDate).toLocaleString()}
                    </p>
                    <p className="card-text">
                      Number of Items: {order.numberOfItems}
                    </p>
                    <p className="card-text">
                      Order Price: {order.orderPrice}dh
                    </p>
                  </div>{" "}
                </div>
                <div className="card mb-3 bg-transparent text-white">
                  <div className="card-header">
                    ---------------------------------------------------
                  </div>
                  {orderCreated && (
                    <div className="card-body">
                      <h5 className="card-title">
                        Waiter ID: {order.waiter.waiterId}
                      </h5>
                      <p className="card-text">Name: {order.waiter.name}</p>{" "}
                      <p className="card-text">
                        Contact Number: {order.waiter.contactNumber}
                      </p>
                      <p className="card-text">
                        Schedule: {order.waiter.schedule}
                      </p>
                    </div>
                  )}
                  <button
                    onClick={() => goToBill(order.orderId)}
                    className="btn btn-outline-light">
                    Generate Bill
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
