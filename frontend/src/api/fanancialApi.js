import apiClient from './index'

const financialApi = {
    getFinancials() {
        return apiClient.get('/financial', {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getFinancialByday() {
        return apiClient.get('/financial', {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default financialApi
