<script setup>
import { computed, onMounted, ref } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import Card from '@/components/Card.vue'
import Search from '@/components/Search.vue'
import { foodsStore } from '@/stores/cart'
import Cart from '@/components/Cart.vue'

const foodStore = foodsStore()
const searchQuery = ref('')

const fetchFoods = async () => {
    await foodStore.fetchFoods()
}

const filteredFoods = computed(() => {
    if (!searchQuery.value) return foodStore.foods
    return foodStore.foods.filter((food) =>
        food.name.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
})

onMounted(() => {
    fetchFoods()
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
            <!-- search filter section  -->
            <section class="flex gap-4">
                <Search @update-search="searchQuery = $event" />
                <div class="flex items-center cursor-pointer">
                    <fa icon="bell" />
                </div>
                <Cart :qty="foodStore.cartQty" />
            </section>
            <!-- Name section  -->
            <section class="w-full h-12">
                <span class="font-bold text-3xl">Menu</span>
            </section>
            <!-- food list section  -->
            <section class="foods">
                <ul class="foods-grid">
                    <li v-for="food in filteredFoods" :key="food.id">
                        <Card :foodsData="food" />
                    </li>
                </ul>
                <p
                    v-if="foodStore.foods.length === 0"
                    class="mt-4 text-gray-500"
                >
                    No food available.
                </p>
            </section>
        </main>
    </div>
</template>
