import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

describe('captcha request', () => {
    it('should return a valid response', async () => {
        const response = await axios.get('http://localhost:5000/api/captcha/requestCaptcha');

        expect(response.status).toBe(200);

        expect(response.data).toHaveProperty('captchaOptions');
        expect(response.data).toHaveProperty('base64Image');
        expect(response.data).toHaveProperty('criptoToken');
    });
});



describe('invalid login request', () => {

    it('should not return a valid response', async () => {
        const loginData = {
            "username": 'username',
            "passwordHash": 'password',
            "captchaToken": 'captcha.criptoToken',
            "captchaAnswers": ['ans1', 'ans2']
        };
        expect.assertions(2);
        try {
            await axios.post("http://localhost:5000/api/auth/authenticate", loginData);
        } catch (error) {
            expect(error.name).toEqual('AxiosError');
            expect(error.status).toEqual(undefined); // invalid data
        }
    });
});

describe('valid login request', () => {
    let mock;

    beforeEach(() => {
        mock = new MockAdapter(axios);
    });

    afterEach(() => {
        mock.restore();
    });



    it('should return a jwt token', async () => {
        const loginData = {
            "username": 'username',
            "passwordHash": 'password',
            "captchaToken": 'criptoToken',
            "captchaAnswers": 'captchaAnswers'
        };

        const expectedResponse = {
            "jwtToken": "mockToken"
        }

        mock.onPost("http://localhost:5000/api/auth/authenticate", loginData).reply(200, expectedResponse)

        const response = await axios.post('http://localhost:5000/api/auth/authenticate', loginData);

        expect(mock.history.post.length).toBe(1);
        expect(mock.history.post[0].url).toBe('http://localhost:5000/api/auth/authenticate');

        expect(response.status).toBe(200);
        expect(response.data).toEqual(expectedResponse);
    });
});