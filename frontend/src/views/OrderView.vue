<script setup>
import { ref, onMounted, computed } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import Search from '@/components/Search.vue'
import OrderCard from '@/components/OrderCard.vue'
import orderApi from '@/api/orderApi'

// State for orders, dropdown, and search query
const orders = ref([])
const dropdownVisible = ref(false)
const selectedOption = ref('')
const searchQuery = ref('')

// Fetch orders when the component is mounted
const fetchOrders = async () => {
    try {
        const response = await orderApi.getOrders()
        orders.value = response.data // Assuming this returns an array of orders
    } catch (error) {
        console.error('Error fetching orders:', error)
    }
}

onMounted(() => {
    fetchOrders()
})

// Toggle dropdown visibility
const toggleDropdown = () => {
    dropdownVisible.value = !dropdownVisible.value
}

// Select dropdown option
const selectOption = (option) => {
    selectedOption.value = option
    dropdownVisible.value = false
}

// Computed orders based on selected dropdown and search query
const filteredOrders = computed(() => {
    let filtered = orders.value
    if (selectedOption.value && selectedOption.value !== 'All Orders') {
        filtered = filtered.filter(
            (order) => order.status === selectedOption.value
        )
    }
    if (searchQuery.value) {
        filtered = filtered.filter((order) =>
            order.user.toLowerCase().includes(searchQuery.value.toLowerCase())
        )
    }
    return filtered
})

// Handle marking order as success
const handleOrderSuccess = async (orderId) => {
    const order = orders.value.find((o) => o.id === orderId)
    if (order) {
        try {
            await orderApi.createOrder({ ...order, status: 'Success' })
            order.status = 'Success'
        } catch (error) {
            console.error('Error updating order:', error)
        }
    }
}

// Handle viewing order details
const handleViewDetail = (orderId) => {
    console.log('View details for order:', orderId)
}
</script>

<template>
    <div class="flex">
        <aside class="fixed">
            <Sidebar />
        </aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen"
        >
            <!-- Search section -->
            <section class="search">
                <Search @update-search="searchQuery = $event" />
            </section>

            <!-- Orders heading -->
            <section class="w-full">
                <span class="font-bold text-3xl">Order</span>
            </section>

            <!-- Type selection dropdown -->
            <div class="relative inline-block mt-2">
                <button
                    @click="toggleDropdown"
                    class="flex px-2 cursor-pointer gap-2 text-gray-500"
                >
                    {{ selectedOption || 'All Orders' }}
                    <fa icon="sort-down" />
                </button>
                <ul
                    v-if="dropdownVisible"
                    class="absolute bg-white rounded-lg shadow-lg text-gray-500"
                >
                    <li
                        v-for="option in [
                            'PENDING',
                            'COMPLETE',
                            'CACEL',
                            'All Orders',
                        ]"
                        :key="option"
                        @click="selectOption(option)"
                        class="px-4 py-2 hover:bg-gray-100 cursor-pointer rounded-lg"
                    >
                        {{ option }}
                    </li>
                </ul>
            </div>

            <!-- Order list -->
            <section class="mt-4">
                <OrderCard
                    v-for="(order, i) in filteredOrders"
                    :index="i + 1"
                    :key="order.id"
                    :order="order"
                    @mark-success="handleOrderSuccess"
                    @view-detail="handleViewDetail"
                />
            </section>
        </main>
    </div>
</template>

<style scoped>
/* Additional styles if needed */
</style>
