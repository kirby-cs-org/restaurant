<script setup>
import { ref } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import ingredientApi from '@/api/ingredientApi'

// Utility function to format the date to dd-mm-yy
const formatDateToDDMMYY = (dateStr) => {
    const date = new Date(dateStr)
    const day = String(date.getDate()).padStart(2, '0')
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const year = String(date.getFullYear()) // Get last two digits of the year
    return `${day}-${month}-${year}`
}

const ingredientData = ref({
    name: '',
    amount: 0.0,
    status: 'AVAILABLE',
    qty: 0,
    expireDate: '', // This will store the formatted date
})

const imageFile = ref(null) // To store the selected image
const imagePreview = ref('') // To store the image preview URL

const handleFileUpload = (event) => {
    console.log(ingredientData)
    const file = event.target.files?.[0]
    if (file) {
        imageFile.value = file
        imagePreview.value = URL.createObjectURL(file) // Generate preview URL
    }
}

const submitIngredient = async () => {
    if (!imageFile.value) {
        alert('Please upload an image.')
        return
    }

    // Format the expireDate before submitting
    ingredientData.value.expireDate = formatDateToDDMMYY(
        ingredientData.value.expireDate
    )

    try {
        const formData = new FormData()

        // Append ingredient data as a JSON Blob
        formData.append(
            'ingredient',
            new Blob([JSON.stringify(ingredientData.value)], {
                type: 'application/json',
            })
        )

        // Append the image file directly
        formData.append('image', imageFile.value)

        console.log(formData)

        // Send the form data to the API
        await ingredientApi.createIngredient(formData)
        alert('Ingredient added successfully!')
    } catch (error) {
        console.error('Error adding ingredient:', error.response?.data)
        alert(
            `Failed to add ingredient: ${
                error.response?.data?.message || error.message
            }`
        )
    }
}
</script>

<template>
    <div class="flex">
        <aside class="fixed"><Sidebar /></aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen"
        >
            <section class="w-full h-12">
                <h2 class="text-xl font-bold mb-4">Add New Ingredient</h2>
                <form
                    @submit.prevent="submitIngredient"
                    class="flex flex-col gap-4"
                >
                    <div>
                        <label for="name" class="block font-semibold mb-1"
                            >Name:</label
                        >
                        <input
                            id="name"
                            v-model="ingredientData.name"
                            required
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <div>
                        <label for="amount" class="block font-semibold mb-1"
                            >Amount:</label
                        >
                        <input
                            type="number"
                            id="amount"
                            v-model="ingredientData.amount"
                            required
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <div>
                        <label for="qty" class="block font-semibold mb-1"
                            >Quantity:</label
                        >
                        <input
                            type="number"
                            id="qty"
                            v-model="ingredientData.qty"
                            required
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <div>
                        <label for="status" class="block font-semibold mb-1"
                            >Status:</label
                        >
                        <select
                            id="status"
                            v-model="ingredientData.status"
                            class="border p-2 rounded w-full"
                        >
                            <option value="AVAILABLE">Available</option>
                            <option value="OUT_OF_STOCK">Out of Stock</option>
                        </select>
                    </div>

                    <div>
                        <label for="expireDate" class="block font-semibold mb-1"
                            >Expiration Date (dd-mm-yy):</label
                        >
                        <input
                            type="date"
                            id="expireDate"
                            v-model="ingredientData.expireDate"
                            required
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <!-- Image upload field -->
                    <div>
                        <label for="image" class="block font-semibold mb-1"
                            >Upload Image:</label
                        >
                        <input
                            type="file"
                            id="image"
                            @change="handleFileUpload"
                            accept="image/*"
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <!-- Image preview -->
                    <div v-if="imagePreview" class="mt-4">
                        <img
                            :src="imagePreview"
                            alt="Image Preview"
                            class="w-32 h-32 object-cover"
                        />
                    </div>

                    <button
                        type="submit"
                        class="mt-4 bg-blue-500 text-white p-2 rounded"
                    >
                        Add Ingredient
                    </button>
                </form>
            </section>
        </main>
    </div>
</template>
