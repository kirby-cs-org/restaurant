<script setup>
import orderApi from '@/api/orderApi'
import Sidebar from '@/components/Sidebar.vue'
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

onMounted(async () => {
    const orderId = route.query.id
    if (orderId) {
        try {
            const res = await orderApi.updateOrderStatus({
                id: orderId,
                status: 'COMPLETE',
            })
            console.log('Order status updated:', res.data)
        } catch (error) {
            console.error(
                'Failed to update order status:',
                error.response ? error.response.data : error.message
            )
        }
    } else {
        console.error('Order ID not found in the URL')
    }
})
</script>

<template>
    <div Class="flex">
        <aside class="fixed">
            <Sidebar />
        </aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen items-center"
        >
            <h><b>Payment Successful</b></h>
            <div>
                <img src="@/assets/success_icon.png" alt="success icon" />
            </div>
        </main>
    </div>
</template>
