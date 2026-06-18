@echo off
REM Startet Voxera lokal mit H2 (In-Memory), ohne MySQL.
REM WICHTIG: Aendert NICHT application.properties -> der IDE-Start der anderen
REM nutzt weiterhin MySQL. Dieser Override gilt nur fuer diesen run.bat-Start.
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot
cd /d C:\Users\zarro\voxera\main2
call mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:h2:mem:voxeradb;DB_CLOSE_DELAY=-1 --spring.datasource.driver-class-name=org.h2.Driver --spring.datasource.username=sa --spring.datasource.password= --spring.jpa.database-platform=org.hibernate.dialect.H2Dialect --spring.h2.console.enabled=true"
