import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

describe('profile access request', () => {
    let mock;

    beforeEach(() => {
        mock = new MockAdapter(axios);
    });

    afterEach(() => {
        mock.restore();
    });


    it('should receive user data if successful', async () => {
        const userData = {
            "username": 'username',
            "firstName": 'firstName',
            "lastName": 'lastName',
            "gender": 'gender',
            "birthDate": 'birthDate',
            "profilePicture": 'profilePicture'
        };

        const token = {
            "jwtToken": "mockToken"
        }

        mock.onGet("http://localhost:5000/api/users/me", { headers: { "Authorization": `Bearer ${token.jwtToken}` } }).reply(200, userData)

        const response = await axios.get('http://localhost:5000/api/users/me', { headers: { "Authorization": `Bearer ${token.jwtToken}` } });

        expect(mock.history.get.length).toBe(1);
        expect(mock.history.get[0].url).toBe('http://localhost:5000/api/users/me');

        expect(response.status).toBe(200);
        expect(response.data).toHaveProperty('username');
        expect(response.data).toHaveProperty('firstName');
        expect(response.data).toHaveProperty('lastName');
        expect(response.data).toHaveProperty('gender');
        expect(response.data).toHaveProperty('birthDate');
        expect(response.data).toHaveProperty('profilePicture');
    });
});



describe('invalid profile access request', () => {

    it('should not receive a valid response', async () => {
        const token = {
            "jwtToken": 'wrongToken'
        };
        expect.assertions(2);
        try {
            await axios.get('http://localhost:5000/api/users/me', { headers: { "Authorization": `Bearer ${token.jwtToken}` } })
        } catch (error) {
            expect(error.name).toEqual('AxiosError');
            expect(error.status).toEqual(undefined); // invalid data
        }
    });
});