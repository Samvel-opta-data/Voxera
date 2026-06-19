@echo off
REM Startet Voxera lokal mit H2 (In-Memory) + Seed-Usern (Samvel/Dustin/Vladyslav),
REM damit man sich ohne MySQL als hinterlegte User einloggen kann.
REM WICHTIG: Aendert NICHT application.properties -> der IDE-Start nutzt weiterhin MySQL.
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot
cd /d C:\Users\zarro\voxera\main3
call mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:h2:mem:voxeradb;DB_CLOSE_DELAY=-1 --spring.datasource.driver-class-name=org.h2.Driver --spring.datasource.username=sa --spring.datasource.password= --spring.jpa.database-platform=org.hibernate.dialect.H2Dialect --spring.jpa.defer-datasource-initialization=true --spring.sql.init.mode=always --spring.sql.init.data-locations=file:seed-h2.sql --spring.devtools.restart.enabled=false --spring.h2.console.enabled=true"
