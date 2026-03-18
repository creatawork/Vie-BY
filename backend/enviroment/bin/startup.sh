#!/bin/bash

# 获取脚本所在目录
BIN_DIR=$(cd "$(dirname "$0")" && pwd)
# 应用根目录（上一级）
BASE_DIR=$(dirname "$BIN_DIR")

# JVM 配置
SERVICE_OPTS="${SERVICE_OPTS} -server"
SERVICE_OPTS="${SERVICE_OPTS} -Xms512m -Xmx1024m"
SERVICE_OPTS="${SERVICE_OPTS} -XX:+UseG1GC"
SERVICE_OPTS="${SERVICE_OPTS} -XX:MaxGCPauseMillis=200"
SERVICE_OPTS="${SERVICE_OPTS} -XX:+UseGCOverheadLimit"

# 生产环境调试端口（可选）
# SERVICE_OPTS="${SERVICE_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:65005"

SERVICE_OPTS="${SERVICE_OPTS} -jar $BASE_DIR/main.jar"

# 激活生产环境配置
SERVICE_OPTS="${SERVICE_OPTS} --spring.profiles.active=prod"

# 确保日志目录存在
mkdir -p "$BASE_DIR/logs"

# 启动应用
echo "启动 VIE 应用..."
echo "使用 Spring Profile: prod"
echo "日志目录: $BASE_DIR/logs"

cd "$BASE_DIR" && nohup java $SERVICE_OPTS > "$BASE_DIR/logs/stdout.log" 2>&1 &
echo $! > "$BASE_DIR/java-app.pid"

echo "VIE 应用已在后台启动。"
echo "进程ID已保存到: $BASE_DIR/java-app.pid"
echo "日志文件: $BASE_DIR/logs/stdout.log"
