import apiClient from './index'

const ingredientApi = {
    getIngredient() {
        return apiClient.get('/ingredient')
    },

    getIngredientById(ingredientId) {
        return apiClient.get(`/ingredient/${ingredientId}`)
    },

    createIngredient(ingredientData) {
        return apiClient.post('/ingredient', ingredientData)
    },

    updateIngredient(ingredientId, ingredientData) {
        return apiClient.put(`/ingredient/${ingredientId}`, ingredientData)
    },

    deleteIngredient(ingredientId) {
        return apiClient.delete(`/ingredient/${ingredientId}`)
    },
}

export default ingredientApi
