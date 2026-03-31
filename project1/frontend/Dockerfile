# 使用Nginx作为前端容器
FROM nginx:alpine

# 复制前端构建文件到Nginx的静态文件目录
COPY dist /usr/share/nginx/html

# 复制Nginx配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露80端口
EXPOSE 80

# 启动Nginx
CMD ["nginx", "-g", "daemon off;"]