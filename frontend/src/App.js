import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";


import Home from './pages/Home'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Profile from './pages/Profile'
import Logout from './pages/Logout'


import logo from './res/dotseven.png'
import { useCookies } from "react-cookie";



export default function App() {
  const [cookies, setCookie, removeCookie] = useCookies(['jwtToken']);

  return (
    <Router>
      <nav>
        <ul>
          <li className="logo-wrap">
            <img src={logo} alt="dotseven logo" />
          </li>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            {cookies.jwtToken ? <Link to="/profile">Profile</Link> : ''}
          </li>
          <li>
            {cookies.jwtToken ? <Link to="/logout">Logout</Link> : <Link to="/login">Log In</Link>}
            {/* <Link to="/signup">Sign Up</Link> */}
          </li>
        </ul>
      </nav>

      <main>
        <Routes>
          <Route path="/" element={<Home cookies={cookies} setCookie={setCookie} removeCookie={removeCookie} />} />
          <Route path="/login" element={<Login cookies={cookies} setCookie={setCookie} removeCookie={removeCookie} />} />
          <Route path="/logout" element={<Logout cookies={cookies} setCookie={setCookie} removeCookie={removeCookie} />} />
          {cookies.jwtToken ? <Route path="/profile" element={<Profile cookies={cookies} setCookie={setCookie} removeCookie={removeCookie} />} /> : <></>}
          {/* <Route path="/signup" element={<Signup />} /> */}
        </Routes>
      </main>
    </Router>
  );
}