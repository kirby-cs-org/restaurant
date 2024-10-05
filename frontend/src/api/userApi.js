import apiClient from './index'

const userApi = {
    getUsers() {
        return apiClient.get('/user')
    },

    getUserById(userId) {
        return apiClient.get(`/user/${userId}`)
    },
}

export default userApi
