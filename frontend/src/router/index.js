import { createRouter, createWebHistory } from 'vue-router'
import SignupView from '@/views/SignupView.vue'
import SigninView from '@/views/SigninView.vue'
import FoodView from '@/views/FoodView.vue'
import OrderView from '@/views/OrderView.vue'
import IngredientView from '@/views/IngredientView.vue'
import TransactionView from '@/views/TransactionView.vue'
import ReceiptView from '@/views/ReceiptView.vue'
import DashboardView from '@/views/DashboardView.vue'
import CartView from '@/views/CartView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/signup',
            name: 'signup',
            component: SignupView,
        },
        {
            path: '/signin',
            name: 'signin',
            component: SigninView,
        },
        {
            path: '/food',
            name: 'food',
            component: FoodView,
        },
        {
            path: '/order',
            name: 'order',
            component: OrderView,
        },
        {
            path: '/ingredient',
            name: 'ingredient',
            component: IngredientView,
        },
        {
            path: '/transaction',
            name: 'transaction',
            component: TransactionView,
        },
        {
            path: '/receipt',
            name: 'receipt',
            component: ReceiptView,
        },
        {
            path: '/dashboard',
            name: 'dashboard',
            component: DashboardView,
        },
        {
            path: '/cart',
            name: 'cart',
            component: CartView,
        },
    ],
})

export default router
