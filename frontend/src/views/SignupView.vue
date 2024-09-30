<template>
  <main class="flex w-full h-screen bg-[url('../assets/food.png')]">
    <div
      class="flex justify-center items-center w-full mx-auto bg-white max-w-[90%] lg:max-w-[55%] md:max-w-[75%] h-full fixed right-0"
    >
      <div class="flex flex-col gap-8 p-8">
        <span class="font-extrabold text-3xl">Get Started</span>
        <div>
          <span class="text-lg">Already have an account? </span>
          <a class="text-blue-500" href="/signin">Login</a>
        </div>
        <input
          v-model="username"
          type="text"
          placeholder="Username"
          class="input"
        />
        <input
          v-model="password"
          type="password"
          placeholder="Password"
          class="input"
        />
        <input
          v-model="confirmPassword"
          type="password"
          placeholder="Confirm Password"
          class="input"
        />
        <input
          v-model="phoneNumber"
          type="text"
          placeholder="Phone Number"
          class="input"
        />
        <button
          @click="register"
          class="bg-[#ffca40] rounded-lg h-[4rem] font-bold text-xl"
        >
          Register
        </button>
        <div v-if="errorMessage" class="text-red-500">{{ errorMessage }}</div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";

const username = ref("");
const password = ref("");
const confirmPassword = ref("");
const phoneNumber = ref("");
const errorMessage = ref(""); // State to hold error messages

const register = async () => {
  // Reset the error message
  errorMessage.value = "";

  // Check if passwords match
  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Passwords do not match.";
    return; // Stop execution if passwords do not match
  }

  try {
    const response = await axios.post("http://localhost:8088/auth/signup", {
      username: username.value,
      password: password.value,
      confirmPassword: confirmPassword.value,
      phone: phoneNumber.value,
    });

    // Handle successful registration (e.g., redirect, show a message)
    console.log("Registration successful:", response.data);
  } catch (error) {
    // Handle registration error (e.g., show error message)
    console.error("Registration failed:", error.response.data);
    errorMessage.value = "Registration failed. Please try again.";
  }
};
</script>
