import React, { useRef, useState, useEffect } from 'react'
import './css/captcha.css';
import Login from '../pages/Login.js';
import logo from '../res/dotseven.png';
import axios from 'axios';


export default function Captcha({
    isActive,
    setIsActive,
    captcha,
    setCaptcha,
    captchaAnswers,
    setCaptchaAnswers,
    captchaSolved,
    setCaptchaSolved
}) {

    const [options, setOptions] = useState([])
    const [selectedOptions, setSelectedOptions] = useState([0, 0, 0, 0, 0, 0, 0, 0]);

    useEffect(() => {
        setOptions(captcha["captchaOptions"]);
    }, [captcha]);

    const openDialog = async (e) => {
        e.preventDefault();
        if (!captchaSolved) // block from opening if captcha was correctly submitted 
            setIsActive(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        let answers = [];
        for (let i = 0; i < selectedOptions.length; i++) {
            if (selectedOptions[i] == 1) answers.push(options[i]);
        }
        if (answers.length !== 2) refreshCaptcha();
        else {
            setCaptchaAnswers([...answers]);
            setCaptchaSolved(true);
            setIsActive(false);
        }
    };

    const uncheckOptions = () => {
        setSelectedOptions([0, 0, 0, 0, 0, 0, 0, 0]);
    }

    const refreshCaptcha = (e) => {
        if (e) e.preventDefault();
        uncheckOptions(); // unchecks all the options
        axios
            .get('http://localhost:5000/api/captcha/requestCaptcha')
            .then((response) => {
                setCaptcha(response.data);
            })
            .catch((err) => {
                console.log(err);
            });
        console.log('captcha refreshed')
    }

    const toggleChecked = (index) => {
        const selected = selectedOptions;
        if (!selectedOptions[index]) { // not selected -> select
            selected[index] = 1;
            setSelectedOptions([...selected]);
        } else { // selected -> unselect
            selected[index] = 0;
            setSelectedOptions([...selected]);
        }
    };

    function toggleCheckedStyles(index) {
        if (selectedOptions[index] === 1) {
            return "option checked";
        } else {
            return "option unchecked";
        }
    };


    function toggleCaptchaBtnClass() {
        if (captchaAnswers.length === 2) {
            return "captcha-btn solved";
        } else {
            return "captcha-btn";
        }
    };


    return (
        <>
            {!isActive
                ? <button type="button" onClick={openDialog} className={captchaSolved ? 'captcha-btn' + ' solved' : 'captcha-btn'}>I'm not a robot</button>
                : <div className='captcha-dialog' role='dialog'>
                    <section className='captcha-form' onSubmit={handleSubmit}>
                        <h1>What can you see in this picture?</h1>
                        <img className='captcha-img' src={'data:image/png;base64,' + captcha.base64Image} alt='captcha' />
                        {/* <img className='captcha-img' src={logo} alt='captcha' /> */}
                        <section className='options-container'>
                            {options ? options.map((element, index) => (
                                <div
                                    key={index}
                                    className={toggleCheckedStyles(index)}
                                    onClick={() => { toggleChecked(index); }}
                                >{element}</div>
                            )) : <span>Loading...</span>}
                        </section>
                        <section className='btns-wrap'>
                            <button className='refresh-btn' onClick={refreshCaptcha}>Refresh</button>
                            {/* momentaneamente submit chiude il dialog */}
                            <button type='submit' onClick={handleSubmit}>Submit</button>
                        </section>
                    </section>
                </div >
            }
        </>
    );
}
