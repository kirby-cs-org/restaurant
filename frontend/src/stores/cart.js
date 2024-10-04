import { defineStore } from 'pinia'
import foodApi from '@/api/foodApi'

export const foodsStore = defineStore('foods', {
    state: () => ({
        foods: [],
        cart: JSON.parse(localStorage.getItem('carts')) || [],
    }),

    actions: {
        async fetchFoods() {
            try {
                const res = await foodApi.getFoods()
                this.foods = res.data
            } catch (error) {
                console.error('Error fetching foods:', error)
            }
        },

        addToCart(food) {
            const existingItem = this.cart.find(
                (item) => item.food.id === food.id
            )
            if (existingItem) {
                // เพิ่มจำนวนถ้าอาหารมีอยู่ในตะกร้าแล้ว
                existingItem.quantity += 1
            } else {
                // ถ้าอาหารไม่อยู่ในตะกร้า ให้เพิ่มรายการใหม่พร้อมจำนวน 1
                this.cart.push({ food, quantity: 1 })
            }
            localStorage.setItem('carts', JSON.stringify(this.cart))
            console.log('Updated cart:', this.cart)
            console.log(localStorage.getItem('carts'))
        },

        removeFromCart(id) {
            console.log('remove ', id)
            this.cart = this.cart.filter((item) => {
                // ถ้าจำนวนมากกว่า 1 ให้ลดจำนวน ถ้าน้อยกว่าหรือเท่ากับ 1 ให้ลบออก
                if (item.food.id === id) {
                    if (item.quantity > 1) {
                        item.quantity -= 1
                        return true // เก็บไว้ในตะกร้า
                    } else {
                        return false // ลบออกจากตะกร้า
                    }
                }
                return true // เก็บไว้ในตะกร้า
            })
            localStorage.setItem('carts', JSON.stringify(this.cart))
            console.log('Updated cart:', localStorage.getItem('carts'))
        },
    },
})
