<template>
    <div class="border rounded-lg shadow-md bg-white w-56 h-[20rem]">
        <img class="rounded-t-lg h-36 w-full" :src="ingredientData.imagePath" />
        <div class="p-3">
            <h3 class="font-bold text-lg">{{ ingredientData.name }}</h3>
            <p>Quantity: {{ ingredientData.qty }} Kg.</p>
            <p>Status: {{ ingredientData.status }}</p>
            <p>ExpireDate: {{ ingredientData.expireDate }}</p>
            <button
                v-if="role === 'ADMIN'"
                class="w-full mt-4 bg-red-400 px-4 py-1 rounded-md"
            >
                Edit
            </button>
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
