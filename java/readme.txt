Starting up the server:

Docker must be installed and running, must be connected to internet
In commandline type: "docker-compose up" --> takes a while the first time

After server start
Host: 0.0.0.0
Admin port:9990
HTTP port: 8080

Get report image: http://0.0.0.0:8080/quantumup/api/reports/generate?experimentId=<<experimentId>>&metric=<<measurement>>&charttype=<<charttype>>
Get report text: http://0.0.0.0:8080/quantumup/api/reports/data?experimentId=<<experimentId>>&metric=<<measurement>>&delimiter=<<charttype>>

replace <<>> with values

experimentId = id from execution team, for demo as is now values are randomly generated in server so enter anything
measuremen = CPU or Memory
charttype = LINE, BAR, PIE and SCATTER, case sensitive all uppercase
delimiter = COMMA, SPACE, SEMICOLON or TAB
