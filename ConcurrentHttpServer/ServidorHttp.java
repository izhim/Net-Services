import servidor1.*;
import utilidades.Utilidades;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 *
 * @author JOSE ANTONIO CARRILLO HUETE
 */
public class ServidorHttp {
    public static void main(String[] args){
        // variables y constantes
        final int PUERTO_DEFECTO = 80;
        int puerto;
        String mensaje = "";
        
        // cabecera de introducción del programa
        System.out.println("\n\nSERVIDOR HTTP CONCURRENTE");
        System.out.println("-------------\n");
        
        try {
            if(args.length > 0)
                puerto = Integer.parseInt(args[0]);
            else
                puerto = PUERTO_DEFECTO;
            // creamos el servidor y los contextos
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(puerto), 0);
            httpServer.createContext("/saludar", new HttpHandlerSaludar());
            httpServer.createContext("/calculadora", new HttpHandlerCalculadora());
            // iniciamos el pool de hilos
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();
            mensaje = "Servidor HTTP CONCURRENTE iniciado en el puerto " + puerto;
        // controlamos las distintas excepciones
        } catch (IOException ex) {
            mensaje = "Error de E/S: " + ex.getMessage();
        } catch (NumberFormatException ex){
            mensaje = "Error de formato: " + ex.getMessage();
        } catch (IllegalArgumentException ex){
            mensaje = "Error de valor invalido: " + ex.getMessage();
        }finally{
            System.out.println("["+Utilidades.getFechaHoraActualFormateada()+"] "+mensaje);
        }
        
    }
    
}
