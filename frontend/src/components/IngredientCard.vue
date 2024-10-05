<template>
    <div class="border rounded-lg shadow-md bg-white w-56 h-[17rem] bg-red-100">
        <img class="rounded-t-lg h-3/5" :src="ingredientData.imagePath" />
        <div class="h-2/5 px-2">
            <h3 class="font-bold text-lg">{{ ingredientData.name }}</h3>
            <p>Quantity: {{ ingredientData.qty }} Kg.</p>
            <p>Status: {{ ingredientData.status }}</p>
            <p>ExpireDate: {{ ingredientData.expireDate }}</p>
        </div>
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
