import apiClient from './index'

const receiptApi = {
    getReceiptById(id) {
        return apiClient.get(`/order/${id}/receipt`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default receiptApi
