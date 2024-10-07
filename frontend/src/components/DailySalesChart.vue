<template>
  <div>
    <canvas id="myChart"></canvas>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default {
  name: 'DailySalesChart',
  setup() {
    const chart = ref(null);

    onMounted(() => {
      const ctx = document.getElementById('myChart').getContext('2d');

      chart.value = new Chart(ctx, {
        type: 'line',
        data: {
          labels: ['09:00 am', '', ''
          , '12:00 pm', '', ''
          , '03:00 pm', '', ''
          , '06:00 pm'],
          datasets: [
            {
              fill: 'origin',
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255, 99, 132, 1)',
              data: [10, 50, 50, 130, 120, 200, 80, 95, 70, 20],
              tension: 0.5,
              pointRadius: 5,
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
              text: 'Daily Sales',
              font: {
                family: 'Gilroy Bold',
                size: 30,
              },
            },
          },
          scales: {
            x: {
              grid: {
                display: false,
              },
              title: {
                display: false,
                font: {
                  family: 'Gilroy Bold',
                  size: 16,
                },
              },
              ticks: {
                font: {
                  family: 'Gilroy Bold',
                },
              },
            },
            y: {
              beginAtZero: true,
              grid: {
                display: true,
              },
              title: {
                display: false,
                font: {
                  family: 'Gilroy Bold',
                  size: 16,
                },
              },
              ticks: {
                font: {
                  family: 'Gilroy Bold',
                  size: 14,
                },
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
