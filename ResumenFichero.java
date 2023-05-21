import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Genera el resumen de un fichero, el fichero se debe elegir del disco duro. El
 * resumen se debe mostrar en hexadecimal. Utiliza los algoritmos SHA-256 y MD5
 * y comprueba los resúmenes generados.
 * 
 * @author JOSE ANTONIO CARRILLO HUETE
 */
public class ResumenFichero {
    
    public static void main(String[] args){
        
        if(args.length == 1){
            String archivo = args[0];
            mostrarResumen(args[0], "SHA-256");
            mostrarResumen(args[0], "MD5");
        }else
            System.out.println("Argumentos no válidos");
    }
    
    /**
     * Muestra un resumen de un archivo en base al algoritmo elegido
     * 
     * @param nombreArchivo Nombre del archivo a convertir
     * @param algoritmo algoritmo utilizado
     */
    public static void mostrarResumen(String nombreArchivo, String algoritmo){
        try {
                File archivo = new File(nombreArchivo);
                System.out.printf("Fichero a resumir con %s: %s\n",algoritmo, archivo.getName());
                System.out.println("Mensaje en hexadecimal: " + obtenerResumen(archivo, algoritmo));
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Error: algoritmo no válido");
            } catch (IOException ex) {
                System.out.println("Error de entrada/salida");
            }
    }
    
    /**
     * Método que obtiene el resumen de un archivo según el algoritmo facilitado
     * 
     * @param archivo nombre del archivo a mostrar
     * @param algoritmo algoritmo utilizado
     * @return resumen del archivo en hexadecimal
     * @throws NoSuchAlgorithmException si el algoritmo no es válido
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si hay un error de entrada/salida
     */
    public static String obtenerResumen(File archivo, String algoritmo) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
        byte[] buffer = new byte[1];
        byte[] resumen;
        int caracter;
        StringBuilder resultado = new StringBuilder("");
        MessageDigest digest = MessageDigest.getInstance(algoritmo);

        FileInputStream fis = new FileInputStream(archivo);
        caracter = fis.read(buffer);
        while(caracter != -1){
            digest.update(buffer);
            caracter = fis.read(buffer);
        }
        fis.close();
        resumen = digest.digest();

        // conversión a HEX
        for(byte res : resumen){
            resultado.append(Integer.toHexString((res >> 4)& 0xf));
            resultado.append(Integer.toHexString(res & 0xf));
        }
        return resultado.toString().toUpperCase();
    }
}
