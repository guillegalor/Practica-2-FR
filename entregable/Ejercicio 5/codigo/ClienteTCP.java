package ejercicio5;

/**
 *
 * @author johanna
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClienteTCP{
  
    
  public static void main(String[] args) {
		
		String buferEnvio;
		String buferRecepcion;
		int bytesLeidos=0;
                
                String entradaTeclado;
		Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
                    
                try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			socketServicio = new Socket(host,port); 
			//////////////////////////////////////////////////////			
			
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
			
                        do{
                            //Pedimos el Menu de opciones
                             buferRecepcion = inReader.readLine();
                             
                             if(buferRecepcion.equals("-1"))
                                 break;
                            //////////////////////////////////////////////////////
                            System.out.println(buferRecepcion);
                            //////////////////////////////////////////////////////
			
                            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
			
                            outPrinter.println(entradaTeclado);
                            
                            buferRecepcion = inReader.readLine();
                            System.out.println(buferRecepcion);
                             
                            if(buferRecepcion.equals("-1"))
                                 break;
                            
                        }while(true);
			
			
			//////////////////////////////////////////////////////
			socketServicio.close();
			//////////////////////////////////////////////////////
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
