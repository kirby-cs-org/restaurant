import apiClient from './index'

const orderApi = {
    getOrders() {
        return apiClient.get('/order')
    },

    getOrderUserById(orderId) {
        return apiClient.get(`/order/${orderId}/user`)
    },

    createOrder(orderData) {
        return apiClient.post('/order', orderData)
    },

    updateOrderStatus(orderStatus) {
        return apiClient.patch('/order', orderStatus)
    },
}

export default orderApi
