in directory of pom.xml: mvn package spring-boot:repackage
in target directory: java -jar demo-0.0.1-SNAPSHOT.jar --myLoc="abc" --server.port=1234

as master node	
	java -jar <jar name> --server.port=1234 --myLoc="some location" 

as slave node
	java -jar <jar name> --masterIP=1.1.1.1 --masterPort=1234 --server.port=1234 --myLoc="some location"
		// here --server.port is the port of the application running