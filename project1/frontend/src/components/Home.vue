<template>
  <div class="home">
    <!-- 导航栏 -->
    <nav class="navbar">
      <div class="navbar-container">
        <div class="navbar-brand">
          <h1>上海第二工业大学</h1>
          <span class="navbar-subtitle">AI高考志愿填报系统</span>
        </div>
        <div class="navbar-menu">
          <a href="/" class="navbar-item active">首页</a>
          <a href="/major-library" class="navbar-item">专业库</a>
          <a href="/qna" class="navbar-item">智能问答</a>
          <a href="#" class="navbar-item">个人中心</a>
        </div>
      </div>
    </nav>

    <!-- Hero区域 -->
    <section class="hero-section">
      <div class="hero-container">
        <div class="hero-content">
          <h2>选择二工大，成就应用型卓越工程师</h2>
          <p>基于AI技术，为您提供个性化的高考志愿填报指导服务</p>
          <div class="hero-actions">
            <span class="year-tag">{{ new Date().getFullYear() }}年招生季</span>
            <button class="hero-button" @click="goToQnA">开始咨询</button>
          </div>
        </div>
        <div class="logo-watermark"></div>
      </div>
    </section>

    <!-- 核心功能 -->
    <section class="features-section">
      <div class="features-container">
        <h3>核心功能</h3>
        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"></circle>
                <path d="M8 14s1.5 2 4 2 4-2 4-2"></path>
                <line x1="9" y1="9" x2="9.01" y2="9"></line>
                <line x1="15" y1="9" x2="15.01" y2="9"></line>
              </svg>
            </div>
            <h4>智能问答</h4>
            <p>通过AI技术解答您关于学校、专业、招生政策等方面的问题</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
                <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
              </svg>
            </div>
            <h4>专业库</h4>
            <p>浏览学校所有专业信息，了解专业课程、就业前景等详细内容</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"></polyline>
              </svg>
            </div>
            <h4>志愿填报指导</h4>
            <p>基于您的分数和兴趣，推荐适合的专业和志愿填报方案</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 常见问题 -->
    <section class="faq-section">
      <div class="faq-container">
        <h3>常见问题</h3>
        <div class="faq-list">
          <div class="faq-item">
            <div class="faq-question">如何使用智能问答功能？</div>
            <div class="faq-answer">
              <p>在智能问答页面，您可以输入关于学校、专业、招生政策等方面的问题，系统会通过AI技术为您生成答案。</p>
            </div>
          </div>
          <div class="faq-item">
            <div class="faq-question">专业库包含哪些信息？</div>
            <div class="faq-answer">
              <p>专业库包含学校所有专业的详细信息，包括专业介绍、课程设置、就业前景等内容。</p>
            </div>
          </div>
          <div class="faq-item">
            <div class="faq-question">如何获取志愿填报指导？</div>
            <div class="faq-answer">
              <p>在智能问答页面，您可以输入您的分数和兴趣，系统会基于这些信息为您推荐适合的专业和志愿填报方案。</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="footer-container">
        <div class="footer-content">
          <p>© {{ new Date().getFullYear() }} 上海第二工业大学 AI高考志愿填报系统</p>
          <div class="footer-links">
            <a href="#">关于我们</a>
            <a href="#">联系方式</a>
            <a href="#">隐私政策</a>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  name: 'Home',
  setup() {
    const router = useRouter()
    
    const goToQnA = () => {
      // 跳转到智能问答页面
      router.push('/qna')
    }
    
    return {
      goToQnA
    }
  }
})
</script>

<style scoped>
/* 全局样式 */
.home {
  width: 100%;
  min-height: 100vh;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

/* 导航栏 */
.navbar {
  background-color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 70px;
}

.navbar-brand {
  display: flex;
  flex-direction: column;
}

.navbar-brand h1 {
  font-size: 20px;
  font-weight: 600;
  color: #1A3A6C;
  margin: 0;
}

.navbar-subtitle {
  font-size: 12px;
  color: #666;
  margin-top: 2px;
}

.navbar-menu {
  display: flex;
  gap: 30px;
}

.navbar-item {
  text-decoration: none;
  color: #333;
  font-size: 14px;
  font-weight: 500;
  transition: color 0.3s ease;
  position: relative;
}

.navbar-item:hover {
  color: #1A3A6C;
}

.navbar-item.active {
  color: #1A3A6C;
}

.navbar-item.active::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #1A3A6C;
}

