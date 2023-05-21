# Servicios en Red con Java

### En este apartado recojo varios pequeños proyectos de distintos servicios de seguridad y en red utilizando Java
### [ResumenFichero](https://github.com/izhim/Servicios-en-Red/blob/main/ResumenFichero.java)
Genera el resumen de un fichero pasado como argumento. El resumen se muestra en hexadecimal utilizando los algoritmos SHA-256 y MD5.
### [LogFTP_Email](https://github.com/izhim/Servicios-en-Red/blob/main/LogFTP_Email.java)
Pide por teclado un nombre de usuario y una contraseña en un proceso repetitivo que finaliza cuando el nombre de usuario sea * o una cadena vacía. Después se conecta con un servidor FTP en LocalHost y actualiza un archivo log.txt dentro de un directorio log donde se almacenan la fecha y la hora de la conexión del usuario1.
Por último envía el contenido del log a través del servidor SMTP de Gmail (no implementadas credenciales)
