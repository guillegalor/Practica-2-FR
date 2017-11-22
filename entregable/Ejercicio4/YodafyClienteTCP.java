package practica2;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) {
		
		byte[] buferEnvio;
		byte[] buferRecepcion = new byte[256];
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
                
		// Puerto en el que espera el servidor:
		int port=10000;
                InetAddress direccion;
                DatagramPacket paquete;
                DatagramSocket socket;
                
                try {
			//////////////////////////////////////////////////////
			socket = new DatagramSocket(); 
			//////////////////////////////////////////////////////	
                        
                        direccion = InetAddress.getByName(host);
                        
                        buferEnvio="Al monte del volcán debes ir sin demora".getBytes();
                        
                        paquete = new DatagramPacket(buferEnvio, buferEnvio.length, direccion, port);
                        
                        socket.send(paquete);
                        
                        paquete = new DatagramPacket(buferRecepcion, buferRecepcion.length);
                        socket.receive(paquete);
                        
                        String mensajeRecibido = new String(paquete.getData(), paquete.getOffset(), paquete.getLength());
                        
                        // Mostremos la cadena de caracteres recibidos:
			System.out.println("Recibido: " + mensajeRecibido);
						
			socket.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}

