import React, { useEffect } from 'react'
import Footer from '../components/Footer'
import axios from 'axios';


export default function Home({ cookies, setCookies, removeCookie }) {
    // const testToken = async (e) => {
    //     e.preventDefault();
    //     axios.get("http://localhost:5000/api/auth/secured", { headers: { "Authorization": `Bearer ${cookies.jwtToken}` } })
    //         .then((response) => {
    //             if (response.status === 200) {
    //                 console.log(response);
    //             }
    //         }).catch((err) => {
    //             console.log(err.response);
    //         });
    //     console.log('sent');
    // };

    return (
        <div className='container'>
            <h1>Welcome to the .7 CAPTCHA</h1>
            <ol>
                <li>Try out our CAPTCHA on the login page;</li>
                <li>Log out.</li>
            </ol>
            {/* <button className='form-submission-btn' onClick={testToken}>TEST</button> */}
            <Footer />
        </div>


    );
}