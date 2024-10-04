import axios from 'axios'

const apiClient = axios.create({
    baseURL: 'http://localhost:8088',
    headers: {
        'Content-Type': 'application/json',
        Authorization: localStorage.getItem('token'),
    },
})

apiClient.interceptors.request.use((config) => {
    // You can attach tokens here if needed (e.g., JWT)
    return config
})

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        // Handle errors globally here if necessary
        return Promise.reject(error)
    }
)

export default apiClient
