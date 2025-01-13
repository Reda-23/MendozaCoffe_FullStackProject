import { useEffect, useState } from "react";
import Navbar from "./Navbar";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

function Customers() {
  const [customerList, setCustomerList] = useState([]);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const baseURL = "http://localhost:8085/v1/customer";
  const token = localStorage.getItem("Token");
  let roles = [];

  const getUserRoles = () => {
    try {
      const token = localStorage.getItem("Token");
      const decodedToken = jwtDecode(token);
      roles = decodedToken.roles.map((role) => role.authority);
      return roles;
    } catch (error) {
      console.error("Failed to decode token:", error);
      return [];
    }
  };

  useEffect(() => {
    handleGetCustomers();
  }, []);

  const handleGetCustomers = async () => {
    const token = localStorage.getItem("Token");
    try {
      const response = await fetch(baseURL + "/customers", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const customers = await response.json();
        setCustomerList(customers);
      } else {
        throw new Error("Fetching Customers Failed");
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleDeleteCustomer = async (id) => {
    console.log(getUserRoles());

    const isDeleted = confirm("would you like to delete the customer");

    if (isDeleted == true) {
      const response = await fetch(baseURL + `/${id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        alert("Customer is Deleted");
        handleGetCustomers();
      }
    } else {
      console.log("Keep The Customer");
    }
  };

  const goToEdit = (customerId) => {
    navigate(`/editCustomer/${customerId}`);
  };

  const handleAddCustomer = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("Token");
    const customer = { name: name, email: email };
    try {
      const response = await fetch(baseURL + "/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(customer),
      });
      if (response.ok) {
        const returnedCustomer = await response.json();
        alert("Customer Saved " + returnedCustomer.name.toUpperCase());
        setName("");
        setEmail("");
        setCustomerList((prevList) => [...prevList, returnedCustomer]);
      } else {
        throw new Error("Customer Save Failed");
      }
    } catch (err) {
      console.log(console.error(err));
    }
  };

  return (
    <div>
      <Navbar />
      <h2 className="text-center m-4">Add Customer </h2>
      <form>
        <div className="row">
          <div className="col">
            <input
              type="text"
              name="name"
              value={name}
              className="form-control"
              onChange={(e) => setName(e.target.value)}
              placeholder="First name"
            />
          </div>
          <div className="col">
            <input
              type="text"
              name="email"
              value={email}
              className="form-control"
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email"
            />
          </div>
          <div className="col">
            <button
              type="submit"
              onClick={handleAddCustomer}
              className="btn btn-primary">
              Submit
            </button>
          </div>
        </div>
      </form>

      <div>
        <h2 className="text-center m-5">Customer List</h2>
      </div>

      <table className="table align-middle mb-0 bg-dark m-7">
        <thead className="bg-dark">
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Delete</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customerList.map((c, key) => (
            <tr key={key}>
              <td>
                <div className="d-flex align-items-center">
                  <div className="ms-3">
                    <p className="fw-bold mb-1">{c.name}</p>
                  </div>
                </div>
              </td>
              <td>
                <p className="fw-bold mb-1">{c.email}</p>
              </td>

              <td>
                <button
                  disabled={getUserRoles() == "USER"}
                  onClick={() => handleDeleteCustomer(c.customerId)}
                  className="btn btn-warning"
                  title={
                    getUserRoles() === "USER"
                      ? "You don't have permission to delete customers"
                      : ""
                  }>
                  Delete
                </button>
              </td>
              <td>
                <button
                  onClick={() => goToEdit(c.customerId)}
                  className="btn btn-success">
                  Edit
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Customers;
