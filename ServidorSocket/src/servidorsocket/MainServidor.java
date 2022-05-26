package servidorsocket;

import java.io.IOException;
import java.net.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Título:      MainServidor
 * Descripción: Clase para iniciar nuestro servidor concurrente
 * @author      David Jiménez Riscardo
 * @version     1.0
 */
public class MainServidor {

    // Límite de conexiones con clientes
    public static final int LIMITE_CONEXIONES = 3;

    /**
     * Programa principal
     * @param args Puerto indicado desde la línea de comandos. Si no se indica el sistema tomará el puerto 6000 por defecto
     */
    public static void main(String[] args) {
        
        int puertoServidor=6000;
        if(args.length!=1){
            System.out.println("Error de parámetros,tomaremos los parámetros por defecto");
        }else{
            try{
                puertoServidor = Integer.parseInt(args[0]);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        //Declaro un array "hilosServidor" con 3 hilos
        Vector<Thread> hilosServidor = new Vector<Thread>(LIMITE_CONEXIONES);

        //Declaro el socket del servidor
        ServerSocket server = null;
          
        int numCliente = 0;

        try {
            // Punto de conexión del servidor (socket en el puerto indicado en la máquina donde se ejecute el proceso)
            Socket conexion = null;
            //Instancia el socket del servidor
            server = new ServerSocket(puertoServidor);

            // Mensaje de arranque de la aplicación
            System.out.println("SERVIDOR CONCURRENTE");
            System.out.println("--------------------");
            //Debo especificar en el mensaje posterior el número de puerto
            System.out.printf("Servidor de David Jiménez Riscardo. Servidor iniciado. Escuchando por el puerto "+puertoServidor+"\n");
            System.out.println("Esperando conexión con cliente.");

            
            while (numCliente < LIMITE_CONEXIONES) {

                // Quedamos a la espera ("escuchando") de que se realice una conexión con el socket de servidor.
                // En el momento en que eso suceda, se aceptará. Mientras tanto, la ejecución queda aquíe 
                // bloqueada en espera a que se reciba esa petición por parte de un cliente.
                conexion = server.accept();
                
                if(conexion!=null){
                    System.out.println("Conexión establecida con cliente.");

                    // Creamos un nuevo hilo de ejecución para servir a este nuevo cliente conectado y lo almaceno en el array de hilos
                    Servidor s = new Servidor(conexion, numCliente++);
                    hilosServidor.add(s);

                    // Lanzamos la ejecución de ese nuevo hilo
                    s.start();
                }
           
            } // y seguimos "escuchando" a otras posibles peticiones de cliente
            
            // Finaliza la interacción con posibles clientes. Cerramos el socket de servidor
            server.close();

        } catch (SocketException ex) {
            System.out.printf("Error de socket: %s\n", ex.getMessage());
        } catch (IOException ex) {
            System.out.printf("Error de E/S: %s\n", ex.getMessage());
        }

        // Esperamos a que todos los hilos del servidor finalicen su ejecución
        for (Thread hilo : hilosServidor){
                    
            try {
                hilo.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
               
        }
        
        System.out.println("Fin de ejecución del servidor.");
    }

}
