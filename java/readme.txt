Starting up the server:

Docker must be installed and running, must be connected to internet
In commandline type: "docker-compose up" --> takes a while the first time

After server start
Host: 0.0.0.0
Admin port:9990
HTTP port: 8080

Get report: http://0.0.0.0:8080/quantumup/api/reports/generate?experimentId=<<experimentId>>&metric=<<measurement>>&charttype=<<charttype>>

replace <<>> with values

experimentId = id from execution team, for demo as is now values are randomly generated in server so enter anything
measurement = currenntly = CPU-Usage or Memory-Usage, case sensitive
charttype = LINE, BAR, PIE and SCATTER, case sensitive all uppercase

Etc...

1. Intergration: the server has two resources useMock(boolean) acts as a switch if true we generate mock data and create reports, if false we query execution unit for 
   data, second one is executionUrl(String) and is baseUrl for the execution unit to use if useMock is false
2. Server is also connected to postgres db, still not sure how to create tables through docker. When container is up DB can be accessed at 0.0.0.0:5432/reportsDB. user=postgres, password=P@55w0rD
3. Code is in quantumup folder
