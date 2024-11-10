import axios from "axios";
import { createContext, useEffect, useState } from "react";
export const StoreContext = createContext(null);

const StoreContextProvider = ({ children }) => {
  const [user, setUser] = useState({ name: "Son" });
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userData, setUserData] = useState("");
  const [url, setUrl] = useState("http://localhost:8080");
  const [token, setToken] = useState("");
  const [stores, setStores] = useState([]);
  const [idU, setIdU] = useState(1);
  const [longtitude, setLongtitude] = useState("");
  const [latitude, setLatitude] = useState("");
  const value = {
    user,
    setUser,
    isAuthenticated,
    setIsAuthenticated,
    userData,
    setUserData,
    url,
    setUrl,
    token,
    setToken,
    stores,
    setStores,
    idU,
    longtitude,
    setLongtitude,
    latitude,
    setLatitude,
  };

  return (
    <StoreContext.Provider value={value}>{children}</StoreContext.Provider>
  );
};
export default StoreContextProvider;
