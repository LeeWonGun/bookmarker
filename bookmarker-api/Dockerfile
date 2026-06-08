FROM eclipse-temurin:21-jdk-jammy

# ADD와 COPY는 모두 외부에서 이미지 내부로 파일(jar, tar 등)을 복사하는 기능이다
# ADD는 tar, tar.gz 같은 리눅스 파일을 자동으로 압축 해제하는 기능까지 포함하고 있다
# COPY는 복사 외의 다른 기능은 포함하지 않는다
ADD target/*.jar app.jar

# 파일의 수정 시간을 현재 시간으로 설정
RUN sh -c 'touch /app.jar'

EXPOSE 8080

# 애플리케이션이 실행할 명령어 설정
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]