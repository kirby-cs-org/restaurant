import apiClient from './index'

const userApi = {
    getUsers() {
        return apiClient.get('/user')
    },

    getUserById(userId) {
        return apiClient.get(`/user/${userId}`)
    },

    getUserByJwt() {
        return apiClient.get('/user/jwt', localStorage.getItem('token'))
    },
    
}

export default userApi
