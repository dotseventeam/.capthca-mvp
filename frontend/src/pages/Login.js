import { useState, useEffect } from 'react';
import { Link } from "react-router-dom";

import Captcha from '../components/Captcha'
import Footer from '../components/Footer'
import './css/login.css'

import axios from 'axios';



export default function Login({ cookies, setCookie, removeCookie }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [success, setSucces] = useState(false);

    const [isActive, setIsActive] = useState(false);
    const [captcha, setCaptcha] = useState({});
    const [captchaAnswers, setCaptchaAnswers] = useState([]);
    const [captchaSolved, setCaptchaSolved] = useState(false);


    const requestCaptcha = async (e) => {
        axios
            .get('http://localhost:5000/api/captcha/requestCaptcha')
            .then((response) => {
                setCaptcha(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }



    useEffect(() => {
        requestCaptcha();
    }, []);

    useEffect(() => {
        setErrorMessage('');
    }, [username, password, captchaSolved]);

    useEffect(() => {
        captchaAnswers.length > 0 && !captchaSolved ? setErrorMessage('Login failed. Please try again.') : setErrorMessage('');
    }, [captchaSolved]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (captchaAnswers.length !== 2) {
            setErrorMessage('Please solve the CAPTCHA.')
            return;
        }
        if (!username) {
            setErrorMessage('Please insert a username.');
            return;
        }
        if (!password) {
            setErrorMessage('Please insert a password.');
            return;
        }

        const loginData = {
            "username": username,
            "passwordHash": password,
            "captchaToken": captcha.criptoToken,
            "captchaAnswers": captchaAnswers
        };

        axios.post("http://localhost:5000/api/auth/authenticate", loginData)
            .then((response) => {
                if (response.status === 200) {
                    setSucces(true);
                    setCookie("jwtToken", response.data.jwtToken, { path: "/" });
                    console.log(response.data.jwtToken);
                }
            }).catch(() => {
                setErrorMessage('Login failed. Try again.');
                setCaptchaSolved(false);
                requestCaptcha();
            });
    }

    return (
        <>
            {success || cookies.jwtToken ? (
                <>
                    <section>
                        <h1>You are logged in!</h1>
                        <p>
                            <Link className="link" to="/">Go home</Link>
                        </p>
                    </section>
                    <Footer />
                </>
            ) : (
                <>
                    <section className={!isActive ? '' : 'disabled'}>
                        <h1>Login</h1>
                        <form onSubmit={handleSubmit}>
                            <p className={errorMessage ? "errmsg" : "offscreen"} aria-live="assertive">{errorMessage}</p>
                            <label htmlFor="username">Username:</label>
                            <input
                                type="text"
                                id="username"
                                autoComplete="off"
                                onChange={(e) => setUsername(e.target.value)}
                                value={username}
                                placeholder='Enter your username'
                                required
                            />

                            <label htmlFor="password">Password:</label>
                            <input
                                type="password"
                                id="password"
                                onChange={(e) => setPassword(e.target.value)}
                                value={password}
                                placeholder='Enter your password'
                                required
                            />
                            <Captcha
                                captcha={captcha} setCaptcha={setCaptcha}
                                isActive={isActive} setIsActive={setIsActive}
                                captchaAnswers={captchaAnswers} setCaptchaAnswers={setCaptchaAnswers}
                                captchaSolved={captchaSolved} setCaptchaSolved={setCaptchaSolved}
                            />
                            <button className='form-submission-btn' onClick={handleSubmit}>Log In</button>
                        </form>
                        <p>
                            Need an Account?&nbsp;
                            {<Link className="redirect" to="/signup">Sign Up</Link>}
                        </p>
                    </section>
                    <Footer />
                </>
            )}
        </>
    )
};
