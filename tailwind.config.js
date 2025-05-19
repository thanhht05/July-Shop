// tailwind.config.js
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html',   // Thymeleaf
    './src/main/webapp/**/*.html',                // nếu còn view ở webapp
    './src/**/*.java',                            // dùng @Controller trả về fragment tên class
  ],
  theme: { extend: {} },
  plugins: [],
};
