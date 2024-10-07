import apiClient from './index'

const foodApi = {
    getFoods() {
        return apiClient.get('/foods', {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    getFoodById(foodId) {
        return apiClient.get(`/foods/${foodId}`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    createFood(foodData) {
        return apiClient.post('/foods', foodData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    updateFood(foodId, foodData) {
        return apiClient.put(`/foods/${foodId}`, foodData, {
            headers: { 'Content-Type': 'application/json' },
        })
    },

    deleteFood(foodId) {
        return apiClient.delete(`/foods/${foodId}`, {
            headers: { 'Content-Type': 'application/json' },
        })
    },
}

export default foodApi
