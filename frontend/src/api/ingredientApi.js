import apiClient from './index'

const ingredientApi = {
    // Function to get all lngretdient
    getIngredient() {
        return apiClient.get('/ingredient')
    },

    // Function to get a ingredient item by its ID
    getIngredientById(ingredientId) {
        return apiClient.get(`/ingredient/${ingredientId}`)
    },

    // Function to create a new ingredient item
    createIngredient(ingredientData) {
        return apiClient.post('/ingredient', ingredientData)
    },

    // Function to update a ingredient item
    updateIngredient(ingredientId, ingredientData) {
        return apiClient.put(`/ingredient/${ingredientId}`, ingredientData)
    },

    // Function to delete a ingredient item
    deleteIngredient(ingredientId) {
        return apiClient.delete(`/ingredient/${ingredientId}`)
    },
}

export default ingredientApi
