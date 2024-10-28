# JDK 이미지를 통한 executable jar 빌드
FROM openjdk:17-jdk-alpine AS build
WORKDIR /app

# Gradle Wrapper 및 프로젝트 설정 파일 복사
COPY gradlew . 
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Gradle 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 캐싱을 위해 먼저 dependencies 빌드
RUN ./gradlew dependencies --no-daemon

# 전체 소스 복사 및 빌드 실행 (테스트 제외)
COPY . .
RUN ./gradlew bootJar --no-daemon

# JRE 이미지를 통한 최종 실행 단계
FROM openjdk:17-jre-alpine
WORKDIR /app

# 빌드한 jar 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 실행 포트 정의
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
