APP_HOME=$(pwd)
LOCAL_PATH=("/Users/lulu/soft/deb/jdk-21_linux-x64_bin.deb" "/Users/lulu/soft/apache-maven-3.3.9"  "/Users/lulu/soft/deb/mysql-apt-config_0.8.34-1_all.deb")
REMOTE_BASE="soft"

echo "app home path at  $APP_HOME"
cd $APP_HOME
source ./source.sh


function upload {
    INNER_PATH=$2
    BASE=$1
    DIR_NAME="${BASE%/*}"
    length=${#DIR_NAME}


    if [ ! -d "$INNER_PATH" ]; then
      result1=${INNER_PATH:$length + 1}
      echo "now copy $INNER_PATH --> $REMOTE_BASE/$result1"
      scp -O -P $SCP_PORT "$INNER_PATH" "$SCP_SERVER:$REMOTE_BASE"
      ## rsync -avz "$PATH" "$SCP_SERVER:$REMOTE_BASE/$result1"
    else
        for FILE in "$INNER_PATH"/*; do
          upload $BASE $FILE
        done
    fi
}


for LOCAL_PATH_ENTRY in "${LOCAL_PATH[@]}"; do
  DIR_NAME="${LOCAL_PATH_ENTRY%/*}"
  length=${#DIR_NAME}
  result1=${LOCAL_PATH_ENTRY:$length + 1}
    if ssh $SCP_SERVER "[ -e $result1 ]"; then
      echo "remote file $result1 already exist!"
  else
            scp -r $LOCAL_PATH_ENTRY "$SCP_SERVER:$result1"
  fi
  ## echo "$SCP_I $LOCAL_PATH_ENTRY  $SCP_SERVER $result1"
done








## scp -O -P $SCP_PORT "$LOCAL_PATH" "$SCP_SERVER:$REMOTE_BASE"