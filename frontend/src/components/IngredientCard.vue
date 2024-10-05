<template>
    <div class="p-4 border rounded shadow-md bg-white w-56 h-64 bg-red-100">
        <img class="rounded-md h-32" :src="ingredientData.imagePath" />
        <h3 class="font-bold text-lg">{{ ingredientData.name }}</h3>
        <p>Quantity: {{ ingredientData.qty }} Kg.</p>
        <p>Status: {{ ingredientData.status }}</p>
        <p>ExpireDate: {{ ingredientData.expireDate }}</p>
    </div>
</template>

<script setup>
const props = defineProps({
    ingredientData: {
        type: Object,
        required: true,
    },
})
</script>
