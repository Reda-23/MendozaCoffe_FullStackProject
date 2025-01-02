import { useEffect, useState } from "react"
import  Navbar  from "./Navbar"

function Items() {
  const [items,setItems] = useState([]);
  const [name,setName] = useState('');
  const [price,setPrice] = useState(0);
  const [description,setDescription] = useState('');
  const token = localStorage.getItem('Token');

  const [currentPage,setCurrentPage] = useState(0);
  const [totalPages,setTotalPages] = useState(0);
  const [pageSize,setPageSize] = useState(5);
  
const baseURL =  "http://localhost:8081/v1/item" ;


  const handleSaveItem = async (e)=>{
    e.preventDefault();
    const item = {name: name,price : price , description : description}
    try{
    const response = await fetch(`${baseURL}/`,{
      method:'POST',
      headers :{
        "Content-Type" : "application/json",
          Authorization: `Bearer ${token}`,
      },body: JSON.stringify(item)
    });
    if (response.ok) {
      const savedItem = await response.json();
      alert('Item Added')
      setName('')
      setPrice(0)
      setDescription('')
      setItems((i=>[...i,savedItem]));
    }else{
      console.log('request unresolved for saving the item');  
    }
    
  }catch(err){
     console.log(err);       
  }}

  const handleListItems = async (page,size)=>{
    try{
    const response = await fetch(`${baseURL}/itemsP?page=${page}&size=${size}`,{
      method : 'GET',
      headers:{
          "Content-Type" : "application/json",
          Authorization: `Bearer ${token}`,
      }
    })
    if (response.ok) {
      const items = await response.json();
      setItems(items.content);
      setTotalPages(items.totalPages);
      
      
    }else{
      console.log("failed to fetch items list");
    }
    }catch(err){
        console.log(err);
        
    }
  }

  const handlePageChange 
  = (newPage) => { setCurrentPage(newPage); };


  useEffect(()=>{
    handleListItems(currentPage,pageSize);
  },[currentPage,pageSize])

  return (
    <div style={{overflow:'hidden'}}>
    <Navbar></Navbar>
    <div className="container-fluid vh-100 mt-2">
            <div className="row h-100">
                {/* Left Section: List of Items */}
                <div className="col-md-6 border-end">
                    <h4 className="text-center mb-3">Items List</h4>
                    {items.map((item,key)=>(
                    
                    <ul className="list-group" key={key}>
          <li className="list-group-item d-flex justify-content-between align-items-start">
           <div>
            <h5>
                <i className="fas fa-coffee fa-fw me-2"></i> {item.name}
            </h5>
            <p className="mb-1 text-muted">{item.description}</p>
        </div>
        <span className="badge bg-danger rounded-pill">${item.price}</span>
    </li>
    </ul>
))}
                    {/* Pagination */}

                   <div className="d-flex justify-content-center align-items-center mt-3"> <button
                     className="btn btn-dark mr-2" disabled={currentPage === 0} onClick={() => handlePageChange(currentPage - 1)} > Previous 
                     </button>
                      <span>{currentPage + 1} of {totalPages}</span> 
                    <button className="btn btn-dark ml-2" disabled={currentPage === totalPages - 1} onClick={() => handlePageChange(currentPage + 1)} > Next </button>
                       </div>
                   </div>

                {/* Right Section: Add Item Form */}
                <div className="col-md-6">
                    <h4 className="text-center mb-3">Add New Item</h4>
                    <form onSubmit={handleSaveItem}>
                        <div className="mb-3">
                            <label htmlFor="name" className="form-label">
                                Name
                            </label>
                            <input
                            onChange={(e)=>setName(e.target.value)}
                              value={name}
                                type="text"
                                name="name"
                                id="name"
                                className="form-control"
                                placeholder="Enter item name"
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="price" className="form-label">
                                Price
                            </label>
                            <input onChange={(e)=>setPrice(e.target.value)}
                            value={price}
                                type="number"
                                id="price"
                                className="form-control"
                                placeholder="Enter item price"
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="description" className="form-label">
                                Description
                            </label>
                            <textarea
                            onChange={(e)=>setDescription(e.target.value)}
                                value={description}
                                id="description"
                                className="form-control"
                                rows="4"
                                placeholder="Enter item description"
                            ></textarea>
                        </div>
                        <button type="submit" className="btn btn-primary w-100">
                            Add Item
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Items