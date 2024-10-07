<template>
<div class="flex justify-between px-3 py-2 shadow-md rounded-xl bg-white mb-5">
    <div class="flex flex-col">
        <div class="flex items-center">
            <span class="text-4xl pr-2">Order #{{ index }}</span>
            <span class="align-text-bottom text-lg " 
                  :style="{ color: order.status === 'COMPLETE' ? '#ADE92E' : order.status === 'PENDING' ? '#FF6B00' : '#FF5151' }"> 
                ● 
            </span>
            <span class="align-text-bottom text-lg ml-1">{{ order.status }}</span>
        </div>
        <span class="text-md text-gray-500 py-1 ml-1">By {{ username }}</span>
        <span class="text-md text-gray-500 ml-1">Time: {{ order.createdAt }}</span>
        <span class="py-2 pl-1">
    <button class="px-10 py-2 mt-2 mr-10 rounded-lg" style="background-color: #BCF14A; color: #000000;" @click="markOrderSuccess(order.id)">
        Mark as Success
    </button>
    <button class="px-12 py-2 mt-2 rounded-lg" style="background-color: #F6F6F6; color: #000000;" @click="viewOrderDetail()">
        View Details
    </button>
</span>
    </div>
        <div class="flex flex-col items-end">
            <span class="text-4xl text-gray-750"
                >{{ order.total }} ฿</span
            >
        </div>
    </div>
</template>

<script setup>
import orderApi from '@/api/orderApi'
import { ref, onMounted} from 'vue'
import router from '@/router';

const props = defineProps({
    order: {
        type: Object,
        required: true,
    },
    index: Number,
})

const username = ref('')

const getUsernameById = async (id) => {
    try {
        const res = await orderApi.getOrderUserById(id)
        console.log(res.data.username)
        username.value = res.data.username
    } catch (error) {
        console.error('Error fetching username:', error)
    }
}
onMounted(() => {
    getUsernameById(props.order.id)
})

const emit = defineEmits(['mark-success', 'view-detail'])

const markOrderSuccess = (id) => {
    emit('mark-success', id)
}

const viewOrderDetail = () => {
router.push({
  name: 'receipt',
  params: { 
    id: props.order.id}
})

}
</script>
