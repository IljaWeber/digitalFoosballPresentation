#Introduction
The foosball table uses light barriers for goal recognition. These are connected to a Raspberry Pi which 
runs a Java application.
 
##Description 
By using the library [Pi4j](https://pi4j.com/1.2/index.html) its possible to process IO inputs of the Raspberry Pi pins. 
After processing these inputs are send to the REST endpoints of the Spring Boot application.

##How to use  
By `mvn clean package` maven creates an executable jar. To use it on your own Raspberry Pi you need to transfer
 and execute it on the Raspberry. 




