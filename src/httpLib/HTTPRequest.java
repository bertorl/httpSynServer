/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpLib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author berti
 */
public class HTTPRequest extends HTTPHeaders {

    private HashMap<String, String> headers;
    private String REQ_METHOD;
    private String REQ_PATH;
    private String REQ_VERSION;
    InputStream in;

    public HTTPRequest(InputStream in) throws IOException, Exception {
        this.in = in;
        headers = new HashMap<String, String>();
        loadRequest(in);
    }

    public Enumeration<String> getHeadersName() {
        Vector<String> e = new Vector();
        headers.forEach((k, v) -> e.add(k));

        return e.elements();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getPath() {
        return REQ_PATH;
    }

    private void loadRequest(InputStream in) throws IOException, Exception {

        REQ_PATH = getPathRequest(HTTPUtils.readLine(in));
        String[] parameters;
        String data;
        while (!(data = HTTPUtils.readLine(in)).equals("")) {
            parameters = data.split(":");
            headers.put(parameters[0], parameters[1]);
        }
        // Recorrer las lineas hasta llegar al final
        // Separar cabeceras y contenido en el hash map
    }

    private String getPathRequest(String line) {
        String recurso = null;
        StringTokenizer st = new StringTokenizer(line, " ");
        st.nextToken();
        recurso = st.nextToken();
        recurso = recurso.substring(1, recurso.length());
        return recurso;
    }

}
