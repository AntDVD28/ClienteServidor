package clientesocket;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Título:      Cliente
 * Descripción: Clase para lanzar un hilo por cada cliente
 * @author      David Jiménez Riscardo
 * @version     1.0
 */
public class Cliente extends Thread{
    
    private String cadenaOperacion;
    private String hostServidor;
    private int puertoServidor;
    
    /**
     * Método constructor para la creación de hilos
     * @param cadenaOperacion Cadena con la operación a realizar
     * @param hostServidor Dirección IP del servidor    
     * @param puertoServidor Puerto de escucha del servidor
     */
    public Cliente(String cadenaOperacion, String hostServidor, int puertoServidor){
        this.cadenaOperacion=cadenaOperacion;
        this.hostServidor=hostServidor;
        this.puertoServidor=puertoServidor;
    }
    
    /**
     * Método que se ejecuta tras el inicio del hilo
     */
    @Override
    public void run(){
        
        System.out.println("PROGRAMA CLIENTE INICIADO ...");
        Socket cliente = null;
        
        try {
            //CREO EL SOCKET CON EL PUERTO Y EL HOST DEL SERVIDOR
            cliente = new Socket(this.hostServidor, this.puertoServidor);
            
            //CREO FLUJO DE SALIDA AL SERVIDOR
            DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
            
            
            //ENVÍO AL SERVIDOR UNA CADENA CON LA OPERACIÓN QUE DEBE REALIZAR
            flujoSalida.writeUTF(this.cadenaOperacion);
            
            //CREO FLUJO DE ENTRADA AL SERVIDOR
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
            
            
            //RECIBO EL MENSAJE CON LA CADENA DE RESPUESTA DEL SERVIDOR
            System.out.println(flujoEntrada.readUTF());
            
            //CERRAR STREAMS Y SOCKETS
            flujoSalida.close();
            flujoEntrada.close();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
