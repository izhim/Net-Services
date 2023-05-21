# Net Services In Java

### In this section I collect some small projects from different security and network services using Java.
### [ResumenFichero](https://github.com/izhim/Servicios-en-Red/blob/main/ResumenFichero.java)
Generates the summary of a file passed as an argument. The digest is displayed in hexadecimal using the SHA-256 and MD5 algorithms.
### [LogFTP_Email](https://github.com/izhim/Servicios-en-Red/blob/main/LogFTP_Email.java)
It prompts for a username and password in a repetitive process until the username is * or an empty string. Then connects to an FTP server on LocalHost and updates a log.txt file placed in a log directory where the date and time of user connections are stored.
Finally, it sends the log file content through the Gmail SMTP server (no credentials implemented).
### [ConcurrentHttpServer](https://github.com/izhim/Net-Services/tree/main/ConcurrentHttpServer)
Implementation of an HTTP server that listens from a port received from the console as a parameter when the program starts, providing two different contexts: "/calculator" and "/greet" and working concurrently to be able to serve several clients simultaneously.
