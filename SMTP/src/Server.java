
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
	private ServerSocket server;
	private int porta;
	private  Socket clientSocket;
	private int numCon;
	private ArrayList<Messaggio> listaMessaggi = new ArrayList<Messaggio>();

	
	public Server(int porta) {
		super();
		this.porta = porta;//porta su cui in server sar� in listen
		clientSocket=null;//socket connesso al client
		numCon=0;	
	}
	
	public void avvia() {
		
		try {
			server=new ServerSocket(porta);//server il listen sulla porta specificata
			
			while(true) {
				System.out.println("In attesa di connessioni...");
				
				//Con il metodo accept() il server rimane in attesa di connessioni
				//(la chiamata ad accept() � blccante, 
				//ossia l'esecuzione rimane bloccata alla riga 40 finch� un client 
				// non si connette al server)
				//accept() restituisce un oggetto di tipo Socket,
				//Quando un client si connette al server viene creato un nuovo socket 
				//Questo socket verr� passato al Thread di gestione del client, che si occuper� dello 
				//scambio di messaggi tra server e client.
				
				
				clientSocket = server.accept();
				numCon++;
				System.out.println("Numero di connessioni: "+numCon);
				ThreadGestioneClient t = new ThreadGestioneClient (clientSocket);
                t.start();//avvia il Thread
                
                
                //aspetta un centesimo di secondo tra una connessione e l'altra
                
                try {
					Thread.sleep(10);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
              //adesso il server è pronto per altre connessioni. Altri client possono connettersi
              //La comunicazione tra i diversi client ed il server verr� gestita in parallelo dai Thread creati alla riga 45
                
                 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Impossibile avviare il server sulla porta "+porta);
			System.exit(1);
			//e.printStackTrace();
		}
	}

	
}
