package practica2;

import java.io.*;
import java.util.Date;

/**
 * Clase para la gestión de errores
 * <p>
 * Se resgistran los errores en un fichero de texto
 *
 * @author nadalLlabres
 */
public class ErrorLog {

    private static final String FILE_NAME = "logs.txt";

    /**
     * Loggear excepcion
     *
     * @param ex excepcion
     */
    static public void logException(Exception ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        String slogs = writer.toString();
        System.err.println("Se ha producido un error. El error es:\n" + slogs);
        try {
            FileWriter fr = new FileWriter(FILE_NAME, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write("********************** Error date: " + (new Date()).toString());
            br.newLine();
            br.write(slogs);
            br.write("********************** End error report");
            br.newLine();
            br.newLine();
            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println("Error en la manipulación del archivo de logs.");
            e.printStackTrace();
        }
    }
}
