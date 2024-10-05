<script setup>
import Sidebar from '@/components/Sidebar.vue'
import Search from '@/components/Search.vue'
</script>

<script>
import { ref } from 'vue'
const dropdownVisible = ref(false)
const options = ref([
    'Pending',
    'Preparing',
    'In delivery',
    'Delivered',
    'Cancelled',
    'All Order',
])
const selectedOption = ref('')

const toggleDropdown = () => {
    dropdownVisible.value = !dropdownVisible.value
}

const selectOption = (option) => {
    selectedOption.value = option
    dropdownVisible.value = false
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
            <!-- search filter section  -->
            <section class="search">
                <Search @update-search="searchQuery = $event" />
            </section>

            <!-- Name section -->
            <section class="w-full">
                <span class="font-bold text-3xl">Order</span>
            </section>

            <!-- Type selection dropdown on a new line -->
            <div class="relative inline-block mt-2">
                <button
                    @click="toggleDropdown"
                    class="flex px-2 cursor-pointer gap-2 text-gray-500"
                >
                    {{ selectedOption ? selectedOption : 'All Orders' }}
                    <fa icon="sort-down" />
                </button>
                <ul
                    v-if="dropdownVisible"
                    class="absolute bg-gray-80 rounded-lg shadow-lg text-gray-500"
                >
                    <li
                        v-for="option in options"
                        :key="option"
                        @click="selectOption(option)"
                        class="px-4 py-2 hover:bg-gray-100 cursor-pointer rounded-lg"
                    >
                        {{ option }}
                    </li>
                </ul>
            </div>
        </main>
    </div>
</template>
