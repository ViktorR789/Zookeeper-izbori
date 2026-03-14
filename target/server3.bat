@echo off
echo ============================================
echo  Starting Election Server 3 (Port 50053)
echo ============================================
java -cp "izbori-2026-0.0.1-SNAPSHOT.jar;lib/*" rs.ac.fink.izbori.server.ElectionAppServer localhost:2181 50053 log3.txt ../src/main/resources/election_config.json
pause