<template>
  <div class="p-4 border rounded shadow-md bg-white">
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
    required: true
  }
})
</script>
