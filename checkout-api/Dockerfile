FROM openjdk:17.0.2-oraclelinux7

ADD ./build/libs/checkout-api-*.jar /usr/app/checkout-api.jar

WORKDIR /usr/app

EXPOSE 8080

ENTRYPOINT ["java", "-Dfeign.client.config.products.url=${PRODUCTS_API_URL}", "-Dfeign.client.config.promotions.url=${PROMOTIONS_API_URL}", "-jar", "checkout-api.jar"]