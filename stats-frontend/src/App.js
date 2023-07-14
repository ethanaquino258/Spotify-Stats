import logo from './logo.svg';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Home from './Pages/Home';
import Songs from './Pages/Songs';
import Login from './Pages/Login';
import {Navigate} from 'react-router-dom';
import Outlet from './components/Outlet';
import { useState } from 'react';

const App = () => {
  const [user, setUser] = useState("");
  const ProtectedRoute = ({ user, redirectPath='/login', children}) => {
    if (user === "") {
        return <Navigate to={redirectPath} replace />;
    }

    return children ? children : <Outlet />;
  };

  async function login() {
    try {
        const response = await fetch("http://localhost:8080/login?scope=user-library-read+user-top-read");

        const result = await response.text();

        setUser(result);
        console.log(user);
        console.log(result);

        const win = window.open(result, "_blank");
        if (win != null) {
            win.focus();
        }
    } catch(error) {
        console.error("Error:", error);
    }

};

  return (
    <>
    <Routes>
      <Route index element={<Login user={user} login={() => login()}/>}/>
      <Route path='/login' element={<Login user={user} login={() => login()}/>}/>
      <Route element={<ProtectedRoute user={user}/>}>
        <Route path='/' element = {<Home/>} />
        <Route path='/songs' element = {<Songs />} />
      </Route>
    </Routes>
    </>
  )
}

export default App;
