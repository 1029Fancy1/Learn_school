<template>
  <div class="qna">
    <!-- 导航栏 -->
    <nav class="navbar">
      <div class="navbar-container">
        <div class="navbar-brand">
          <h1>上海第二工业大学</h1>
          <span class="navbar-subtitle">AI高考志愿填报系统</span>
        </div>
        <div class="navbar-menu">
          <a href="/" class="navbar-item">首页</a>
          <a href="/major-library" class="navbar-item">专业库</a>
          <a href="/qna" class="navbar-item active">智能问答</a>
          <a href="#" class="navbar-item">个人中心</a>
        </div>
      </div>
    </nav>

    <!-- 主要内容 -->
    <main class="qna-main">
      <div class="qna-container">
        <div class="chat-history">
          <div v-for="(message, index) in chatHistory" :key="index" class="message" :class="{ 'user-message': message.type === 'user', 'ai-message': message.type === 'ai' }">
            <div class="message-header">
              <span class="message-sender">{{ message.type === 'user' ? '我' : 'AI' }}</span>
              <span class="message-time">{{ message.time }}</span>
            </div>
            <div v-if="message.type === 'user'" class="message-content">
              <p v-for="(paragraph, pIndex) in message.content.split('\n')" :key="pIndex">{{ paragraph }}</p>
            </div>
            <div v-else class="answer-card">
              <div class="answer-text" v-html="sanitizedAnswer(message.content)"></div>
              <div class="answer-images">
                <img 
                  v-for="(imageUrl, iIndex) in getImageUrlsFromContent(message.content)" 
                  :key="iIndex" 
                  :src="getImageUrl(imageUrl)" 
                  alt="AI生成的图片" 
                  class="answer-image"
                />
                <img v-for="(imageUrl, iIndex) in message.imageUrls" :key="iIndex" :src="imageUrl" alt="AI生成的图片" class="answer-image" />
              </div>
              <div class="feedback-section">
                <button 
                  class="feedback-button useful" 
                  @click="submitFeedback(message, 'useful')"
                >
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="20 6 9 17 4 12"></polyline>
                  </svg>
                  有用
                </button>
                <button 
                  class="feedback-button improve" 
                  @click="submitFeedback(message, 'need_improvement')"
                >
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                  </svg>
                  待优化
                </button>
              </div>
            </div>
          </div>
          <div v-if="loading" class="loading-message">
            <div class="loading-spinner"></div>
            <span>AI正在思考中...</span>
          </div>
        </div>
        <div class="chat-input">
          <textarea
            v-model="inputMessage"
            rows="4"
            placeholder="请输入您的问题，例如：宿舍几人间？"
            @keyup.enter.ctrl="sendMessage"
          ></textarea>
          <div class="input-actions">
            <button class="clear-button" @click="clearHistory">清空历史</button>
            <button class="send-button" @click="sendMessage" :disabled="loading">
              <span v-if="loading">发送中...</span>
              <span v-else>发送</span>
            </button>
          </div>
        </div>
      </div>
      <div class="related-questions">
        <h3>相关问题</h3>
        <div class="related-tags">
          <span v-for="question in relatedQuestions" :key="question" class="related-tag" @click="useRelatedQuestion(question)">{{ question }}</span>
        </div>
      </div>
    </main>

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
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

interface Message {
  type: 'user' | 'ai'
  content: string
  imageUrls: string[]
  time: string
}

export default defineComponent({
  name: 'QnA',
  setup() {
    const router = useRouter()
    const inputMessage = ref('')
    const chatHistory = ref<Message[]>([])
    const loading = ref(false)
    const relatedQuestions = ref([
      '宿舍几人间？',
      '学校有哪些专业？',
      '招生政策是什么？',
      '就业情况如何？',
      '奖学金设置？',
      '校园环境怎么样？'
    ])
    
    const sendMessage = async () => {
      if (!inputMessage.value.trim()) return
      
      // 添加用户消息到历史记录
      const userMessage: Message = {
        type: 'user',
        content: inputMessage.value,
        imageUrls: [],
        time: new Date().toLocaleTimeString()
      }
      chatHistory.value.push(userMessage)
      
      // 清空输入框
      const question = inputMessage.value
      inputMessage.value = ''
      
      // 显示加载状态
      loading.value = true
      
      try {
        // 调用后端API获取AI回答
        const response = await axios.post('http://localhost:8080/api/ask', {
          question: question,
          session_id: 'test-session'
        })
        
        // 添加AI回答到历史记录
        const aiMessage: Message = {
          type: 'ai',
          content: response.data.answer,
          imageUrls: response.data.image_urls || [],
          time: new Date().toLocaleTimeString()
        }
        chatHistory.value.push(aiMessage)
      } catch (error) {
        console.error('获取AI回答失败:', error)
        // 添加错误消息到历史记录
        const errorMessage: Message = {
          type: 'ai',
          content: '抱歉，获取回答失败，请稍后再试。',
          imageUrls: [],
          time: new Date().toLocaleTimeString()
        }
        chatHistory.value.push(errorMessage)
      } finally {
        // 隐藏加载状态
        loading.value = false
      }
    }
    
    const clearHistory = () => {
      chatHistory.value = []
    }
    
    const useRelatedQuestion = (question: string) => {
      inputMessage.value = question
      sendMessage()
    }
    
    // 防XSS处理
    const sanitizedAnswer = (content: string) => {
      // 简单的防XSS处理，实际项目中可使用更专业的库
      return content
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;')
        .replace(/\n/g, '<br>')
    }
    
    // 从答案中提取[图片:xxx]标记
    const getImageUrlsFromContent = (content: string) => {
      const imageRegex = /\[图片:(.*?)\]/g
      const images: string[] = []
      let match
      while ((match = imageRegex.exec(content)) !== null) {
        images.push(match[1])
      }
      return images
    }
    
    // 生成图片的完整URL
    const getImageUrl = (imagePath: string) => {
      // 假设图片存储在服务器的/images目录下
      return `http://localhost:8000/images/${imagePath}`
    }
    
    // 提交反馈
    const submitFeedback = async (message: Message, feedbackType: string) => {
      try {
        await axios.post('http://localhost:8080/api/feedback', {
          message_id: message.time, // 使用时间作为唯一标识
          feedback_type: feedbackType,
          content: message.content
        })
        // 可以添加反馈成功的提示
        console.log('反馈提交成功')
      } catch (error) {
        console.error('反馈提交失败:', error)
      }
    }
    
    return {
      inputMessage,
      chatHistory,
      loading,
      relatedQuestions,
      sendMessage,
      clearHistory,
      useRelatedQuestion,
      sanitizedAnswer,
      getImageUrlsFromContent,
      getImageUrl,
      submitFeedback
    }
  }
})
</script>

