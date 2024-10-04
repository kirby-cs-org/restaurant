<script setup>
import { ref, onMounted } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import IngredientCard from '@/components/IngredientCard.vue'
import ingredientApi from '@/api/ingredientApi'

// Define a reactive variable to store the ingredients data
const ingredients = ref([])

// Fetch the ingredients when the component is mounted
const fetchIngredients = async () => {
  try {
    const response = await ingredientApi.getIngredient()
    ingredients.value = response.data
    console.log(response.data) // ตรวจสอบว่าข้อมูลถูกดึงมา
  } catch (error) {
    console.error('Error fetching ingredients:', error)
  }
}

// Call the fetchIngredients function when the component is mounted
onMounted(() => {
  fetchIngredients()
})
</script>

<template>
  <div class="flex">
    <aside class="fixed">
      <Sidebar />
    </aside>
    <main class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen">
      <div class="text-2xl font-bold">Ingredients</div>
      <section class="ingredient w-full h-screen bg-pink-100">
        <ul class="grid grid-cols-3 gap-4">
          <li v-for="ingredient in ingredients" :key="ingredient.id" class="border p-4 rounded-lg shadow-md">
            <!-- Pass each ingredient's data to IngredientCard component -->
            <IngredientCard :ingredientData="ingredient" />
          </li>
        </ul>
      </section>
    </main>
  </div>
</template>

<style scoped>
  /* ปรับขนาด layout ให้องค์ประกอบต่างๆ แสดงผลถูกต้อง */
  .ingredient {
    max-height: 80vh;
    overflow-y: auto;
  }
</style>
