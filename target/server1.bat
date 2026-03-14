@echo off
echo ============================================
echo  Starting Election Server 1 (Port 50051)
echo ============================================
java -cp "izbori-2026-0.0.1-SNAPSHOT.jar;lib/*" rs.ac.fink.izbori.server.ElectionAppServer localhost:2181 50051 log1.txt ../src/main/resources/election_config.json
pause