import React, { useState, useEffect } from 'react'
import axios from 'axios';

import Footer from '../components/Footer'
import './css/login.css'
import './css/profile.css'
import blank from '../res/blank.png'


export default function Profile({ cookies, setCookie, removeCookie }) {
    const [userData, setUserData] = useState({});

    useEffect(() => {
        axios
            .get('http://localhost:5000/api/users/me', { headers: { "Authorization": `Bearer ${cookies.jwtToken}` } })
            .then((response) => {
                setUserData(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, [])

    return (
        <>
            <section>
                <h1>Welcome! {userData.firstName}</h1>
                <div className='user-data-container'>
                    {/* <img src={userData.profilePicture === 'NULL' ? blank : userData.profilePicture } alt='profile picture' /> */}
                    <img className='propic' src={blank} alt='profile picture' />
                    <div className='data-label-wrapper'>
                        <label className="user-data-label" for="username">Username</label>
                        <span className="user-data">{userData.username}</span>
                    </div>
                    <div className='data-label-wrapper'>
                        <label className="user-data-label" for="firstName">First name</label>
                        <span className="user-data">{userData.firstName}</span>
                    </div>
                    <div className='data-label-wrapper'>
                        <label className="user-data-label" for="lastName">Last name</label>
                        <span className="user-data">{userData.lastName}</span>
                    </div>
                    <div className='data-label-wrapper'>
                        <label className="user-data-label" for="geneder">Gender</label>
                        <span className="user-data">{userData.gender}</span>
                    </div>
                    <div className='data-label-wrapper'>
                        <label className="user-data-label" for="birthDate">Birth date</label>
                        <span className="user-data">{userData.birthDate}</span>
                    </div>
                </div>
            </section>
            <Footer />
        </>
    );
}
