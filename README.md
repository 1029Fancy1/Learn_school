
# 上海第二工业大学AI高考志愿填报系统

## 项目简介

上海第二工业大学AI高考志愿填报系统是一个基于AI技术的智能志愿填报指导系统，旨在为考生提供个性化的高考志愿填报指导服务。系统通过AI技术解答考生关于学校、专业、招生政策等方面的问题，帮助考生做出更加明智的志愿填报决策。

## 技术栈

### 前端
- Vue3 + TypeScript + Pinia + Axios + Element Plus
- 响应式设计，支持多端访问

### 后端
- SpringBoot 3.2 + JDK17 + MyBatis-Plus + Redis
- 成熟稳定，与学校现有系统易集成

### AI服务
- Python 3.10 + LangChain 0.2 + FastAPI + ChromaDB
- RAG流程标准化，向量检索高效

### 嵌入模型
- BAAI/bge-small-zh-v1.5（本地HuggingFace）
- 避免依赖DeepSeek嵌入API，降低成本+提升响应速度

### LLM调用
- DeepSeek-V2/V3（通过LangChain封装）
- 自备密钥，仅用于生成答案，非嵌入计算

### 部署
- Docker + Docker Compose（开发）/ K8s（生产）
- 环境隔离，便于教务处IT团队运维

## 系统功能

### 1. 智能问答
- 通过AI技术解答考生关于学校、专业、招生政策等方面的问题
- 支持多轮对话，提供个性化的回答
- 富文本答案渲染，支持文本、图片等多种形式的答案展示

### 2. 专业库
- 浏览学校所有专业信息，了解专业课程、就业前景等详细内容
- 支持按专业名称、类别等条件查询专业信息
- 提供专业详情页，展示专业介绍、课程设置、就业前景等信息

### 3. 志愿填报指导
- 基于考生分数和兴趣，推荐适合的专业和志愿填报方案
- 提供专业就业情况分析，帮助考生做出更加明智的决策

## 本地部署方式

### 1. 环境准备
- 安装Docker和Docker Compose
- 安装Git
- 安装JDK 17（用于后端开发）
- 安装Node.js 16+（用于前端开发）
- 安装Python 3.10+（用于AI服务开发）

### 2. 克隆项目
```bash
git clone https://github.com/your-username/sspu-ai-volunteer-system.git
cd sspu-ai-volunteer-system
```

### 3. 配置环境变量
- 在`ai-service/.env`文件中设置DeepSeek API密钥：
  ```
  DEEPSEEK_API_KEY=your_deepseek_api_key_here
  ```

### 4. 构建和运行
```bash
# 构建前端
cd frontend
npm install
npm run build
cd ..

# 构建后端
cd backend
mvn clean package
cd ..

# 启动所有服务
docker compose up -d
```

### 5. 访问系统
- 打开浏览器，访问 http://localhost

## 云端服务器部署方式

### 1. 环境准备
- 选择云服务器提供商（如阿里云、腾讯云、AWS等）
- 选择合适的服务器配置（建议至少2核4G内存）
- 安装Docker和Docker Compose

### 2. 部署步骤
1. **连接服务器**：使用SSH连接到云服务器
2. **安装依赖**：安装Git、JDK 17、Node.js 16+、Python 3.10+
3. **克隆项目**：同本地部署步骤
4. **配置环境变量**：同本地部署步骤
5. **构建和运行**：同本地部署步骤
6. **配置域名**：（可选）设置域名解析到服务器IP
7. **配置HTTPS**：（可选）使用Let's Encrypt等工具配置HTTPS

### 3. 部署架构
- **前端**：Nginx托管，静态文件部署
- **后端**：SpringBoot应用，集群部署
- **AI服务**：Python应用，独立部署
- **Redis**：缓存服务，高可用部署

### 4. 监控和维护
- 配置服务器监控，监控CPU、内存、磁盘等指标
- 配置应用监控，监控各服务的运行状态和响应时间
- 配置日志管理，集中管理系统日志，便于问题排查
- 定期备份数据，确保数据安全

## 注意事项

1. **DeepSeek API密钥**：需要在`ai-service/.env`文件中设置有效的DeepSeek API密钥
2. **文档初始化**：AI服务启动时会自动加载`ai-service/documents`目录下的文档，确保该目录包含学校相关文档
3. **性能优化**：对于生产环境，建议配置Redis集群、使用负载均衡等方式优化性能
4. **安全措施**：配置防火墙、使用HTTPS、定期更新系统等安全措施
5. **数据备份**：定期备份数据库和向量库数据，确保数据安全
