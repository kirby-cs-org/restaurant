import apiClient from './index'

const receiptApi = {
    getReceiptById(id) {
        return apiClient.get(`/order/${id}/receipt`)
    },

    getUserById(id){
        return apiClient.get(`/order/${id}/user`)
    },

    getFoodById(id){
        return apiClient.get(`/order/${id}/food`)
    }
}

export default receiptApi
