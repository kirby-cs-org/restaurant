import apiClient from './index'

const authApi = {
    signIn(userData) {
        return apiClient.post('/auth/signin', userData)
    },

    signUp(userData) {
        return apiClient.post('/auth/signup', userData)
    },
}

export default authApi
