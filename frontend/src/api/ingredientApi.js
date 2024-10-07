import apiClient from './index'

const ingredientApi = {
    getIngredient() {
        return apiClient.get('/ingredient', {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getIngredientById(ingredientId) {
        return apiClient.get(`/ingredient/${ingredientId}`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    createIngredient(ingredientData) {
        return apiClient.post('/ingredient', ingredientData) // multipart/form-data
    },

    updateIngredient(ingredientData) {
        return apiClient.patch(`/ingredient`, ingredientData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    deleteIngredient(ingredientId) {
        return apiClient.delete(`/ingredient/${ingredientId}`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default ingredientApi