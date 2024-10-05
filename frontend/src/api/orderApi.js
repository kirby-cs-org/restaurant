import apiClient from './index'

const orderApi = {
    getOrders() {
        return apiClient.get('/order')
    },

    getOrderById(orderId) {
        return apiClient.get(`/order/${orderId}`)
    },

    createOrder(orderData) {
        return apiClient.post('/order', orderData)
    },
}

export default orderApi
