package servidorsocket;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Título:      Servidor
 * Descripción: Clase para lanzar en el servidor un hilo por cada petición cliente que llegue
 * @author      David Jiménez Riscardo
 * @version     1.0
 */
public class Servidor extends Thread {

    private Socket socketCliente;

    // Flujo de salida a través del cual enviaremos información al proceso cliente 
    // conectado a través del socket
    private DataOutputStream flujoEscrituraCliente;

    // Flujo de entrada a través del cual recibiremos información desde el proceso cliente
    private DataInputStream flujoEntradaCliente;

    private int numCliente;

    /**
     * Método constructor para la creación de hilos
     * @param socketCliente Conexión con el cliente
     * @param numCliente Número de cliente
     */
    public Servidor(Socket socketCliente, int numCliente) {
        try {
            this.socketCliente = socketCliente;
            // Flujo de salida a través del cual enviaremos información al proceso cliente
            // conectado a través del socket
            this.flujoEscrituraCliente = new DataOutputStream(this.socketCliente.getOutputStream());
            
            // Flujo de entrada a través del cual recibiremos información desde el proceso cliente
            this.flujoEntradaCliente = new DataInputStream(this.socketCliente.getInputStream());
            
            this.numCliente = numCliente;
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que se ejecuta tras el inicio del hilo
     */
    @Override
    public void run() {
        // Creamos las variables para las operaciones de la calculadora    
        double n1 = 0, n2 = 0;
        char op = ' ';
        String resultado, peticionCliente = null;
        Calculadora miCalculadora = null;
        StringTokenizer token = null;
        int estado = 0;

        System.out.printf("Iniciado hilo servidor %d.\n", this.numCliente);

        try {

            // En espera de la recepción de peticiones por parte del cliente
            peticionCliente = flujoEntradaCliente.readUTF();
            if (!peticionCliente.isEmpty()) {
                System.out.println("Recibiendo del CLIENTE: \n\t" + peticionCliente);
                if (peticionCliente.equals("FIN") == false) {
                    token = new StringTokenizer(peticionCliente, ";");
                    try {
                        n1 = Double.parseDouble(token.nextToken());
                        n2 = Double.parseDouble(token.nextToken());
                        op = token.nextToken().charAt(0);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }                 
                    miCalculadora = new Calculadora(n1, n2, op);
                } 
            }

            // Devuelvo al cliente el resultado de la operación
            flujoEscrituraCliente.writeUTF(miCalculadora.getResultado());

            // Cerramos la comunicación con el cliente
            socketCliente.close();
            
            // Cerramos flujos de entrada y salida
            flujoEscrituraCliente.close();
            flujoEntradaCliente.close();

        } catch (SocketException ex) {
            System.out.printf("Error de socket: %s\n", ex.getMessage());
        } catch (IOException ex) {
            System.out.printf("Error de E/S: %s\n", ex.getMessage());
        }

         System.out.printf(
                "Hilo servidor %d: Fin de la conexión con el cliente.\n", this.numCliente);

    }
}
