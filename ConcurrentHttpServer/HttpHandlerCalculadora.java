import utilidades.Calculadora;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilidades.Utilidades;

/**
 *
 * @author JOSE ANTONIO CARRILLO HUETE
 */
public class HttpHandlerCalculadora implements HttpHandler {
    public HttpHandlerCalculadora(){
        super();
    }

    @Override
    public synchronized void handle(HttpExchange exchange) throws IOException {
        
        // variables
        String respuesta = "";
        String[] datos;
        double op1,op2;
        char ope;
        
        // obtenemos la petición solicitada a través de un objeto URI
        URI uri = exchange.getRequestURI();
        String request = uri.toString();
        System.out.println("["+Utilidades.getFechaHoraActualFormateada()+"] Atendiendo a la peticion: "+ request);
        // controlamos mediante una expresión regular que la petición tenga el formato correcto
        Pattern p = Pattern.compile("^/calculadora\\?op1=[0-9]+&op2=[0-9]+&ope=(suma|resta|multiplica|divide)$");
        Matcher m = p.matcher(request);
        
        // traducimos el operador al caracter que reconoce el método al que vamos a llamar
        if(m.matches()){
            datos = request.split("=|\\?|\\&|=");
            op1 = Double.parseDouble(datos[2]);
            op2 = Double.parseDouble(datos[4]);
            switch(datos[6]){
                case "suma":
                    ope = '+';
                    break;
                case "resta":
                    ope = '-';
                    break;
                case "multiplica":
                    ope = '*';
                    break;
                case "divide":
                    ope = '/';
                    break;
                default:
                    ope = ' ';  // lo utilizamos para que tenga valor, este caso no se llega a dar
                                // ya que lo controlamos con la expresión regular
            }
            // creamos un objeto calculadora y obtenemos el resultado
            Calculadora calculadora = new Calculadora(op1, op2, ope);
            respuesta = calculadora.getResultado();
            
        // si el formato de la operación introducida no es correcto lo mostramos mediante un mensaje    
        }else
            respuesta = "El formato de la operacion no es correcto";
        
        // mostramos la respuesta por pantalla
        System.out.println("["+Utilidades.getFechaHoraActualFormateada()+"] Respuesta a la peticion: "+ request +" -> "+respuesta.toString());

        // abrimos un stream, enviamos la respuesta y cerramos el stream
        exchange.sendResponseHeaders(200, respuesta.toString().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(respuesta.toString().getBytes());
        os.close();
    }
}
