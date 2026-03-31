# 使用Java 17作为基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制构建好的jar文件到容器中
COPY target/ai-volunteer-system-1.0.0.jar app.jar

# 暴露8080端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "app.jar"]