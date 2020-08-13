#The Digital Foosball Project 
We are living in a rapid changing world. Also our foosball experience needs to evolve - **The Digital Foosball Project** was born.

##A good training piece 
Mainly it has been introduced for transforming the analog user experience to a, tech company worthy, modern implementation. 
Since Valtech started training apprentices, the project changed into to an exercise for the new trainees. 

##Installation
Digital Foosball is managed by**Apache Maven** as build tool. It requires **Java** installed on your maschine.  
After the first checkout run: `mvn install` 
It will download, compile all needed dependencies and run all tests.
  
It's split up in different sub modules: 
 
- [Backend-readme](backend/readme.md)

- [Frontend-readme](frontend/readme.md)

- [Raspberry-Controller-readme](raspicontroller/readme.md)

##Interaction among modules

User commands like choosing game mode, goal scoring or team naming triggering through REST calls 
the business layer of the application. Processed commands are send to the web application by a web socket.  


