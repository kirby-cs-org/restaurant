<script setup>
import { ref, onMounted, computed } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import Search from '@/components/Search.vue'
import OrderCard from '@/components/OrderCard.vue'
import orderApi from '@/api/orderApi'
import userApi from '@/api/userApi'

const orders = ref([])
const dropdownVisible = ref(false)
const selectedOption = ref('')
const searchQuery = ref('')

const fetchOrders = async () => {
    try {
        const orderResponse = await orderApi.getOrders()
        const userResponse = await userApi.getUsers()

        orders.value = orderResponse.data
        username.value = userResponse.data
        role.value = userResponse.data

        if (role.value === 'CUSTOMER') {
            filtered = filtered.filter((order) => order.username === username.value)
        }
    } catch (error) {
        console.error('Error fetching orders:', error)
    }
}

onMounted(() => {
    fetchOrders()
})

const toggleDropdown = () => {
    dropdownVisible.value = !dropdownVisible.value
}

const selectOption = (option) => {
    selectedOption.value = option
    dropdownVisible.value = false
}

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
                            'CANCEL',
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
