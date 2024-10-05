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
<<<<<<< HEAD
import SuccessView from '@/views/SuccessView.vue'
import CancelView from '@/views/CancelView.vue'
=======
import FailView from '@/views/FailView.vue'
import SuccessView from '@/views/SuccessView.vue'
>>>>>>> 0b34dc8d40c0bb50447a0b4b5396a625fbc6f996

const routes = [
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
    {
        path: '/payment/success',
        name: 'success',
        component: SuccessView,
    },
    {
        path: '/payment/fail',
        name: 'fail',
        component: CancelView,
    },

]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

// navigation guard
router.beforeEach((to, from, next) => {
    const isAuthenticated = !!localStorage.getItem('token') // Check for token

    // If the route is protected (not signin or signup) and the user is not authenticated
    if (!isAuthenticated && to.name !== 'signin' && to.name !== 'signup') {
        next({ name: 'signin' }) // Redirect to signin
    } else {
        next() // Proceed to the route
    }
})

export default router
