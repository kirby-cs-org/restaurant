<template>
    <div class="cart-view">
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
</template>

<script>
import { defineComponent, computed } from 'vue'
import { foodsStore } from '@/stores/cart' // Adjust the path as needed

export default defineComponent({
    setup() {
        const foodStore = foodsStore()
        const cart = foodStore.cart

        const removeFromCart = (id) => {
            foodStore.removeFromCart(id)
        }

        const total = computed(() => {
            return cart.reduce(
                (sum, item) => sum + item.food.price * item.quantity,
                0
            )
        })

        return { cart, removeFromCart, total }
    },
})
</script>

<style scoped>
.cart-view {
    padding: 20px;
}

.cart-item {
    display: flex;
    justify-content: space-between;
    margin: 10px 0;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.item-details {
    flex-grow: 1;
}

.item-actions {
    display: flex;
    align-items: center;
}

button {
    padding: 5px 10px;
    background-color: #f44336;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #d32f2f;
}

.total {
    margin-top: 20px;
    font-size: 18px;
    font-weight: bold;
}
</style>
