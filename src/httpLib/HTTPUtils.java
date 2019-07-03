/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpLib;

import java.io.*;
import java.util.HashMap;


/**
 *
 * @author berti
 */
public class HTTPUtils{

	protected static String readLine(InputStream in) throws IOException {
        
        int flag = 0;
        StringBuffer line = new StringBuffer();
        while (flag < 2) {
            char c = (char)in.read();
            if(c == '\n' || c == '\r')
                flag++;
            else
                line.append(c);
        }
        return line.toString();
    }
    
    protected static void writeLineToResponse (PrintWriter pw, String msg){
        
        pw.print(msg);
        pw.print("\r\n");
        pw.flush();
    }

    protected static void writeHeaders(PrintWriter pw, HashMap<String, String> headers) {

        headers.forEach((k,v)->{pw.print(k + ": ");pw.print(v + "\r\n");});
        pw.write("Server: prueba\r\n");
        pw.print("\r\n");
        pw.flush();
    }

    protected static void writeData(PrintWriter pw, OutputStream out, HashMap<String, String> headers, File f) throws FileNotFoundException, IOException {
        
        FileInputStream resourceStream = new FileInputStream(f);
        byte[] buffer = new byte[1024];
        int tam_read;
        
        HTTPUtils.writeLineToResponse(pw, HTTPHeaders.OK);
        writeHeaders(pw, headers);
        while ((tam_read = resourceStream.read(buffer, 0, 1024)) > 0)
            out.write(buffer, 0, tam_read);
        
        out.flush();
        resourceStream.close();
    }

    protected static void sendVideo(PrintWriter pw, OutputStream out, HashMap<String, String> res_headers, File f, HTTPRequest request) throws FileNotFoundException, IOException {
        
        RandomAccessFile file = new RandomAccessFile(f, "r");
        String range_head = request.getHeader("Range").substring(7);
        res_headers.put("Accept-Ranges", "bytes");
        String start_h = range_head.substring(0, range_head.indexOf("-"));
        String end_h = range_head.substring(range_head.indexOf("-")+1);
        //if(end_h.length() <= 0){
            long start = Long.parseLong(start_h);
            long end = f.length()-1;
            long size = f.length();
            
            res_headers.replace("Content-length", String.valueOf(end));
            res_headers.put("Content-Range","bytes " + start + "-" + end + "/" + size);
        //}
        
        HTTPUtils.writeLineToResponse(pw, HTTPHeaders.PARTIAL_CONTENT);
        writeHeaders(pw, res_headers);
        copyData(out, file, start, end);
    }

    protected static void copyData(OutputStream out, RandomAccessFile file, long start, long end) throws IOException {
        
        file.seek(start);
        byte[] buffer = new byte[10240]; //10KB
        BufferedOutputStream buff_out = new BufferedOutputStream(out);
        int readed;
        while((readed = file.read(buffer, 0, 10240)) > 0)
            if((end-=readed)>0)
                buff_out.write(buffer, 0, readed);
            else{
                buff_out.write(buffer, 0, (int) end + readed);
                break;
            }  
        
        buff_out.flush();
        file.close();
    }
    
    public static void processGetPetition(HTTPRequest request, HTTPResponse response) throws FileNotFoundException, IOException {
        
    	String path = request.getPath();
        File f = new File(path);
        HashMap<String, String> headers = response.getHeaders();
        PrintWriter pw = response.getPrintWriter();
        OutputStream out = response.getOutputStream();
        
        headers.put("Content-length", "" + f.length());
        response.setMimeType(path);        
    	
        if(request.getHeader("Range") != null && response.getMimeType().equals("video/mp4")){
            sendVideo(pw, out, headers, f, request);
        }
        else{
            writeData(pw, out, headers, f);
        }
    }
    
}
