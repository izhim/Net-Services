import utilidades.Utilidades;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author JOSE ANTONIO CARRILLO HUETE
 */
public class HttpHandlerSaludar implements HttpHandler {
    
    public HttpHandlerSaludar(){
        super();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        // variables
        StringBuilder respuesta = new StringBuilder("Hola ");
        String[] datos;
        boolean conocido = false;
        
        // obtenemos la petición solicitada a través de un objeto URI
        URI uri = exchange.getRequestURI();
        String request = uri.toString();
        
        // controlamos mediante una expresión regular que la petición tenga el formato correcto
        Pattern p = Pattern.compile("^/saludar(\\?(nombre|apellido)=[a-zA-Z]+(&apellido=[a-zA-Z]+)?)?");
        Matcher m = p.matcher(request);
        
        if(m.matches()){
            // dividimos la petición en parámetros dentro de un array
            datos = request.split("=|\\?|\\&");
            System.out.println("["+Utilidades.getFechaHoraActualFormateada()+"] Atendiendo a peticion: "+request);

            // comprobamos si se ha introducido algún parámetro de nombre o apellido
            for(int i = 1; i < datos.length; i++){
                if((datos[i].equals("nombre")||datos[i].equals("apellido"))&&(i+1)<= datos.length){
                    respuesta.append(datos[i+1] + " ");
                    conocido = true;
                }
            }

            // si no se han introducido los parámetros le asignamos como persona desconocida
            if(!conocido)
                respuesta.append("persona no identificada");

        }else
            // enviamos un mensaje de formato incorrecto
            respuesta.append("persona no identificada correctamente");
        
        // mostramos la respuesta por pantalla
        System.out.println("["+Utilidades.getFechaHoraActualFormateada()+"] Respuesta a la peticion: "+ request +" -> "+respuesta.toString());

        // abrimos un stream, enviamos la respuesta y cerramos el stream
        exchange.sendResponseHeaders(200, respuesta.toString().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(respuesta.toString().getBytes());
        os.close();
    }
}
