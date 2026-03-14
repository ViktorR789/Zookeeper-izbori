@echo off
echo ============================================
echo  Starting Election Server 2 (Port 50052)
echo ============================================
java -cp "izbori-2026-0.0.1-SNAPSHOT.jar;lib/*" rs.ac.fink.izbori.server.ElectionAppServer localhost:2181 50052 log2.txt ../src/main/resources/election_config.json
pause