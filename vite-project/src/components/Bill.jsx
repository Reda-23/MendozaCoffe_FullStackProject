import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export const Bill = () => {
  const [customer, setCustomer] = useState("");
  const [order, setOrder] = useState({});
  const { orderId } = useParams();
  const [payement, setPayment] = useState(0);
  const [tax, setTax] = useState(0);
  const [bill, setBill] = useState({});
  const [isBillGenerated, setIsBillGenerated] = useState(false);
  const token = localStorage.getItem("Token");

  const generateBill = async () => {
    try {
      const response = await fetch(
        `http://localhost:8081/v1/bill/${orderId}/${payement}/${tax}`,
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
        setBill(data);
        isBillGenerated(true);
        setCustomer("");

        setTax(0);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    //getOrderById();
  }, []);

  return (
    <div className="bg-black text-white p-3 min-vh-100">
      <h1 className="text-center">Billing Details</h1>
      <div className="container m-4">
        <label className="m-2">Enter Payement Method</label>
        <select
          value={payement}
          onChange={(e) => setPayment(e.target.value)}
          className="form-select bg-black text-white"
          aria-label="Default select example">
          <option defaultValue>Choose Payment Method</option>
          <option value="1">CASH</option>
          <option value="2">CREDIT CARD</option>
          <option value="3">MOBILE APPLICATION</option>
        </select>
        <label className="m-2" htmlFor="usr">
          Enter Customer Name
        </label>
        <div className="form-group">
          <input
            value={customer}
            onChange={(e) => setCustomer(e.target.value)}
            type="text"
            className="form-control bg-black text-white"
            id="usr"
          />
        </div>
        <label className="m-2" htmlFor="tax">
          Enter Tax
        </label>
        <div className="form-group">
          <input
            value={tax}
            onChange={(e) => setTax(e.target.value)}
            type="text"
            className="form-control bg-black text-white"
            id="tax"
          />
        </div>
      </div>
      <div className="text-center">
        <button
          onClick={() => generateBill()}
          className="btn btn-outline-light m-3">
          Generate Invoice
        </button>
      </div>

      {!isBillGenerated && bill.order ? (
        <>
          <div className="container mb-4">
            <div className="row mb-2">
              <div className="col-12">
                <div className="invoice-title">
                  <h2 className="text-center">Invoice</h2>
                  <h3 className="float-right">Order {bill.billId}</h3>
                </div>
                <hr />
              </div>
            </div>
            <div className="row">
              <div className="col-6">
                <address>
                  <strong className="text-muted">Billed To:</strong>
                  <br /> {customer}
                  <br />
                  <strong className="text-muted">Waiter : </strong>
                  <br /> {bill.order.waiter.name}
                  <br /> {bill.order.waiter.contactNumber}
                  <br /> Fes
                </address>
              </div>
              <div className="col-6 text-right">
                <address>
                  <strong>CAFE MENDOZA </strong>
                  <br />
                  <strong>Café/Restaurant</strong>
                </address>
              </div>
            </div>

            <div className="row">
              <div className="col-6">
                <address>
                  <strong className="text-muted">Payment Method:</strong>
                  <strong> {bill.paymentMethod}</strong>
                  <br />
                </address>
              </div>

              <div className="col-6 text-right">
                <address>
                  <strong className="text-muted">Order Date:</strong>
                  <br /> {new Date(bill.order.orderDate).toLocaleString()}
                  <br />
                </address>
              </div>
            </div>
            <div className="row mb-4">
              <div className="col-12">
                <div className="card bg-dark text-white">
                  <div className="card-header">
                    <h3 className="card-title">
                      <strong className="text-muted">Order summary</strong>
                    </h3>
                  </div>
                  <div className="card-body">
                    {" "}
                    <div className="container-fluid">
                      {" "}
                      <div className="row">
                        {" "}
                        <div className="col-3">
                          <strong>Item</strong>
                        </div>{" "}
                        <div className="col-3 text-center">
                          <strong>Price</strong>
                        </div>{" "}
                        <div className="col-3 text-center">
                          <strong>Quantity</strong>
                        </div>{" "}
                        <div className="col-3 text-right">
                          <strong>Totals</strong>
                        </div>{" "}
                      </div>{" "}
                      {Object.keys(bill.order.items).map((key) => {
                        const itemDetails = key.match(
                          /itemId=(\d+), name=(.*), price=(\d+\.\d+), description=(.*)\)/
                        );
                        if (itemDetails) {
                          const itemId = itemDetails[1];
                          const itemName = itemDetails[2];
                          const itemPrice = itemDetails[3];
                          const quantity = bill.order.items[key];
                          return (
                            <div className="row" key={itemId}>
                              {" "}
                              <div className="col-3">{itemName}</div>{" "}
                              <div className="col-3 text-center">
                                ${itemPrice}
                              </div>{" "}
                              <div className="col-3 text-center">
                                {quantity}
                              </div>{" "}
                              <div className="col-3 text-right">
                                ${(itemPrice * quantity).toFixed(2)}
                              </div>{" "}
                            </div>
                          );
                        }
                        return null;
                      })}{" "}
                      <div className="row">
                        {" "}
                        <div className="col-6"></div>{" "}
                        <div className="col-3 text-center">
                          {" "}
                          <strong>Subtotal</strong>{" "}
                        </div>{" "}
                        <div className="col-3 text-right">
                          ${bill.totalPrice.toFixed(2)}
                        </div>{" "}
                      </div>{" "}
                      <div className="row">
                        {" "}
                        <div className="col-6"></div>{" "}
                        <div className="col-3 text-center">
                          {" "}
                          <strong>Tax</strong>{" "}
                        </div>{" "}
                        <div className="col-3 text-right">
                          ${bill.tax.toFixed(2)}
                        </div>{" "}
                      </div>{" "}
                      <div className="row">
                        {" "}
                        <div className="col-6"></div>{" "}
                        <div className="col-3 text-center">
                          {" "}
                          <strong>Total</strong>{" "}
                        </div>{" "}
                        <div className="col-3 text-right  bg-secondary text-wrap fs-2">
                          ${bill.finalPrice.toFixed(2)}
                        </div>{" "}
                      </div>{" "}
                    </div>{" "}
                  </div>{" "}
                </div>
              </div>
            </div>
          </div>
          <button className="btn btn-outline-secondary ">
            <Link
              className="text-decoration-none text-white text-align-end"
              to="/orders">
              Go To Order History
            </Link>
          </button>
        </>
      ) : (
        <div className="container-fluid mb-6 text-center mt-5">
          Bill Will Show Up Here
        </div>
      )}
    </div>
  );
};
