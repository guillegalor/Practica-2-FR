package practica2;

import practica2.ProcesadorYodafy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;


//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo    {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=10000;
		
                
		try {
			DatagramSocket socketServicio = new DatagramSocket(port);
                        ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
                        
                        // Mientras ... siempre!
			do {	
				procesador.procesa();	
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
