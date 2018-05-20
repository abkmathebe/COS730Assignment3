Starting up the server:

Docker must be installed and running, must be connected to internet.

To start:
In commandline type: "docker-compose up" --> takes a while the first time
To rebuild java project in quantumup folder
In commandline type: "docker-compose up --build"

After server start
Host: 0.0.0.0
Admin port:9990
HTTP port: 8080

Get report images: http://0.0.0.0:8080/quantumup/api/reports/generate?experimentId=<<experimentId>>&charttype=<<charttype>>
Get report text: http://0.0.0.0:8080/quantumup/api/reports/data?experimentId=<<experimentId>>&delimiter=<<charttype>>

replace <<>> with values

experimentId = id from execution team, for demo as is now values are randomly generated in server so enter anything
charttype = LINE, BAR, PIE and SCATTER
delimiter = COMMA, SPACE, SEMICOLON or TAB
