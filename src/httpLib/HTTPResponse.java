/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpLib;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author berti
 */
public class HTTPResponse extends HTTPHeaders {

	private OutputStream out;
    private PrintWriter pw;
    private HashMap<String, String> RES_headers;

    public HTTPResponse(OutputStream out) {
        this.out = out;
        pw = new PrintWriter(out);
        RES_headers = new HashMap<String, String>();
    }

    public static String getDate() {
        String format = "EEE, dd MMM yyyy HH:mm:ss zzz";
        return (new SimpleDateFormat(format).format(new Date()));
    }

    public PrintWriter getPrintWriter() {
        return pw;
    }

    public void setMimeType(String path) {
        String ext = path.substring(path.lastIndexOf('.') + 1, path.length());

        if (ext.equals("html")) {
            RES_headers.put("Content-Type", "text/html");
        } else if (ext.equals("png")) {
            RES_headers.put("Content-Type", "image/png");
        } else if (ext.equals("jpg")) {
            RES_headers.put("Content-Type", "image/jpeg");
        } else if (ext.equals("js")) {
            RES_headers.put("Content-Type", "text/javascript");
        } else if (ext.equals("css")) {
            RES_headers.put("Content-Type", "text/css");
        } else if (ext.equals("pdf")) {
            RES_headers.put("Content-Type", "application/pdf");
        } else if (ext.equals("json")) {
            RES_headers.put("Content-Type", "text/json");
        } else if (ext.equals("txt")) {
            RES_headers.put("Content-Type", "text/plain");
        } else if (ext.equals("java")) {
            RES_headers.put("Content-Type", "text/plain");
        } else if (ext.equals("ico")){
            RES_headers.put("Content-Type", "image/x-icon");
        } else if (ext.equals("mp4")){
            RES_headers.put("Content-Type", "video/mp4");
        }
    }
    
    public String getMimeType(){
        return RES_headers.get("Content-Type");
    }
    public OutputStream getOutputStream() {
        return out;
    }

    public HashMap<String, String> getHeaders() {
        return RES_headers;
    }
}
