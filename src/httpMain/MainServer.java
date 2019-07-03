/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpMain;

import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author berti
 */
public class MainServer {
    
    private static int port=2024;
    private static ExecutorService taskHandler;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
        *    Properties config = new Properties();
        *    try {
        *        config.load(new FileInputStream("config.ini"));
        *        port = Integer.parseInt(config.getProperty("port", "8080"));
        *    }catch (Exception ex){
        *        ex.printStackTrace();
        *    }
        **/
        try{
            
            ServerSocket server = new ServerSocket(port);
            taskHandler = Executors.newCachedThreadPool();
            while(true){
                taskHandler.execute(new ClientWorker(server.accept()));    
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
}
