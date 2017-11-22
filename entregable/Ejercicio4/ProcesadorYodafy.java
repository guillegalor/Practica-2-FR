package practica2;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramSocket socket;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
        
        // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramSocket socketServicio) {
		this.socket=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	public void procesa(){
		String datosRecibido;
		
                DatagramPacket paquete, paqueteOut;
                byte[] bufer = new byte[256];
		
		
		try {
                        paquete = new DatagramPacket(bufer, bufer.length);
                        socket.receive(paquete);
			
			// Lee la frase a Yodaficar:
			////////////////////////////////////////////////////////
                        datosRecibido = new String(paquete.getData(), paquete.getOffset(), paquete.getLength());
			////////////////////////////////////////////////////////
			
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(datosRecibido);
			
			// Enviamos la traducción de Yoda
                        paqueteOut = new DatagramPacket(respuesta.getBytes(), respuesta.getBytes().length, paquete.getAddress(), paquete.getPort());
                        
                        socket.send(paqueteOut);
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

        // Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
