@echo off
echo ============================================
echo  Starting Controller Client
echo  Controllers: 5
echo  Stations: 100
echo ============================================
java -cp "izbori-2026-0.0.1-SNAPSHOT.jar;lib/*" rs.ac.fink.izbori.client.ControllerClient localhost:2181 5 100
pause