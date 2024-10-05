import apiClient from './index'

const orderApi = {
    getOrders() {
        return apiClient.get('/foods')
    },

    getOrderById(orderId) {
        return apiClient.get(`/foods/${orderId}`)
    },

    createOrder(orderData) {
        return apiClient.post('/foods', orderData)
    },
}

export default orderApi
