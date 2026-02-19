# Job Scheduling projekts

Optimizācijas tīmekļa serviss darbu plānošanai, izmantojot Spring Boot un Timefold.

## Prasības

Java 17+
Maven

## Atkarību lejupielāde

mvn clean install

## Palaišana

mvn spring-boot:run

Vai palaist klasē:
SchedulerApplication.java

##Projekta palaišana, izmantojot Docker:

docker pull skdev00/job-sheduling-timefold:1.1

docker run -p 8080:8080 skdev00/job-sheduling-timefold:1.1

## Sistēmas tīmekļa saskarne, pieejama lokāli:

http://localhost:8080/piemeri.html
