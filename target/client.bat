@echo off
java -cp "izbori-2026-0.0.1-SNAPSHOT.jar;lib/*" rs.ac.fink.izbori.client.ControllerClient localhost:2181 ../src/main/resources/election_config.json
pause