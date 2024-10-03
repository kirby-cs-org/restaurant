<template>
    <div
        class="flex justify-between items-center flex-col w-64 h-72 rounded-md shadow-md bg-white"
    >
        <div class="w-full h-2/3">
            <img
                :src="props.foodsData.imagePath"
                alt="Food Image"
                class="w-full h-full object-cover rounded-md"
            />
        </div>
        <div class="flex flex-col items-center justify-center h-1/3 w-full">
            <span class="font-bold">{{ props.foodsData.name }}</span>
            <span>{{ props.foodsData.price }} Baht</span>
            <button
                class="px-2 py-1 rounded-md bg-yellow-300 shadow-md hover:bg-yellow-500 duration-100"
                @click="addToCart(props.foodsData)"
            >
                Add to Cart
            </button>
        </div>
    </div>
</template>

<script setup>
import { foodsStore } from '@/stores/cart'
const props = defineProps({
    foodsData: {
        type: Object,
        required: true,
    },
})

const foodStore = foodsStore()

const addToCart = (food) => {
    foodStore.addToCart(food)
}
</script>
