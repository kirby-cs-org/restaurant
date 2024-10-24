<template>
    <div>
        <canvas id="myChart"></canvas>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { Chart, registerables } from 'chart.js'
import financialApi from '@/api/fanancialApi';



Chart.register(...registerables)

export default {
    name: 'DailySalesChart',
    setup() {
        const chart = ref(null)

        onMounted(() => {
            const ctx = document.getElementById('myChart').getContext('2d')

            const chart = ref(null);
            const financialData = ref([]); // Added: Ref for the financial data

            const fetchFinancialData = async () => {
                try {
                    const response = await financialApi.getFinancials(); // Added: Call API
                    financialData.value = response.data; // Added: Assuming API returns data as an array of objects
                } catch (error) {
                    console.error('Error fetching financial data:', error);
                }
            };

            const dates = financialData.value.map(item => item.date); 
            const prices = financialData.value.map(item => item.income); 

            chart.value = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [
                        {
                            fill: 'origin',
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            data: prices,
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
            })
        })

        return {
            chart,
        }
    },
}
</script>

<style scoped>
canvas {
    max-width: 500px;
    height: 300px;
    margin: auto;
}
</style>
