<script setup>
import { ref } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import ingredientApi from '@/api/ingredientApi'
import router from '@/router';

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

const goBack = () => {
    router.push('/ingredient')
}
</script>

<template>
    <div class="flex">
        <aside class="fixed"><Sidebar /></aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex-col gap-4 bg-gray-50 h-screen"
        >
                    <!-- Back button -->
                    <div
                class="flex bg-[#C7C7C7FF] text-white rounded-lg px-3 py-2 w-10 mb-4"
                @click="goBack"
            >
                <button>
                    <fa icon="arrow-left" />
                </button>
            </div>
            <section class="items-center justify-center flex-col w-10/12 h-12 mx-auto">

                <h2 class="text-4xl font-bold mb-4">Add New Ingredient</h2>
                <form
                    @submit.prevent="submitIngredient"
                    class="w-full p-10 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD]"
                >
                    <div>
                        <label for="name" class="block font-semibold mb-1 pt-3"
                            >Name</label
                        >
                        <input
                            id="name"
                            v-model="ingredientData.name"
                            required
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <div>
                        <label for="amount" class="block font-semibold mb-1 pt-3"
                            >Amount</label
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
                        <label for="qty" class="block font-semibold mb-1 pt-3"
                            >Quantity</label
                        >
                        <input
                            type="number"
                            id="qty"
                            v-model="ingredientData.qty"
                            required
                            min="0"
                            class="border p-2 rounded w-full"
                        />
                    </div>

                    <div>
                        <label for="status" class="block font-semibold mb-1 pt-3"
                            >Status</label
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
                        <label for="expireDate" class="block font-semibold mb-1 pt-3"
                            >Expiration Date</label
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
                        <label for="image" class="block font-semibold mb-1 pt-3"
                            >Upload Image</label
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
                    <div class="flex items-center justify-center">
                        <button
                        type="submit"
                        class="w-3/6 mt-4 bg-yellow-300 text- p-2 rounded shadow-md transition-transform"
                        onmouseover="this.style.transform='scale(1.05)'" 
                        onmouseout="this.style.transform='scale(1)'"
                    >
                        Add Ingredient
                    </button>
                    </div>
                </form>
            </section>
        </main>
    </div>
</template>
