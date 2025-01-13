//import { useState } from "react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export const EditCustomer = () => {
  const { customerId } = useParams();
  const baseURL = "http://localhost:8085/v1/customer";
  const token = localStorage.getItem("Token");
  const [customer, setCustomer] = useState({ id: "", name: "", email: "" });
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const getCustomerById = async () => {
    try {
      const response = await fetch(`${baseURL}/${customerId}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const getCustomer = await response.json();
        setCustomer(getCustomer);
      }
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getCustomerById();
  }, []);

  const handleEditCustomer = async (e) => {
    e.preventDefault();
    const cc = { name: name, email: email };
    console.log(JSON.stringify(cc));
    try {
      const response = await fetch(`${baseURL}/${customerId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(cc),
      });
      if (response.ok) {
        const updated = response.text;
        alert("customer updated");
        navigate("/customers");
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <form
        className="p-4 border rounded shadow-lg w-50"
        onSubmit={(e) => handleEditCustomer(e)}>
        <h3 className="text-center mb-4">Edit Customer</h3>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Name
          </label>
          <input
            type="text"
            id="name"
            name="name"
            className="form-control"
            placeholder="Enter customer name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            Email
          </label>
          <input
            type="email"
            id="email"
            name="email"
            className="form-control"
            placeholder="Enter customer email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="text-center">
          <button type="submit" className="btn btn-primary w-100">
            Save Edit
          </button>
        </div>
      </form>
    </div>
  );
};
