import apiClient from './index'

const foodApi = {
    // Function to get all foods
    getFoods() {
        return apiClient.get('/foods')
    },

    // Function to get a food item by its ID
    getFoodById(foodId) {
        return apiClient.get(`/foods/${foodId}`)
    },

    // Function to create a new food item
    createFood(foodData) {
        return apiClient.post('/foods', foodData)
    },

    // Function to update a food item
    updateFood(foodId, foodData) {
        return apiClient.put(`/foods/${foodId}`, foodData)
    },

    // Function to delete a food item
    deleteFood(foodId) {
        return apiClient.delete(`/foods/${foodId}`)
    },
}

export default foodApi
