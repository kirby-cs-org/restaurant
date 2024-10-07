<template>
    <div>
      <canvas id="totalIncomeChart"></canvas> <!-- Use a unique ID -->
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { Chart, registerables } from 'chart.js';
  
  Chart.register(...registerables);
  
  export default {
    name: 'TotalIncomeChart',
    setup() {
      const chart = ref(null);
  
      onMounted(() => {
        const ctx = document.getElementById('totalIncomeChart').getContext('2d');
  
        chart.value = new Chart(ctx, {
          type: 'doughnut',
          data: {
            labels: ['09:00 am', '', ''
          , '12:00 pm', '', ''
          , '03:00 pm', '', ''
          , '06:00 pm'],
            datasets: [
              {
                label: 'Total Income',
                backgroundColor: [
                'rgba(255,99,132,255)',
                'rgba(182, 224, 29)',
                'rgba(255,203,69,255)',
                'rgba(54,72,86,255)'],
                data: [325, 780, 522, 478],
                offset: 10
              },
            ],
          },
          options: {
            responsive: true,
            plugins: {
              legend: {
                display: false,
              },
              title: {
                display: true,
                text: 'Total Income',
                font: {
                  family: 'Gilroy Bold',
                  size: 30,
                },
              },
            },
          },
        });
      });
  
      return {
        chart,
      };
    },
  };
  </script>
  
  <style scoped>
  canvas {
    max-width: 500px;
    height: 300px;
    margin: auto;
  }
  </style>
  