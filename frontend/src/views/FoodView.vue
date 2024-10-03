<script setup>
import { onMounted } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import Card from '@/components/Card.vue'
import Search from '@/components/Search.vue'
import { foodsStore } from '@/stores/cart'

const foodStore = foodsStore()

const fetchFoods = async () => {
    await foodStore.fetchFoods()
}

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
            <section class="search">
                <Search />
            </section>
            <!-- Name section  -->
            <section class="w-full h-12">
                <span class="font-bold text-3xl">Menu</span>
            </section>
            <!-- food list section  -->
            <section class="foods">
                <ul class="my_grid">
                    <li v-for="food in foodStore.foods" :key="food.id">
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

<style scoped>
.my_grid {
    display: grid;
    gap: 24px 4px;
    grid-template-columns: repeat(auto-fill, minmax(234px, 1fr));
}
</style>
