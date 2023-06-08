import { useRef, useState, useEffect } from "react";
import axios from '../api/axios';
import './css/signup.css';
import { Link } from "react-router-dom";

import Captcha from '../components/Captcha'
import Footer from '../components/Footer'


const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = '/register';
const NAMES_REGEX = /^[A-z][A-z ]{3,24}$/;
const EMAIL_REGEX = /^\S+@\S+\.\S+$/;

const Signup = () => {
    const userRef = useRef();
    const errRef = useRef();

    const [user, setUsername] = useState('');
    const [validName, setValidUsername] = useState(false);
    const [userFocus, setUsernameFocus] = useState(false);

    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [passwordFocus, setPasswordFocus] = useState(false);

    const [matchPassword, setMatchPassword] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);

    const [matchEmail, setMatchEmail] = useState('');
    const [validMatchEmail, setValidMatchEmail] = useState(false);

    const [firstName, setFirstName] = useState('');
    const [validFirstName, setValidFirstName] = useState(false);
    const [firstNameFocus, setFirstNameFocus] = useState(false);


    const [lastName, setLastName] = useState('');
    const [validLastName, setValidLastName] = useState(false);

    const [gender, setGender] = useState('');
    const [validGender, setValidGender] = useState(false);

    const [privacy, setPrivacy] = useState(false);

    const [immagine, setImmagine] = useState('');

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);



    const [isActive, setIsActive] = useState(false);





    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setValidUsername(USER_REGEX.test(user));
    }, [user])

    useEffect(() => {
        setValidPassword(PWD_REGEX.test(password));
        setValidMatch(password === matchPassword);
    }, [password, matchPassword])

    useEffect(() => {
        setValidFirstName(NAMES_REGEX.test(firstName));
        setValidLastName(NAMES_REGEX.test(lastName));
    }, [firstName, lastName])

    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(email));
        setValidMatchEmail(email === matchEmail);
    }, [email, matchEmail])

    useEffect(() => {
        setErrMsg('');
    }, [user, password, matchPassword, firstName, lastName, email, matchEmail])

    const handleSubmit = async (e) => {
        e.preventDefault();
        // if button enabled with JS hack
        // const v1 = USER_REGEX.test(user);
        // const v2 = PWD_REGEX.test(password);
        // if (!v1 || !v2) {
        //     // setErrMsg("Invalid Entry");
        //     console.log('err');
        //     // return;
        // }
        console.log('signup');
        // try {
        //     const response = await axios.post(REGISTER_URL,
        //         JSON.stringify({ user, password }),
        //         {
        //             headers: { 'Content-Type': 'application/json' },
        //             withCredentials: true
        //         }
        //     );
        //     // TODO: remove console.logs before deployment
        //     console.log(JSON.stringify(response?.data));
        //     //console.log(JSON.stringify(response))
        //     setSuccess(true);
        //     //clear state and controlled inputs
        //     setUsername('');
        //     setPassword('');
        //     setMatchPassword('');
        // } catch (err) {
        //     if (!err?.response) {
        //         setErrMsg('No Server Response');
        //     } else if (err.response?.status === 409) {
        //         setErrMsg('Username Taken');
        //     } else {
        //         setErrMsg('Registration Failed')
        //     }
        //     errRef.current.focus();
        // }
    }

    return (
        <>
            {success ? (
                <>
                    <section>
                        <h1>Success!</h1>
                        <p>
                            <a href="#">Sign In</a>
                        </p>
                    </section>
                    <Footer />
                </>
            ) : (
                <>
                    <section className={!isActive ? '' : 'disabled'}>
                        <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                        <h1>Signup</h1>
                        <form onSubmit={handleSubmit}>
                            <label htmlFor="username">
                                Username:
                            </label>
                            <input
                                placeholder="Insert a username"
                                type="text"
                                id="username"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setUsername(e.target.value)}
                                value={user}
                                required
                                aria-invalid={validName ? "false" : "true"}
                                aria-describedby="uidnote"
                                onFocus={() => setUsernameFocus(true)}
                                onBlur={() => setUsernameFocus(false)}
                            />

                            <fieldset>
                                <div className="field-container">
                                    <label htmlFor="firstName">
                                        First name
                                    </label>
                                    <input
                                        placeholder="Insert your first name"
                                        type="text"
                                        id="firstName"
                                        autoComplete="off"
                                        onChange={(e) => setFirstName(e.target.value)}
                                        value={firstName}
                                        required
                                        aria-invalid={validFirstName ? "false" : "true"}
                                        aria-describedby="uidnote"
                                        onFocus={() => setFirstNameFocus(true)}
                                        onBlur={() => setFirstNameFocus(false)}
                                    />
                                </div>

                                <div className="field-container">
                                    <label htmlFor="lastName">
                                        Last name
                                    </label>
                                    <input
                                        placeholder="Insert your last name"
                                        type="text"
                                        id="lastName"
                                        autoComplete="off"
                                        onChange={(e) => setLastName(e.target.value)}
                                        value={lastName}
                                        required
                                        aria-invalid={validLastName ? "false" : "true"}
                                        aria-describedby="uidnote"
                                    />
                                </div>
                            </fieldset>

                            <fieldset>
                                <div className="field-container">
                                    <label htmlFor="email">
                                        E-mail
                                    </label>
                                    <input
                                        placeholder="Insert your e-mail address"
                                        type="text"
                                        id="email"
                                        autoComplete="off"
                                        onChange={(e) => setEmail(e.target.value)}
                                        value={email}
                                        required
                                        aria-invalid={validEmail ? "false" : "true"}
                                        aria-describedby="uidnote"
                                    />
                                </div>

                                <div className="field-container">
                                    <label htmlFor="matchEmail">
                                        Confirm E-mail
                                    </label>
                                    <input
                                        placeholder="Confirm your e-mail address"
                                        type="text"
                                        id="matchEmail"
                                        autoComplete="off"
                                        onChange={(e) => setMatchEmail(e.target.value)}
                                        value={matchEmail}
                                        required
                                        aria-invalid={validMatchEmail ? "false" : "true"}
                                        aria-describedby="uidnote"
                                    />
                                </div>
                            </fieldset>

                            <label htmlFor="gender">
                                Gender
                            </label>
                            <select id="gender" onChange={(e) => setGender(e.target.value)}
                                value={gender}
                                required
                                aria-invalid={validGender ? "false" : "true"}
                                aria-describedby="uidnote">
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                                <option value="N">Non-binary</option>
                            </select>



                            <fieldset>
                                <div className="field-container">
                                    <label htmlFor="password">
                                        Password
                                    </label>
                                    <input
                                        placeholder="Choose a password"
                                        type="password"
                                        id="password"
                                        onChange={(e) => setPassword(e.target.value)}
                                        value={password}
                                        required
                                        aria-invalid={validPassword ? "false" : "true"}
                                        aria-describedby="passwordnote"
                                        onFocus={() => setPasswordFocus(true)}
                                        onBlur={() => setPasswordFocus(false)}
                                    />
                                </div>

                                <div className="field-container">
                                    <label htmlFor="confirm_password">
                                        Confirm Password
                                    </label>
                                    <input
                                        placeholder="Confirm your password"
                                        type="password"
                                        id="confirm_password"
                                        onChange={(e) => setMatchPassword(e.target.value)}
                                        value={matchPassword}
                                        required
                                        aria-invalid={validMatch ? "false" : "true"}
                                        aria-describedby="confirmnote"
                                        onFocus={() => setMatchFocus(true)}
                                        onBlur={() => setMatchFocus(false)}
                                    />
                                </div>
                            </fieldset>

                            <label htmlFor="picture">
                                Picture
                            </label>
                            <input
                                type="file"
                                id="picture"
                                autoComplete="off"
                                onChange={(e) => setImmagine(e.target.value)}
                                value={immagine}
                                required
                                aria-describedby="uidnote"
                            />

                            <label htmlFor="privacy">
                                Conditions for Privacy
                            </label>
                            <input
                                type="checkbox"
                                id="privacy"
                                onChange={(e) => setPrivacy(e.target.checked)}
                                value={privacy}
                                required
                            />

                            <Captcha isActive={isActive} setIsActive={setIsActive} />
                            {/* <button className='form-submission-btn' onClick={handleSubmit} disabled={!validName || !validPassword || !validMatch || !validFirstName ||
                                !validLastName || !validEmail || !validMatchEmail || privacy ? true : false}>Sign Up</button> */}
                            <button className='form-submission-btn' onClick={handleSubmit}>Sign Up</button>
                        </form>
                        <p>
                            Already registered?&nbsp;
                            {<Link className="redirect" to="/login">Sign In</Link>}
                        </p>
                    </section>
                    <Footer />
                </>
            )}
        </>
    )
}

export default Signup