<script setup>
import { ref, onMounted, computed } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import IngredientCard from '@/components/IngredientCard.vue'
import ingredientApi from '@/api/ingredientApi'
import Search from '@/components/Search.vue'

const ingredients = ref([])
const searchQuery = ref('')

const filteredIngredients = computed(() => {
    if (!searchQuery.value) return ingredients.value
    return ingredients.value.filter((ingredient) => {
        ingredient.name.toLowerCase().includes(searchQuery.value.toLowerCase())
    })
})

const fetchIngredients = async () => {
    try {
        const response = await ingredientApi.getIngredient()
        ingredients.value = response.data
        console.log(response.data)
    } catch (error) {
        console.error('Error fetching ingredients:', error)
    }
}

onMounted(() => {
    fetchIngredients()
})
</script>

<template>
    <div class="flex">
        <aside class="fixed">
            <Sidebar />
        </aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen"
        >
            <Search @update-search="searchQuery = $event" />
            <div class="text-2xl font-bold">Ingredients</div>
            <section class="pb-12">
                <ul class="ingredients-grid">
                    <li
                        v-for="ingredient in filteredIngredients"
                        :key="ingredient.id"
                    >
                        <IngredientCard :ingredientData="ingredient" />
                    </li>
                </ul>
            </section>
        </main>
    </div>
</template>
