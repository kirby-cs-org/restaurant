import apiClient from './index'

const receiptApi = {
    getReceiptById(receiptId) {
        return apiClient.get(`/receipt/${receiptId}`)
    },
    createReceipt(receiptData) {
        return apiClient.post('/receipt', receiptData)
    },
    updateReceipt(receiptId, receiptData) {
        return apiClient.put(`/receipt/${receiptId}`, receiptData)
    },
    deleteReceipt(receiptId) {
        return apiClient.delete(`/receipt/${receiptId}`)
    },
}

export default receiptApi
