<script setup>
import Sidebar from '@/components/Sidebar.vue';
import receiptApi from '@/api/receiptApi';
import router from '@/router'

// สร้าง reactive state
import { ref, onMounted } from 'vue';

const b_total = ref('');
const b_id = ref('');
const created_at = ref('');
const username = ref('');
const formattedDate = ref('');

const goBack = () => {
    router.push('/food')
}

onMounted(async () => {
  try {
    const receiptId = "123456789"; // รับค่า ID จาก params หรือ state
    const response = await receiptApi.getReceiptById(receiptId); // เรียก API

    // บันทึกข้อมูลที่ดึงมาใน state
    b_total.value = response.data.total;
    b_id.value = response.data.id;
    created_at.value = new Date(response.data.created_at).toLocaleString("en-US", {
      weekday: "short",
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      second: "numeric",
    });
    username.value = response.data.username;
  } catch (error) {
    console.error("Error fetching receipt:", error);
  }
});
</script>

<template>
  <div class="flex">
    <aside class="fixed">
      <Sidebar />
    </aside>

    <main class="ml-[14rem] w-full h-screen py-4 px-8 flex flex-col bg-[#FDFDFD]">
      <!-- Back button -->
      <div class="flex bg-[#C7C7C7FF] text-white rounded-lg px-3 py-2 w-10 mb-4" @click="goBack">
        <button>
          <fa icon="arrow-left" />
        </button>
      </div>
      
      <div class="flex justify-center h-5/6 relative ">
        <!-- Receipt -->
        <div class="w-4/6 p-10 rounded-lg shadow-md border border-gray-300 bg-[#FDFDFD]">
        <h1 class="text-center text-3xl mb-4">SuperDuper Restaurant</h1>
        <p class="text-center">{{ formattedDate }}</p>

        <hr class="my-2 border-1 border-dashed border-gray-400 " />

        <!-- Items -->
        <div class="flex justify-between mb-2">
          <p>Tam thai</p>
          <p>$15.10</p>
        </div>
        <div class="flex justify-between mb-2">
          <p>Fried Rice with pork</p>
          <p>$17</p>
        </div>
        <div class="flex justify-between mb-2">
          <p>Pad Kaprao</p>
          <p>$23.25</p>
        </div>
        <div class="flex-col justify-between content-bottom bottom-10">
          <hr class="my-2 border-1 border-dashed border-gray-400" />

        <!-- Subtotal -->
        <div class="flex justify-between mb-2">
          <p>Subtotal:</p>
          <p>$55.35</p>
        </div>

        <!-- Ref Number -->
        <div class="flex justify-between mb-2">
          <p>Ref. Number:</p>
          <p>$0.57</p>
        </div>

        <!-- Total -->
        <div class="flex mb-2">
          <p>Total:</p>
          <p>{{ b_total }}</p>
        </div>
        <hr class="my-2 border-1 border-dashed border-gray-400" />

        <!-- Reference -->
        <div class="flex justify-between mb-2">
          <p>Ref. ID</p>
          <p>{{ b_id }}</p>
        </div>

        <!-- Payment Time -->
        <div class="flex justify-between mb-2">
          <p>Payment Time</p>
          <p>{{ created_at }}</p>
        </div>

        <!-- Customer Name -->
        <div class="flex justify-between mb-2">
          <p>Customer Name</p>
          <p>{{ username }}</p>
        </div>

        <p class="text-center mt-5 ">Thank you for your purchase. Enjoy your meal! :D</p>
        </div>
      </div>
      </div>
      
    </main>
  </div>
</template>
