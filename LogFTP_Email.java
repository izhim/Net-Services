import java.io.BufferedReader;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.time.LocalDateTime;
import java.util.Properties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Programa que pide por teclado un nombre de usuario y una contraseña en un
 * proceso repetitivo que finaliza cuando el nombre de usuario sea * o una cadena
 * vacía.
 * Después se conecta con un servidor FTP en LocalHost y actualiza un archivo
 * log.txt dentro de un directorio log donde se almacenan la fecha y la hora de
 * la conexión del usuario1.
 * Por último envía el contenido del log a través del servidor SMTP de Gmail
 * (no implementadas credenciales)
 * @author JOSE ANTONIO CARRILLO HUETE
 */
public class LogFTP_Email {
    
    public static void proceso(){
        
        String usuario;
        char[] clave;
        boolean usuarioValido;
        Console console = System.console();
        FTPClient clienteFTP;
        final String host = "127.0.0.1";
        int conexiones = 0;
        FileOutputStream fos;
        FileInputStream fis;
        final String ficheroLog = "LOG.txt";
        final String directorioLog = "LOG";
        File archivo;
        Logger logger = Logger.getLogger("MiLog");
        FileHandler fh;
        
        System.out.println("\nCONTROL DE LOG Y ENVIO DE CORREO");
        System.out.println("================================");
        
        try{
            do{
                System.out.print("Introduce el usuario: ");
                usuario = console.readLine();
                usuarioValido = (usuario.equals("") || usuario.equals("*")) ? false : true;
                if(usuarioValido){
                    System.out.print("Introduce la clave: ");
                    clave = console.readPassword();
                    // conectamos al servidor FTP
                    clienteFTP = new FTPClient();
                    clienteFTP.connect(host);
                    // comprobamos que la conexión sea correcta
                    if(FTPReply.isPositiveCompletion(clienteFTP.getReplyCode())
                            &&clienteFTP.login(usuario, String.valueOf(clave))){
                        System.out.println("Conexion FTP establecida");
                        conexiones ++;
                        
                        // Abrimos los streams de entrada y salida
                        fos = new FileOutputStream(ficheroLog);
                        fis = new FileInputStream(ficheroLog);
                        
                        // descargamos el archivo log y lo modificamos
                        clienteFTP.retrieveFile("/" + directorioLog + "/" + ficheroLog, fos);
                        fh = new FileHandler(ficheroLog, true);
                        logger.addHandler(fh);
                        logger.setUseParentHandlers(false);
                        fh.setFormatter(new SimpleFormatter());
                        logger.log(Level.INFO , "Conexion: " + LocalDateTime.now());
                        
                        // subimos el archivo modificado
                        if(clienteFTP.storeFile("/" + directorioLog + "/" + ficheroLog, fis)){
                            System.out.println("LOG actualizado.\n");
                        }
                        // cerramos los streams
                        fos.close();
                        fis.close();
                        // enviamos el archivo almacenado y lo eliminamos
                        archivo = new File(ficheroLog);
                        enviarMail(archivo);
                        archivo.delete();
                    }else
                        System.out.println("Usuario o clave no validos");
                    clienteFTP.disconnect();
                }
            }while(usuarioValido);
        }catch(IOException ex){
            System.out.println("No se ha podido establecer la conexion");
        }
    }
    
    /**
     * Método que envía el contenido de un archivo por email a través de un
     * servidor SMTP de Gmail
     * 
     * @param archivo archivo cuyo contenido vamos a enviar
     * @return true si se ha realizado el envío correctamente
     */
    public static boolean enviarMail(File archivo){
        
        Socket socket           = null;
        DataInputStream dis     = null;
        DataOutputStream dos    = null;
        Session sesion          = null;
        Message mensaje         = null;
        Properties props        = null;
        Transport transporte    = null;
        boolean enviado         = false;
        
        final String remite     = "";
        final String claveEmail = "";
        final String destino    = "";
        final String asunto     = "";
        
        // configuramos las propiedades
        props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remite);
        props.put("mail.smtp.clave", claveEmail);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        
        // creamos la sesión y el mensaje
        sesion = Session.getDefaultInstance(props);
        mensaje = new MimeMessage(sesion);
        
        try{
            // confeccionamos el mensaje
            mensaje.setFrom(new InternetAddress(remite));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            mensaje.setSubject(asunto);
            mensaje.setText(archivoToString(archivo));
            
            // configuramos el transporte con el protocolo SMTP y enviamos el mensaje
            transporte = sesion.getTransport("smtp");
            transporte.connect("smtp.gmail.com", remite, claveEmail);
            transporte.sendMessage(mensaje, mensaje.getAllRecipients());
            transporte.close();
            enviado = true;
        }catch(AddressException ex){
            
        } catch (MessagingException ex) {
            Logger.getLogger(Ejercicio2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio2.class.getName()).log(Level.SEVERE, null, ex);
        }       
            
        return true;
    }
    
    /**
     * Método que lee un archivo devuelve su contenido en un String
     * 
     * @param archivo archivo cuyo contenido vamos a leer
     * @return cadena de caracteres con el contenido del archivo
     * @throws FileNotFoundException si no encuentra el archivo
     * @throws IOException error de entrada/salida
     */
    public static String archivoToString(File archivo) throws FileNotFoundException, IOException{
        
        StringBuilder resultado = new StringBuilder("");
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        do{
            resultado.append("\n"+br.readLine());
        }while(br != null);
        
        return resultado.toString();
    }
}
