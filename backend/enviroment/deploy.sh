APP_HOME=$(pwd)

echo "app home path at  $APP_HOME"
cd $APP_HOME
source ./source.sh

SOURCE="$ENV_HOME/$MAIN_MODULE/target"
BIN_DIR="$APP_HOME/bin"

function upload {
  DIR_NAME="$1"
  filename=$(basename "$DIR_NAME")
  filepath="$TARGET/$filename"

  if [[ -n $2 ]]; then
      filepath="$TARGET/$2"
  fi

  scp -r "$DIR_NAME" "$SCP_SERVER:$filepath"
}

# 仅上传可执行 fat-jar，避免把 *.jar.original 或其他历史包传到服务器
MAIN_JAR=$(ls -t "$SOURCE"/*.jar 2>/dev/null | grep -v "\.jar\.original$" | head -n 1)
if [[ -z "$MAIN_JAR" ]]; then
  echo "ERROR: 未找到可部署的主程序jar，请先执行 mvn clean package"
  exit 1
fi

echo "Deploy jar: $MAIN_JAR"

# 安全部署：只清理应用程序相关文件，不影响其他业务数据
ssh "$SCP_SERVER" "rm -rf $TARGET/bin $TARGET/lib $TARGET/main.jar && mkdir -p $TARGET $TARGET/logs"

upload "$BIN_DIR"
ssh "$SCP_SERVER" "chmod -R 755 $TARGET/bin/*"

upload "$MAIN_JAR" main.jar