/* Hero区域 */
.hero-section {
  text-align: center;
  padding: 120px 20px;
  background: linear-gradient(135deg, #1A3A6C, #0D2A52);
  position: relative;
  overflow: hidden;
  color: white;
}

.hero-container {
  max-width: 1200px;
  margin: 0 auto;
  position: relative;
  z-index: 2;
}

.hero-content h2 {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 24px;
  line-height: 1.2;
}

.hero-content p {
  font-size: 20px;
  margin-bottom: 40px;
  color: rgba(255, 255, 255, 0.9);
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.hero-actions {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.year-tag {
  background-color: rgba(255, 255, 255, 0.2);
  padding: 10px 20px;
  border-radius: 25px;
  font-size: 16px;
  font-weight: 500;
}

.hero-button {
  background-color: #ffffff;
  color: #1A3A6C;
  border: none;
  padding: 12px 32px;
  border-radius: 4px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.hero-button:hover {
  background-color: #f0f0f0;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
}

.logo-watermark {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  height: 400px;
  background-image: url('https://placehold.co/400x400?text=上海第二工业大学');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  opacity: 0.1;
  z-index: 1;
}

/* 核心功能 */
.features-section {
  padding: 80px 20px;
  background-color: #f9f9f9;
}

.features-container {
  max-width: 1200px;
  margin: 0 auto;
}

.features-section h3 {
  font-size: 32px;
  font-weight: 600;
  margin-bottom: 60px;
  text-align: center;
  color: #333;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 40px;
}

.feature-card {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  text-align: center;
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.feature-icon {
  width: 80px;
  height: 80px;
  background-color: rgba(26, 58, 108, 0.1);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 24px;
  color: #1A3A6C;
}

.feature-card h4 {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 16px;
  color: #333;
}

.feature-card p {
  font-size: 14px;
  color: #666;
  line-height: 1.5;
}

/* 常见问题 */
.faq-section {
  padding: 80px 20px;
  background-color: #ffffff;
}

.faq-container {
  max-width: 1000px;
  margin: 0 auto;
}

.faq-section h3 {
  font-size: 32px;
  font-weight: 600;
  margin-bottom: 60px;
  text-align: center;
  color: #333;
}

.faq-list {
  border-top: 1px solid #eaeaea;
}

.faq-item {
  border-bottom: 1px solid #eaeaea;
}

.faq-question {
  font-size: 16px;
  font-weight: 500;
  padding: 20px 0;
  cursor: pointer;
  position: relative;
  color: #333;
  transition: color 0.3s ease;
}

.faq-question:hover {
  color: #1A3A6C;
}

.faq-question::after {
  content: '+';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  font-size: 20px;
  font-weight: 300;
  transition: transform 0.3s ease;
}

.faq-item.active .faq-question::after {
  transform: translateY(-50%) rotate(45deg);
}

.faq-answer {
  padding: 0 0 20px;
  font-size: 14px;
  color: #666;
  line-height: 1.5;
  display: none;
}

.faq-item.active .faq-answer {
  display: block;
}

/* 页脚 */
.footer {
  background-color: #f9f9f9;
  padding: 60px 20px 20px;
  border-top: 1px solid #eaeaea;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
}

.footer-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.footer-content p {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.footer-links {
  display: flex;
  gap: 30px;
}

.footer-links a {
  text-decoration: none;
  color: #666;
  font-size: 14px;
  transition: color 0.3s ease;
}

.footer-links a:hover {
  color: #1A3A6C;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .navbar-container {
    flex-direction: column;
    height: auto;
    padding: 15px 20px;
    gap: 10px;
  }
  
  .navbar-menu {
    gap: 20px;
  }
  
  .hero-section {
    padding: 80px 20px;
  }
  
  .hero-content h2 {
    font-size: 32px;
  }
  
  .hero-content p {
    font-size: 16px;
  }
  
  .hero-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .logo-watermark {
    width: 250px;
    height: 250px;
  }
  
  .features-section {
    padding: 60px 20px;
  }
  
  .features-section h3 {
    font-size: 28px;
    margin-bottom: 40px;
  }
  
  .feature-card {
    padding: 30px;
  }
  
  .faq-section {
    padding: 60px 20px;
  }
  
  .faq-section h3 {
    font-size: 28px;
    margin-bottom: 40px;
  }
  
  .footer-links {
    flex-wrap: wrap;
    justify-content: center;
  }
}
</style>