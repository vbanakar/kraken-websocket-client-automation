#kraken-websocket-client-automation

kraken-websocket-client automation is a basic java application which implements few tests for testing Kraken websocket api 1.9.0 as
per the requirements described in tt-sdet-assement document.

## Instructions to execute these tests with Docker


Prerequisite : Docker installed

1) Clone this repo to your desktop


2) Go to root directory i.e., kraken-websocket-client-automation and execute below command
`docker build -t websocketTests .`


3) To execute tests run below, execution results along with received messages from would be displayed in console / terminal:
`docker run websocketTests mvn clean test`


## Instructions to execute these tests in an IDE

If you open this project in Intellij or Eclipse IDE then you must have the following prerequisites

1) maven-3.8.6
2) Java 1.8 and above