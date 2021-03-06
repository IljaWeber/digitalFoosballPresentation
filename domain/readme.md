# Introduction
This module represents the business logic of the project. It is written in **Java** and the package structure represents the different 
use cases/ game modes, kind of domain driven design. 


## Used technologies, techniques and principles
- Java
- Spring Boot
- JUnit5
- Test Driven Development 
- Clean Code
- Different Design Patterns


## Build status
#### Game Modes are available:

##### AdHoc-Game:
A quick game with default names for the teams and players. For a win it is necessary to win two matches. 
To win a match you need to score at least 6 goals AND have a score difference of two goals to the opponent. 

##### Ranked-Game:
The win conditions are the same as in an Ad Hoc game but you can choose a name for your team and the players.
The names are stored in a database and may be reused in future ranked games. 

##### Time-Game:
As in the Ad Hoc in a time game you play with default team and player names. The time limit of a half is 7 minutes. 
There are two play halves and a half time. To win a time game a team needs to reach 10 goals or to lead after the second play half.
If there is no leader after the time has expired, the game ends tie. 


## How to use
To run the Spring Boot application on localhost port: 8888 run in command line: `mvn spring-boot:run`
This will also start the React application.
Open your browser and visit: `localhost:8888/index.html`

 



