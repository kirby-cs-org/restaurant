import apiClient from './index'

const authApi = {
    signIn(userData) {
        return apiClient.post('/auth/signin', userData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    signUp(userData) {
        return apiClient.post('/auth/signup', userData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default authApi
