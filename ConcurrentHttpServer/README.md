# CONCURRENT HTTP SERVER

Implementation of an HTTP server that listens from a port received from the console as a parameter when the program starts, providing two different contexts: "/calculadora" and "/saludar" and working concurrently to be able to serve several clients simultaneously.

The HttpServer and HttpHandler classes, available in the com.sun.net.httpserver package, provide us with the possibility of organizing from a higher level all the management that an HTTP server must carry out without having to worry about details such as opening/closing sockets or having to wait to read from an input stream.

Using these high level tools we can quite easily implement an HTTP server that will allow us to concentrate on the service we want to provide without having to program the more technical details of the connections. In other words, we can focus more on the application level by leaving the details of the transport level (such as socket management) to those specialized HTTP classes.
