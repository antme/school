export PATH=/usr/local/maven/bin/:/usr/java/jdk1.8.0_151/:$PATH
#sudo su
cd /root/workspace/school
sudo git pull
mvn clean package -DskipTests

cp /root/workspace/school/target/school/WEB-INF/classes/config.server_1.properties /root/workspace/school/target/school/WEB-INF/classes/config.properties

sh /usr/local/tomcat/bin/shutdown.sh
NAME=tomcat
echo $NAME
ID=`ps -ef | grep "$NAME" | grep -v "$0" | grep -v "grep" | awk '{print $2}'`
echo $ID
echo "---------------"
for id in $ID
do
kill -9 $id
echo "killed $id"
done
echo "---------------"
rm -rf /usr/local/tomcat/webapps/school/pages/*
rm -rf /usr/local/tomcat/webapps/school/WEB-INF/*
cp -r /root/workspace/school/target/school/* /usr/local/tomcat/webapps/school/
cp -r /root/workspace/BaiHua/BaiHua/* /usr/local/tomcat/webapps/school/


sh /usr/local/tomcat/bin/startup.sh