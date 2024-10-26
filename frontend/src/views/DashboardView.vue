<script setup>
import Sidebar from '@/components/Sidebar.vue'
import DailySalesChart from '@/components/DailySalesChart.vue'
import TotalIncomeChart from '@/components/TotalIncomeChart.vue'
</script>

<template>
    <div class="flex">
        <aside class="fixed h-full">
            <Sidebar />
        </aside>
        <main
            class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 min-h-screen"
        >
            <!-- Name section  -->
            <section class="w-full h-12">
                <span class="font-bold text-5xl">Dashboard</span>
            </section>
            <div class="flex flex-row gap-4">
                <div
                    class="m-auto p-6 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD] flex-1 h-[400px]"
                >
                    <DailySalesChart />
                </div>
                <div
                    class="m-auto p-6 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD] flex-1 h-[400px]"
                >
                    <TotalIncomeChart />
                </div>
            </div>
        </main>
    </div>
</template>