<style scoped>
/* 全局样式 */
.qna {
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

/* 主要内容 */
.qna-main {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 40px;
}

.qna-container {
  display: flex;
  flex-direction: column;
}

.chat-history {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 30px;
  height: 600px;
  overflow-y: auto;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.message {
  margin-bottom: 24px;
  max-width: 80%;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.user-message {
  align-self: flex-end;
  margin-left: auto;
}

.user-message .message-content {
  background-color: #1A3A6C;
  color: white;
  border-radius: 18px 18px 4px 18px;
  padding: 16px 20px;
  box-shadow: 0 2px 8px rgba(26, 58, 108, 0.15);
}

.ai-message {
  align-self: flex-start;
}

.ai-message .message-content {
  background-color: #ffffff;
  border-radius: 4px 18px 18px 18px;
  padding: 16px 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  font-size: 12px;
  color: #999;
}

.message-content {
  line-height: 1.6;
}

.answer-card {
  background-color: #ffffff;
  border-radius: 4px 18px 18px 18px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  position: relative;
}

.answer-text {
  white-space: pre-wrap;
  line-height: 1.6;
  color: #333;
  margin-bottom: 20px;
}

.answer-images {
  margin-bottom: 20px;
}

.answer-image {
  max-width: 100%;
  border-radius: 8px;
  margin: 12px 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.answer-image:hover {
  transform: scale(1.02);
}

.feedback-section {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.feedback-button {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  background-color: #ffffff;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.feedback-button:hover {
  border-color: #1A3A6C;
  color: #1A3A6C;
}

.feedback-button.useful:hover {
  background-color: rgba(26, 58, 108, 0.05);
}

.feedback-button.improve:hover {
  background-color: rgba(255, 159, 64, 0.05);
  border-color: #ff9f40;
  color: #ff9f40;
}

.loading-message {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  color: #666;
  gap: 10px;
}

.loading-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid #f3f3f3;
  border-top: 2px solid #1A3A6C;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.chat-input {
  background-color: #ffffff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.chat-input textarea {
  width: 100%;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 12px;
  font-size: 14px;
  resize: none;
  font-family: inherit;
  transition: border-color 0.3s ease;
}

.chat-input textarea:focus {
  outline: none;
  border-color: #1A3A6C;
  box-shadow: 0 0 0 2px rgba(26, 58, 108, 0.1);
}

.input-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 16px;
}

.clear-button {
  padding: 10px 20px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background-color: #ffffff;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #666;
}

.clear-button:hover {
  border-color: #1A3A6C;
  color: #1A3A6C;
}

.send-button {
  padding: 10px 24px;
  border: none;
  border-radius: 4px;
  background-color: #1A3A6C;
  color: white;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.send-button:hover:not(:disabled) {
  background-color: #0D2A52;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(26, 58, 108, 0.2);
}

.send-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

/* 相关问题 */
.related-questions {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  height: fit-content;
  position: sticky;
  top: 90px;
}

.related-questions h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #333;
}

.related-tags {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.related-tag {
  padding: 10px 16px;
  background-color: #ffffff;
  border-radius: 20px;
  font-size: 14px;
  color: #666;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.related-tag:hover {
  background-color: #1A3A6C;
  color: white;
  transform: translateX(4px);
}

/* 页脚 */
.footer {
  background-color: #f9f9f9;
  padding: 60px 20px 20px;
  border-top: 1px solid #eaeaea;
  margin-top: 60px;
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
  
  .qna-main {
    grid-template-columns: 1fr;
    gap: 20px;
    margin: 20px auto;
  }
  
  .related-questions {
    position: static;
  }
  
  .chat-history {
    height: 400px;
    padding: 20px;
  }
  
  .message {
    max-width: 90%;
  }
  
  .input-actions {
    flex-direction: column;
  }
  
  .input-actions button {
    width: 100%;
  }
  
  .footer-links {
    flex-wrap: wrap;
    justify-content: center;
  }
}
</style>