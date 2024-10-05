<template>
    <div
        class="flex justify-between items-center p-4 shadow-md rounded-lg bg-white mb-4"
    >
        <!-- Order Details -->
        <div class="flex flex-col">
            <span class="text-lg font-bold">Order #{{ index }}</span>
            <span class="text-sm text-gray-500">By {{ username }}</span>
            <span class="text-sm text-gray-500"
                >Time: {{ order.createdAt }}</span
            >
            <span class="text-sm" :class="statusClass"
                >Status: {{ order.status }}</span
            >
        </div>

        <!-- Order Actions and Amount -->
        <div class="flex flex-col items-end">
            <span class="text-xl font-bold text-gray-800"
                >{{ order.total }} ฿</span
            >
            <button
                class="px-4 py-2 mt-2 bg-green-500 text-white rounded-md"
                @click="markOrderSuccess(order.id)"
            >
                Mark as Success
            </button>
            <button
                class="px-4 py-2 mt-2 bg-gray-200 text-gray-700 rounded-md"
                @click="viewOrderDetail(order.id)"
            >
                View Details
            </button>
        </div>
    </div>
</template>

<script setup>
import orderApi from '@/api/orderApi'
import { ref, onMounted, computed } from 'vue'

// Props
const props = defineProps({
    order: {
        type: Object,
        required: true,
    },
    index: Number,
})

// Username state
const username = ref('')

// Fetch the username when the component is mounted
const getUsernameById = async (id) => {
    try {
        const res = await orderApi.getOrderUserById(id)
        console.log(res.data.username)
        username.value = res.data.username // Update the username correctly
    } catch (error) {
        console.error('Error fetching username:', error)
    }
}

onMounted(() => {
    getUsernameById(props.order.id)
})

// Emit events to parent component
const emit = defineEmits(['mark-success', 'view-detail'])

// Dynamically set the class for status
const statusClass = computed(() => {
    switch (props.order.status) {
        case 'Pending':
            return 'text-yellow-500'
        case 'Preparing':
            return 'text-orange-500'
        case 'In delivery':
            return 'text-blue-500'
        case 'Delivered':
            return 'text-green-500'
        case 'Cancelled':
            return 'text-red-500'
        default:
            return ''
    }
})

// Mark order as success
const markOrderSuccess = (id) => {
    emit('mark-success', id)
}

// View order details
const viewOrderDetail = (id) => {
    emit('view-detail', id)
}
</script>
