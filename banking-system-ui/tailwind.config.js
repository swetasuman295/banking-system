/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'rabobank-orange': '#FF6200',
        'rabobank-blue': '#002D5C',
      }
    },
  },
  plugins: [],
}