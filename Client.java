
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import javax.crypto.*;
import java.util.*;

public class Client
{
 
    private static Socket socket;
 
    public static void main(String args[])
	
    {
		
		
		
		
		
        try
        {    
            String host = "localhost";
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
			
			int cho;
			Scanner sin=new Scanner(System.in);
			System.out.println("Enter choice 1.Encryption 2.Decryption\n");
			cho=sin.nextInt();
            //Send the message to the server
		
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
			
			FileInputStream in = null;
            FileOutputStream out = null; 
            
			 KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();
             Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");;
            Path ipath=Paths.get("input.txt");
			
			byte[] textEncrypted={};
       
            
         in = new FileInputStream("input.txt");
         out = new FileOutputStream("output.txt");
		 
		  InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
			
			String sendMessage;
			
			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
           byte[] text = Files.readAllBytes(ipath);
            textEncrypted = desCipher.doFinal(text);
			  out.write(textEncrypted);
		 
		 switch(cho)
		 {
			 case 1:
        
          
         //desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
          
         
		   
           System.out.println("done Encryption");
	
			
			
			FileInputStream f= new FileInputStream("output.txt");
			
			int ch;
			 String lines = "";
			while((ch=f.read())!=-1)
			{
				
				lines=lines+(char)ch;
				
			}
			
			System.out.println("Encrypted Message:\t"+lines);
            sendMessage = lines+"\n";
            bw.write(sendMessage);
            bw.flush();
           System.out.println("Message sent to the server : "+sendMessage);
			
			
 
            //Get the return message from the server
           
            String message = br.readLine();
            System.out.println("Message received from the server : " +message);
			
			break;
			case 2:
			
			
			sendMessage = "two\n";
            bw.write(sendMessage);
            bw.flush();
           System.out.println("Message sent to the server : "+sendMessage);
			
			
			 FileOutputStream decypher =  new FileOutputStream("decypher.txt");
			 final PrintStream printStream = new PrintStream(decypher);
            
			 	message = br.readLine();
			 	 printStream.print(message);
                printStream.close();
			 	
            System.out.println("Message received from the server : " +message);
			 	
			 		 decypher =  new FileOutputStream("decypher.txt");
			 desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
             byte[] textDecrypted = desCipher.doFinal(textEncrypted);
             decypher.write(textDecrypted);
             
             System.out.println("done decryption");
             
			f= new FileInputStream("decypher.txt");
			
			
		
			ch=0;
		     lines = "";
			while((ch=f.read())!=-1)
			{
				
				lines=lines+(char)ch;
				
			}
			System.out.println("Decrypted Message:\t"+lines+"\n");
			break;
			
			case 3:
			System.exit(0);
			
			default:
			System.out.println("wrong");
			
        }
		}
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
