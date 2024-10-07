import apiClient from './index'

const userApi = {
    getUsers() {
        return apiClient.get('/user', {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getUserById(userId) {
        return apiClient.get(`/user/${userId}`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getUserByJwt() {
        return apiClient.get('/user/jwt', localStorage.getItem('token'), {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default userApi
