/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpMain;

import httpLib.*;
import java.io.*;
import java.net.Socket;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author berti
 */
public class ClientWorker implements Runnable {

    Socket client;
    InputStream inClientStream;
    OutputStream outClientStream;
    File log;

    ClientWorker(Socket client) throws IOException {

        this.client = client;
        this.log = logFileHTTP();
        this.inClientStream = client.getInputStream();
        this.outClientStream = client.getOutputStream();
    }

    public File logFileHTTP() throws IOException {
        File f = new File("log/logRequest.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        return f;
    }

    public static void logInfoHTTP(File f, String info) throws Exception {
        FileOutputStream out = new FileOutputStream(f, true);
        out.write(info.getBytes());
        out.write("\r\n".getBytes());
        out.close();
    }

    @Override
    public void run() {
        try {

            boolean keepAlive;
            int conMax = 5;
            String data;

            //data = HTTPUtils.transferData(inClientStream);
            //logInfoHTTP(log, data);
            HTTPResponse response = new HTTPResponse(outClientStream);
            HTTPRequest request = new HTTPRequest(inClientStream);
            String path = request.getPath();
            File fq = new File(path);
            HTTPUtils.processGetPetition(request, response);
            //response.testResponse(request);
            //for(Enumeration e = request.getHeadersName(); e.hasMoreElements();)
            //    logInfoHTTP(log, (String) e.nextElement());
            //ProcessPetition.serve(request, response);
           
            /*keep-Alive connection
            *   keepAlive = request.getConnection();
            *   if(keepAlive){
            *       request.getTimeOut();
            *       request.getMaxConnections();
            *   }
            */
           // while (false && conMax > 0) {
                //if (inClientStream.available() != 0) {
                    //data = HTTPUtils.transferData(inClientStream);
                    //request = new HTTPRequest(data);
                    //response = new HTTPResponse(request);
                   //outClientStream.write(data.getBytes());
                   // logInfoHTTP(log, data);               
                   // conMax--;
                //}
           // }
            //logInfoHTTP(log, "--------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                inClientStream.close();
                outClientStream.close();
                client.close();
            } catch (Exception ex) {
                Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
