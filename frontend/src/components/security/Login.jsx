import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";




export const Login = () => {

    const {setAuthData} = useContext(AuthContext);
    
  
    
    const [username,setUserName] = useState('');
    const [password,setPassword] = useState('');
    const[token,setToken] = useState(null);
    const navigate = useNavigate();

    

    const handleLogin = async(e)=>{
        
        e.preventDefault();
        const credentials = {username,password};

        try{
            const response = await fetch("http://localhost:8081/login",{
                method: "POST",
                headers : {
                    "Content-Type" : "application/json"
                },
                body : JSON.stringify(credentials),
            })
            if (response.ok) {
                const jwt = await response.text();    
                localStorage.setItem('Token',jwt)
                setToken(jwt);
                setAuthData({token,username});
                navigate("/orders");
               
            }else{
                throw new Error("Login Failed");
                
            }
        }catch(err){
            console.log(err);
        }
        
    }

  return (
    (
    
        <div className="d-flex justify-content-center align-items-center vh-100">
        <form className="p-4 border rounded shadow" onSubmit={handleLogin}>
          <h3 className="text-center mb-4">Login</h3>
  
          {/* Email input */}
          <div className="form-outline mb-4">
            <input value={username} onChange={(e)=> setUserName(e.target.value)} type="username" id="form2Example1" className="form-control" />
            <label className="form-label" htmlFor="form2Example1">
              Email address
            </label>
          </div>
  
          {/* Password input */}
          <div className="form-outline mb-4">
            <input value={password} onChange={(e)=> setPassword(e.target.value)} type="password" id="form2Example2" className="form-control" />
            <label className="form-label" htmlFor="form2Example2">
              Password
            </label>
          </div>
  
          {/* 2 column grid layout for inline styling */}
          <div className="row mb-4">
            <div className="col d-flex justify-content-center">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  value=""
                  id="form2Example31"
                  defaultChecked
                />
                <label className="form-check-label" htmlFor="form2Example31">
                  Remember me
                </label>
              </div>
            </div>
  
            <div className="col text-end">
              <a href="#!">Forgot password?</a>
            </div>
          </div>
  
          {/* Submit button */}
          <button type="submit" className="btn btn-primary btn-block mb-4 w-100">
            Sign in
          </button>
  
          {/* Register buttons */}
          <div className="text-center">
            <p>
              Not a member? <a href="#!">Register</a>
            </p>
            <p>or sign up with:</p>
            <button
              type="button"
              className="btn btn-link btn-floating mx-1"
            >
              <i className="fab fa-facebook-f"></i>
            </button>
            <button
              type="button"
              className="btn btn-link btn-floating mx-1"
            >
              <i className="fab fa-google"></i>
            </button>
            <button
              type="button"
              className="btn btn-link btn-floating mx-1"
            >
              <i className="fab fa-twitter"></i>
            </button>
            <button
              type="button"
              className="btn btn-link btn-floating mx-1"
            >
              <i className="fab fa-github"></i>
            </button>
          </div>
        </form>
      </div>
    
  ))
}


export default Login;