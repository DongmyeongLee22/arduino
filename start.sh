nohup java -jar -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/real-application.yml arduino-0.0.1-SNAPSHOT.jar > /nonhup.out 2>&1 &