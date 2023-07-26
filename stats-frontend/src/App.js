import logo from './logo.svg';
import './App.css';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Home from './Pages/Home';
import Songs from './Pages/Songs';
import Login from './Pages/Login';
import Artists from './Pages/Artists';
import {Navigate, Outlet} from 'react-router-dom';
import { useState } from 'react';

const App = () => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  const ProtectedRoute = ({ user, redirectPath='/login', children}) => {
    if (user === true) {
      return <Outlet />
    } else {
      return <Navigate to={redirectPath} replace />;
    }
  };

const login = async () => {
  try {
    const response = await fetch("http://localhost:8080/login?scope=user-library-read+user-top-read");

    const result = await response.text();

    const win = window.open(result, "_blank");
    if (win != null) {
        win.focus();
    }

    setUser(true);
    navigate('/home');

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
        <Route path='/home' element = {<Home />} />
        <Route path='/songs' element = {<Songs />} />
        <Route path='/artists' element = {<Artists />} />
      </Route>
    </Routes>
    </>
  )
}

export default App;
