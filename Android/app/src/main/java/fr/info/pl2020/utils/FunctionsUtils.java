package fr.info.pl2020.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FunctionsUtils {
    private FunctionsUtils(){}

    /**
     * Convertit un InputStream en String encodé en UTF-8
     * @see <a href="https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java#answer-35446009">Performance tests</a>
     */
    public static String inputStreamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int l;
            while ((l = is.read(b)) != -1) {
                baos.write(b, 0, l);
            }

            return baos.toString(StandardCharsets.UTF_8.name());
        } catch(IOException e) {
            return "";
        }
    }
}
