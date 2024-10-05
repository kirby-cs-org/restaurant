import apiClient from './index'

const foodApi = {
    getFoods() {
        return apiClient.get('/foods')
    },

    getFoodById(foodId) {
        return apiClient.get(`/foods/${foodId}`)
    },

    createFood(foodData) {
        return apiClient.post('/foods', foodData)
    },

    updateFood(foodId, foodData) {
        return apiClient.put(`/foods/${foodId}`, foodData)
    },

    deleteFood(foodId) {
        return apiClient.delete(`/foods/${foodId}`)
    },
}

export default foodApi
