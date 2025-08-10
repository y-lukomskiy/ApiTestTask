FROM eclipse-temurin:21-jdk

RUN apt-get update && \
    apt-get install -y maven curl && \
    rm -rf /var/lib/apt/lists/*

RUN curl -o allure-2.24.1.tgz -OLs https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.24.1/allure-commandline-2.24.1.tgz && \
    tar -zxvf allure-2.24.1.tgz -C /opt/ && \
    ln -s /opt/allure-2.24.1/bin/allure /usr/bin/allure && \
    rm allure-2.24.1.tgz

WORKDIR /app

COPY pom.xml .
COPY .mvn/ .mvn/

RUN mvn dependency:go-offline

COPY src/ src/

CMD ["mvn", "clean", "test"]