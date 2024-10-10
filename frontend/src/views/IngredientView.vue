<script setup>
import { ref, onMounted, computed } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import IngredientCard from '@/components/IngredientCard.vue'
import ingredientApi from '@/api/ingredientApi'
import Search from '@/components/Search.vue'
import router from '@/router'

const ingredients = ref([])
const searchQuery = ref('')

const filteredIngredients = computed(() => {
    if (!searchQuery.value) return ingredients.value
    return ingredients.value.filter((ingredient) =>
        ingredient.name.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
})

const addingredients = () => {
    router.push('/addingredients')
}

const fetchIngredients = async () => {
    try {
        const { data: response } = await ingredientApi.getIngredient()
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
            <section class="flex gap-4">
                <Search @update-search="searchQuery = $event" />
                <div
                    style="
                        display: flex;
                        background-color: #e5e7eb;
                        align-items: center;
                        border-radius: 0.5rem;
                        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                        padding: 0.5rem;
                        transition: transform 0.3s;
                    "
                    onmouseover="this.style.transform='scale(1.05)'"
                    onmouseout="this.style.transform='scale(1)'"
                >
                    <span
                        style="
                            display: flex;
                            align-items: center;
                            cursor: pointer;
                        "
                        @click="addingredients()"
                    >
                        <fa icon="add" style="margin-right: 0.25rem" />
                        Ingredient
                    </span>
                </div>
            </section>
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
