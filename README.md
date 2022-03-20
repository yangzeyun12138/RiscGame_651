ECE 651: CI/CD Intro: Factoring Server 
![pipeline](https://gitlab.oit.duke.edu/ch450/ece651-project1/badges/main/pipeline.svg)
![coverage](https://gitlab.oit.duke.edu/ch450/ece651-project1/badges/main/coverage.svg?job=test)
======================================

The code in this project is for a server which reads
numbers, and prints back their prime factors.  While
that code is not itself particularly exciting, the point
of this project is to walk studenst through the setup of 
CI/CD on Duke's Gitlab.
[Detailed coverage](https://ch450.pages.oit.duke.edu/ece651-project1/dashboard.html)

======================================
Procedure: 
1. Git clone the repository: `git clone https://gitlab.oit.duke.edu/ch450/ece651-project1.git`
2. `cd ece651-project1`, and then type `./gradlew :client:installDist ` , and type `./gradlew run-server`to start the server.
3. open other terminals, and then  `cd ece651-project1/client/build/install/client/bin`, and then `./client` to start the client.

_P.S. If you want to change the number of players for the map, just type `cd ece651-project1/server/src/main/java/edu/duke/ece651/mp/server`, and `emacs -nw Server.java` to change the **Territory.txt** into **Territory2.txt**, **Territory4.txt** or **Territory5.txt**._


