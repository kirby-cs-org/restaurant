<script setup>
import Sidebar from '@/components/Sidebar.vue'
</script>

<template>
    <div Class="flex">
        <aside class="fixed">
            <Sidebar />
        </aside>
        <main class="ml-[14rem] w-full py-4 px-8 flex flex-col gap-4 bg-gray-50 h-screen items-center">
            <h><b>Payment Successful</b></h>
            <div>
                <img src="../assets/success_icon.png" alt="success icon">
            </div>

        </main>
    </div>

</template>
<style>
    
</style>
