ECE 651: eval1
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
2. For server, `cd ece651-project1` and type `./gradlew run-server`to start the server. 
3. For client, `cd ece651-project1` and then type `./gradlew :client:installDist ` to install client excuted program. Then `cd ece651-project1/client/build/install/client/bin`, and then `./client` to start the client. Then follow the instruction to enter the host name and port number where server is running on.
(If you run server and client on the same machine, host name should be 0.0.0.0)

_P.S. The default port number where server runs on is 9999, and the default number of players for the map is two. If you want to change these, just type `cd ece651-project1/server/src/main/java/edu/duke/ece651/mp/server`, and `emacs -nw Server.java`. In main function, you can change the parameter of **ServerSk constructor** from (**maps, 9999**) to (**maps, port number you want**). In order to change number of players, you should change parameters of funciton **create_one_map** from (**Territory2.txt, 2**) to (**Territory3.txt, 3**), (**Territory4.txt, 4**) or (**Territory5.txt, 5**.)_ 


