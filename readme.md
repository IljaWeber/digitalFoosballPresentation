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

The first module - the Raspberry-Controller - tracks the foosball-table and whenever one team scores a goal, this goal
gets sent via Rest-Calls to the backend.

With the second module - the frontend - the user is able to start a game and send commands like "undo the last goal"
and more to the backend. Also the frontend presents the current state of the game - it gets updated by the backend
via a websocket.

The last module - the backend - is the center of the application. The backend holds the business logic, it knows when
it is legal to score a goal and when it's not. Also it is the connection between the Raspberry Pi and the frontend as
you may already have thought.
