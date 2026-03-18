JAVA_PID=`ps -ef|grep java |grep -v grep | awk -F ' ' '{print $2}'`
kill $JAVA_PID
echo "now stop java pid: $JAVA_PID ... and wait 5 sec..."
sleep 5
JAVA_PID=`ps -ef|grep java |grep -v grep | awk -F ' ' '{print $2}'`
echo "check:$JAVA_PID"
echo "now start java..."
cd "$(dirname "$0")"
./startup.sh