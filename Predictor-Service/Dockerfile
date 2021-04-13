FROM public.ecr.aws/l3s3g4u9/knod-java:latest
VOLUME /tmp
EXPOSE 8088
ADD target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]