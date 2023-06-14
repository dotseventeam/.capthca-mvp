import React, { useEffect } from 'react'
import { Link } from "react-router-dom";
import axios from 'axios';

import Footer from '../components/Footer'
import './css/login.css'


export default function Logout({ cookies, setCookie, removeCookie }) {
    useEffect(() => {
        if (cookies.jwtToken) {
            removeCookie("jwtToken");
            axios
                .get('http://localhost:5000/logout')
                .then((response) => {
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    }, [])

    return (
        <>
            <section>
                <h1>You successfully logged out!</h1>
                <p>
                    <Link className="link" to="/login">Log In</Link>
                </p>
            </section>
            <Footer />
        </>

    );
}