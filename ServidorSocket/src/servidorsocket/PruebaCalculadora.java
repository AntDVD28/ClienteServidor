package servidorsocket;

/**
 * Título:      PruebaCalculadora
 * Descripción: Clase para probar el funcionamiento de la clase Calculadora
 * @author      David Jiménez Riscardo
 * @version     1.0
 */
public class PruebaCalculadora {
        /**
         * Programa principal
         * @param args Valores enviados por la línea de comandos, sin uso.
         */
        public static void main(String[] args){
            Calculadora c = new Calculadora(45.4,2.5,'/');
            String resultado = c.getResultado();
            System.out.println("RESULTADO OBTENIDO DE LA CLASE CALCULADORA");
            System.out.println("==========================================");
            System.out.println(resultado);
        }
}
