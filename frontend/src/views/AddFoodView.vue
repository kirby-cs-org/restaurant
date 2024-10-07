<script setup>
import { ref, onMounted } from 'vue';
import Sidebar from '@/components/Sidebar.vue';
import foodApi from '@/api/foodApi';
import ingredientApi from '@/api/ingredientApi';

const foodData = ref({
  name: '',
  price: null,
  status: 'AVAILABLE',
  recipes: []
});

const imageFile = ref(null);
const imagePreview = ref('');
const ingredients = ref([]);

const fetchIngredients = async () => {
  try {
    const response = await ingredientApi.getIngredient();
    ingredients.value = response.data;
  } catch (error) {
    console.error('Error fetching ingredients:', error);
  }
};

onMounted(() => {
  fetchIngredients();
});

// Handle ingredient selection
const toggleIngredient = (ingredient) => {
  const index = foodData.value.recipes.findIndex(rec => rec.id === ingredient.id);
  if (index === -1) {
    // Add ingredient if not already in recipes
    foodData.value.recipes.push({
      id: ingredient.id,
      name: ingredient.name,
      price: ingredient.price,
      qty: ingredient.qty,
      status: 'AVAILABLE',
      expireDate: ingredient.expireDate
    });
  } else {
    // Remove ingredient if already selected
    foodData.value.recipes.splice(index, 1);
  }
};

// Handle image file upload
const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    imageFile.value = file;
    imagePreview.value = URL.createObjectURL(file);
  }
};

// Handle food creation
const createFood = async () => {
  console.log("ky");
  try {
    if (!imageFile.value) {
        alert('Please upload an image.')
        return
    }
    // Create FormData to handle both the food data and the image
    formData.append(
            'food',
            new Blob([JSON.stringify(foodData.value)], {
                type: 'application/json',
            })
        )
    formData.append('image', imageFile.value); // Append the image file
    

    const response = await foodApi.createFood(formData);
    console.log('Food created successfully:', response.data);
    // Optionally reset form or navigate to another page
  } catch (error) {
    console.error('Error adding food:', error.response?.data)
        alert(
            `Failed to add food: ${
                error.response?.data?.message || error.message
            }`
        )
  }
};
</script>

<template>
  <div class="flex">
    <aside class="fixed">
      <Sidebar />
    </aside>
    <main class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen">
      <h1>Create Food</h1>
      <form @submit.prevent="createFood">

        <input v-model="foodData.name" placeholder="Food Name" required />
        <input v-model.number="foodData.price" type="number" placeholder="Price" required />
        <div>
              <label for="status" class="block font-semibold mb-1"
                >Status:</label>
                  <select
                      id="status"
                      v-model="foodData.status"
                      class="border p-2 rounded w-full"
                    >
                    <option value="AVAILABLE">Available</option>
                    <option value="OUT_OF_STOCK">Out_of_Stock</option>
                  </select>
        </div>
        <!-- Image upload -->
        <label>Upload Food Image:</label>
        <input type="file" @change="handleFileUpload" accept="image/*" required />
        <div v-if="imagePreview" class="mt-4">
          <img :src="imagePreview" alt="Image Preview" class="w-32 h-32 object-cover" />
        </div>

        <!-- Select ingredients -->
        <label>Select Ingredients:</label>
        <div v-if="ingredients.length">
          <div v-for="ingredient in ingredients" :key="ingredient.id" class="flex items-center">
            <input type="checkbox" :value="ingredient.id" @change="toggleIngredient(ingredient)" />
            <span>{{ ingredient.name }} ({{ ingredient.price }}$) </span>
          </div>
        </div>
        <div v-else>
          <p>Loading ingredients...</p>
        </div>

        <button type="submit">Create Food</button>
      </form>
    </main>
  </div>
</template>

<style scoped>
input {
  display: block;
  margin-bottom: 10px;
}

button {
  padding: 8px 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
