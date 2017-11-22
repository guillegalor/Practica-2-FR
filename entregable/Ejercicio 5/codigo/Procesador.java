/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 *
 * @author johanna
 */
public class Procesador extends Thread{
        String newline = System.getProperty("line.separator");
        // Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
        // objeto para encriptar y desencriptar los mensajes
        private Cipher cipher;
	
        // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public Procesador(Socket socketServicio) {
		this.socketServicio=socketServicio;
	
	}
        
        // Aquí es donde se realiza el procesamiento realmente:
        @Override
	public void run(){
		Command command;
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		String datosRecibido;
                String usuario;
		int bytesRecibidos=0;
		int option;
                boolean correcto = true;
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		byte [] datosEnviar;
		
		
		try {
                    // Obtiene los flujos de escritura/lectura
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
			
                        
			//Mostramos el menu
			do{
                            outPrinter.println("Menu de opciones: (1)Registrarse (2)Encriptar (3)Desencriptar (-1)Salir");
                             datosRecibido = inReader.readLine();
                             option = Integer.parseInt(datosRecibido);
                             
                             
                                  
                        switch(option){
                            case 1:
                               
                                do{
                                    if(correcto)
                                      outPrinter.println("OK");
                                    else
                                      outPrinter.println("ERROR");
                                    
                                    outPrinter.println("Introduzca el login que quiere:");
                                    usuario = inReader.readLine();
                                   
                                    correcto = usuarioNoReg(usuario);
                                }while(!correcto);
                                
                                outPrinter.println("OK");
                                outPrinter.println("Introduzca el password:");
                                datosRecibido = inReader.readLine();
                                registrar(usuario, datosRecibido);
                                outPrinter.println("REGOK");
                                
                            break;
                            
                            case 2:                                
                                do{
                                    if(correcto)
                                      outPrinter.println("OK");
                                    else
                                      outPrinter.println("ERROR");
                                    
                                    outPrinter.println("Introduzca el usuario al que le quiere enviar el mensaje encriptado:");
                                    usuario = inReader.readLine(); 
                                    correcto = !usuarioNoReg(usuario);
                                }while(!correcto);
                                
                                outPrinter.println("OK");
                                outPrinter.println("Introduzca el mensaje que le quiere encriptar:");
                                datosRecibido = inReader.readLine();
                                String mensaje = encriptar(usuario,datosRecibido);
                                outPrinter.println(mensaje);
                                
                            break;
                            
                            case 3:
                                String login, passwd;
                                do{
                                    if(correcto)
                                      outPrinter.println("OK");
                                    else
                                      outPrinter.println("ERROR");
                                    outPrinter.println("Login: ");
                                    login = inReader.readLine();
                                    outPrinter.println("\nPassword: ");
                                    passwd = inReader.readLine();
                                    
                                    correcto = loginCorrecto(login, passwd);
                                }while(!correcto);
                                
                                outPrinter.println("OK");
                                outPrinter.println("Introduzca el mensaje que le quiere desencriptar:");
                                datosRecibido = inReader.readLine();
                                mensaje = desencriptar(login, datosRecibido);
                                outPrinter.println(mensaje);
                                    
                            break;
                            
                            default:
                                option = -1;
                        }
                        
                        
                        }while(option != -1);
                        
                        
                  
                        outPrinter.println("-1");
		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}

	}

  
        private boolean usuarioNoReg(String login){
            boolean correcto = true;
            try {
                FileInputStream fstream = new FileInputStream("logins.data");
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                //Read File Line By Line
                while (correcto && (strLine = br.readLine()) != null)   {
                    if (login.equals(strLine.split(" ")[0]))
                        correcto = false;
                }
            } catch (FileNotFoundException ex) {} catch (IOException ex) {}
            return correcto;
        }
     
        private String encriptar(String usuario, String mensaje){
            String encrypted_msg = new String();
            try{
                AsymmetricCryptography ac = new AsymmetricCryptography();
                PublicKey publicKey = ac.getPublic("KeyPair/"+usuario+".pub");
            
                encrypted_msg = ac.encryptText(mensaje, publicKey);
            
            }catch(Exception e){
                encrypted_msg = "ERROR";
            }
            
            return encrypted_msg;
        }
        
        private String desencriptar(String login, String mensaje){
            String decrypted_msg = new String();
            try{
                AsymmetricCryptography ac = new AsymmetricCryptography();
                PrivateKey privateKey = ac.getPrivate("KeyPair/"+login+".priv");
            
                decrypted_msg = ac.decryptText(mensaje, privateKey);
            
            }catch(Exception e){
                decrypted_msg = "ERROR";
            }
            
            return decrypted_msg;                
        }
        
        private void registrar(String login, String passwd){
            if (usuarioNoReg(login)) {
                try{
                    //Creamos la entrada en el archivo de logins
                    FileWriter fstream = new FileWriter("./logins.data", true);
                    fstream.write(newline + login + " " + passwd );
                    fstream.close();
                    
                    //Generamos su clave pública y privada
                    GenerateKeys gk;
                    gk = new GenerateKeys(1024);
                    gk.createKeys();
                    gk.writeToFile("KeyPair/" + login + ".pub", gk.getPublicKey().getEncoded());
                    gk.writeToFile("KeyPair/" + login +".priv", gk.getPrivateKey().getEncoded());
		
                } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
                } catch (Exception e){}
                
            }
            
        }
        
        private boolean loginCorrecto(String login, String passwd){
            System.out.println("Funcion loginCorrecto");
            boolean correcto = false, found = false;
            try {
                FileInputStream fstream = new FileInputStream("logins.data");
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                //Read File Line By Line
                while (!found && (strLine = br.readLine()) != null)   {
                    if (login.equals(strLine.split(" ")[0])){
                        found = true;
                        System.out.println(strLine);
                        if(passwd.equals(strLine.split(" ")[1]))
                            correcto = true;
                    }
                }                        
            } catch (FileNotFoundException ex) {} catch (IOException ex) {}
            return correcto;
        }
}
