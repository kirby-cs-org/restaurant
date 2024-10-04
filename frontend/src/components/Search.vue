<template>
    <section class="w-full flex justify-between items-center">
        <div
            class="flex bg-gray-200 w-96 items-center rounded-lg shadow-md pl-2"
        >
            <fa icon="search" />
            <input
                v-model="searchTerm"
                @input="emitSearch"
                class="w-full h-12 bg-gray-200 px-4 outline-none rounded-lg"
                placeholder="Search..."
            />
        </div>
        <span class="flex gap-8">
            <div class="flex items-center cursor-pointer">
                <fa icon="bell" />
            </div>
            <RouterLink to="/cart">
                <div
                    class="bg-gray-200 py-2 px-4 rounded-md cursor-pointer hover:shadow-md duration-200 gap-2 flex items-center"
                >
                    <fa icon="cart-shopping" />
                    <span>Cart</span>
                </div>
            </RouterLink>
        </span>
    </section>
</template>

<script setup>
import { ref, defineEmits } from 'vue'

const emit = defineEmits()

const searchTerm = ref('')

// Emit the search term whenever it changes
const emitSearch = () => {
    emit('update-search', searchTerm.value)
    console.log(searchTerm.value)
}
</script>
