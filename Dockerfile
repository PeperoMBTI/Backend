# JDK 이미지를 통한 executable jar 빌드 및 실행
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Gradle Wrapper 및 프로젝트 설정 파일 복사
COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/

# Gradle Wrapper에 실행 권한 부여
RUN chmod +x /app/gradlew

# 의존성 캐싱을 위해 dependencies만 설치
RUN /app/gradlew dependencies --no-daemon

# 전체 소스 복사
COPY . /app

# 빌드 실행 (테스트 제외)
RUN /app/gradlew bootJar --no-daemon

# 빌드된 jar 파일을 이미지에 추가
COPY build/libs/*.jar app.jar

# 실행 포트 정의
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
