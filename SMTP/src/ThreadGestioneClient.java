
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadGestioneClient extends Thread{
	private  Socket clientSocket;//socket connesso al client
	private ObjectInputStream in;//per leggere i dati dal socket
	private ObjectOutputStream out;//per scrivere dati sul socket
	private boolean connesso;
	private ArrayList<Messaggio> listaMessaggi;

	public ThreadGestioneClient(Socket clientSocket, ArrayList<Messaggio> listaMessaggi) {
		super();
		connesso=false;
		this.clientSocket = clientSocket;
		this.listaMessaggi = listaMessaggi;
		
		try {
			out=new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			connesso=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Impossibile creare gli stream di Input/Output");
			chiudiSocket();
		}   
	}
	
	
	//il metodo run andr� in esecuzione quando verr� invocato 
	//il metodo start sull'oggetto di tipo ThreadGestioneClient (vedi Server.java)
	@Override
    public void run()  {
       if(connesso) {
            try
            {
            	String risposta=(String) in.readObject();
            	System.out.println("Messaggio dal client: "+risposta);
            	if(risposta.indexOf("HELO server")!=0) {
    				System.out.println("Il client non ha inviato il messaggio \"HELO server...\"");
    				chiudiSocket();
    			}else {
        		// invia al cliente
    				out.writeObject("HELO client. Sei collegato al server."); 		
    				attendiRisposta();
    			}
            } catch (IOException | ClassNotFoundException ex) {
                	ex.printStackTrace();
            }
            finally
            {
            	chiudiSocket();   
            }
        
       }
       else {
    	   chiudiSocket();
       }
        
    }
	
	private void attendiRisposta() {
        try {
            boolean fine = false;
            while (!fine) {
                Messaggio messaggio = (Messaggio) in.readObject();
                System.out.println("Oggetto ricevuto dal client: " + messaggio);
				aggiungiMessaggio(messaggio);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            chiudiSocket();
        }
    }
	
	
	private void chiudiSocket() {
		System.out.println("Chiudo il socket.");
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problemi nella chiusura del socket");
		}
	}

	private void aggiungiMessaggio(Messaggio m) {
		synchronized (listaMessaggi) {  // Sincronizzazione per evitare problemi di concorrenza
			listaMessaggi.add(m);
		}
	}
	
}
