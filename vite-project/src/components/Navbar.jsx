import { Link } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Navbar() {
  let user = "";

  const handleLogout = () => {
    localStorage.removeItem("Token");
    window.location.href = "/";
  };

  const getUserRoles = (token) => {
    try {
      const decodedToken = jwtDecode(token);
      // Extract roles as an array of strings (e.g., ["ADMIN"])
      user = decodedToken.sub;
      return decodedToken.roles.map((role) => role.authority);
    } catch (error) {
      console.error("Failed to decode token:", error);
      return [];
    }
  };

  const token = localStorage.getItem("Token");
  const userRoles = getUserRoles(token);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container-fluid">
        <a className="navbar-brand">
          <Link to="/home" className="text-white text-decoration-none">
            {" "}
            Mendoza
          </Link>
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNavAltMarkup"
          aria-controls="navbarNavAltMarkup"
          aria-expanded="false"
          aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
          <div className="navbar-nav">
            <a className="nav-link active" aria-current="page">
              Home
            </a>
            {(userRoles == "USER" || userRoles == "ADMIN") && (
              <Link className="nav-link" to="/customers">
                Customers
              </Link>
            )}
            {userRoles == "ADMIN" && (
              <Link className="nav-link" to="/items">
                Items
              </Link>
            )}
            {userRoles == "ADMIN" && (
              <Link className="nav-link" to="/orders">
                Orders
              </Link>
            )}
            <a
              className="nav-link disabled"
              href="#"
              tabIndex="-1"
              aria-disabled="true">
              Disabled
            </a>
          </div>
        </div>
        <Link to="/generateOrder">
          <button
            type="button"
            className="btn btn-outline-secondary text-white">
            Generate Order
          </button>
        </Link>
        <span className="navbar-text text-uppercase font-weight-bold p-2 text-white">
          |{"" + user + ""}|
        </span>
        <Link
          className="nav-link p-2 active text-danger font-weight-bold"
          onClick={handleLogout}>
          |Logout|
        </Link>
      </div>
    </nav>
  );
}

export default Navbar;
