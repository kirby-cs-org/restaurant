<template>
  <aside
    class="flex flex-col justify-between items-center h-screen w-56 py-4 border-r-2"
  >
    <div class="flex">
      <img src="../assets/food-icon.png" width="42px" />
      <span class="flex items-center font-bold text-lg"
        >&nbsp;SuperDuperPOS</span
      >
    </div>
    <ul class="flex flex-col gap-8">
      <li
        v-for="(item, index) in menuItems"
        :key="index"
        :class="[
          'list',
          selectedItem === index
            ? 'bg-yellow-300 flex justify-center'
            : 'flex-start',
        ]"
        @click="selectItem(index)"
      >
        <router-link
          :to="item.path"
          class="flex items-center w-full gap-4"
          @click.native="selectItem(index)"
        >
          <fa :icon="item.icon" />
          <span>{{ item.label }}</span>
        </router-link>
      </li>
    </ul>
    <div class="mt-32">
      <span class="px-16 py-2 bg-gray-200 rounded-md cursor-pointer"
        >Logout</span
      >
    </div>
  </aside>
</template>

<script setup>
import { ref } from "vue";

const selectedItem = ref(null); // To track the selected item

// Sample menu items with icons and paths
const menuItems = [
  { label: "Menu", icon: "bars", path: "/food" },
  { label: "Order", icon: "book", path: "/order" },
  { label: "Ingredient", icon: "book", path: "/ingredient" },
  { label: "Transaction", icon: "sack-dollar", path: "/transaction" },
  { label: "Receipt", icon: "receipt", path: "/receipt" },
  { label: "Dashboard", icon: "chart-line", path: "/dashboard" },
];

// Function to set the selected item
const selectItem = (index) => {
  selectedItem.value = index;
};
</script>

<style scoped>
.list {
  @apply flex justify-start items-center cursor-pointer px-8 py-2 rounded-md duration-200;
}

.list:hover {
  @apply bg-yellow-300 shadow-md;
}
</style>
