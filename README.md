# Distributed Systems Project
This project is an implementation of a distributed system that handles shortest path queries on a dynamic graph. The system can process a workload consisting of a series of sequential operation batches. The workload can be produced by any node, and the operations can be arranged based on the node's choice. The system can handle concurrent requests, and the query results in the output reflect the order of the queries within the batches.

## Requirements

- Java 8 or higher
- Maven

---

## Installation

1. Clone the repository: `git clone <https://github.com/username/repo.git`>
2. Build the project: `mvn clean package`
3. Start the server: `java -jar target/server.jar`
4. Start the clients: `java -jar target/client.jar <number of clients>`

---

## Usage

The project consists of a server and multiple clients. The server initializes the graph and exposes it to the clients via remote method invocation (RMI). The clients generate batches of operations and send them to the server for processing.

---

### Server

The server can be started by running the `server.jar` file. The server reads in a list of edges from a file (`edges.txt` by default) and initializes the graph. The server then binds the graph to the RMI registry and starts listening for client connections. The server logs each operation and its result, as well as the time it took to process the batch. The log files are located in the `logs` directory.

---

### Clients

The clients can be started by running the `client.jar` file with the number of clients to start as an argument. The clients generate batches of operations (which can be add, delete, or query) and send them to the server for processing. The client logs each batch and the results of each query. The log files are located in the `logs` directory.

---

## Performance

The performance of the system was analyzed using the following metrics:

- Response time vs frequency of requests for the two variants (BFS and Bi-BFS)
- Response time vs number of nodes [1,5] for the two variants
- Response time vs percentage of add/delete operations of requests for the two variants
- Stress testing with 30% add/delete operations

The performance analysis showed that the Bi-Directional BFS method performed better than the BFS method in terms of response time. However, the response time for both methods increased as the frequency of requests and the number of nodes in the graph increased. The response time also varied depending on the percentage of add/delete operations in the requests.

---

## Conclusion

Implementing a distributed system to handle shortest path queries on a dynamic graph is a complex task that requires careful design and consideration of the system's components. This project provided valuable experience in designing and implementing distributed systems and conducting performance analysis on them.

---

# References

1. [https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html](https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html)
2. [https://www.tutorialspoint.com/java_rmi/java_rmi_application.htm](https://www.tutorialspoint.com/java_rmi/java_rmi_application.htm)
