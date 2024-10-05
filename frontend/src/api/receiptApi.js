import apiClient from './index'

const receiptApi = {
    getReceiptById(id) {
        return apiClient.get(`/order/${id}/receipt`)
    }
}

export default receiptApi
