<template>
    <main class="w-full">
        <Sidebar class="fixed" />
        <div class="ml-[14rem] cart-view">
            <h1>Your Cart</h1>
            <div v-if="cart.length === 0">
                <p>Your cart is empty.</p>
            </div>
            <ul v-else>
                <li v-for="item in cart" :key="item.food.id" class="cart-item">
                    <div class="item-details">
                        <h3>{{ item.food.name }}</h3>
                        <p>Price: {{ item.food.price }} ฿</p>
                        <p>Quantity: {{ item.quantity }}</p>
                    </div>
                    <div class="item-actions">
                        <button @click="removeFromCart(item.food.id)">
                            Remove
                        </button>
                    </div>
                </li>
            </ul>
            <div class="total">
                <h2>Total: {{ total }} ฿</h2>
            </div>
        </div>
    </main>
</template>

<script setup>
import { computed } from 'vue'
import { foodsStore } from '@/stores/cart'
import Sidebar from '@/components/Sidebar.vue'

const foodStore = foodsStore()
const cart = foodStore.cart

const removeFromCart = (id) => {
    // Check if the item quantity is greater than 0 before removing
    const item = cart.find((item) => item.food.id === id)
    if (item && item.quantity > 0) {
        foodStore.removeFromCart(id)
    }
}

const total = computed(() => {
    return cart.reduce((sum, item) => sum + item.food.price * item.quantity, 0)
})
</script>

<style scoped>
.cart-view {
    padding: 20px;
}

.cart-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 10px 0;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.item-details {
    flex-grow: 1;
}

.item-actions button {
    background-color: #ff4757;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 5px;
    cursor: pointer;
}

.item-actions button:hover {
    background-color: #ff6b81;
}

.total {
    margin-top: 20px;
    font-weight: bold;
}
</style>
