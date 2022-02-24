FROM openjdk:17.0.2-oraclelinux7

ADD ./build/libs/promotions-api-*.jar /usr/app/promotions-api.jar

WORKDIR /usr/app

EXPOSE 8082

ENTRYPOINT ["java", "-Dspring.data.mongodb.host=${DATABASE_HOST}", "-jar", "promotions-api.jar"]