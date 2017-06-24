FROM java:8

MAINTAINER Stephen Harrison <stephen@harrison.org>

ENTRYPOINT ["/usr/bin/java", "-jar", "/home/app.jar"]

ADD target/lib /home/lib
ADD target/devops-example-application.jar /home/app.jar