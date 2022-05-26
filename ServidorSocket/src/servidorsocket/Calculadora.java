package servidorsocket;

/**
 * Título:      Calculadora
 * Descripción: Clase que gestiona las operaciones básicas de una calculadora
 * @author      David Jiménez Riscardo
 * @version     1.0
 */
public class Calculadora {

    private double num1;
    private double num2;
    private char operacion;
    private String resultado;

    /**
     * Método constructor 
     * @param num1 Número decimal
     * @param num2 Número decimal
     * @param operacion Operación a realizar: sumar, restar, multiplicar o dividir 
     */
    public Calculadora(double num1, double num2, char operacion) {
        this.num1 = num1;
        this.num2 = num2;
        this.operacion = operacion;
        calcularResultado();
    }

    /**
     * Método para el cálculo del resultado
     */
    private void calcularResultado() {
        switch (operacion) {
            case '+':
                sumar();
                break;
            case '-':
                restar();
                break;
            case '*':
                multiplicar();
                break;
            case '/':
                dividir();
                break;
            default:
                resultado = "Operación no permitida. Los operadores válidos son ('+','-','*','/').";

        }
    }

 
    private void sumar() {
        resultado = num1 + " + " + num2 + " = " + (num1 + num2);
    }

    private void restar() {
        resultado = num1 + " - " + num2 + " = " + (num1 - num2);
    }

    private void multiplicar() {
        resultado = num1 + " * " + num2 + " = " + (num1 * num2);
    }

    private void dividir() {
        if (num2 == 0) {
            resultado = "No se puede dividir entre cero";
        } else {
            resultado = num1 + " / " + num2 + " = " + (num1 / num2);
        }
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public char getOperacion() {
        return operacion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public void setOperacion(char operacion) {
        this.operacion = operacion;
    }
}
