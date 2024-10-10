<template>
    <div
        class="border rounded-lg shadow-md bg-white w-56 h-72 cursor-pointer hover:opacity-50 duration-200"
    >
        <img class="rounded-t-lg h-36 w-full" :src="ingredientData.imagePath" />
        <div class="p-3">
            <h3 class="font-bold text-lg">{{ ingredientData.name }}</h3>
            <p>Quantity: {{ ingredientData.qty }} Kg.</p>
            <p>Status: {{ ingredientData.status }}</p>
            <p>ExpireDate: {{ ingredientData.expireDate }}</p>
        </div>
    </div>
</template>

<script setup>
import userApi from '@/api/userApi'
import { onMounted, ref } from 'vue'

const props = defineProps({
    ingredientData: {
        type: Object,
        required: true,
    },
})

const role = ref('')

onMounted(async () => {
    const { data: res } = await userApi.getUserByJwt()
    role.value = res.data.role
})
</script>
