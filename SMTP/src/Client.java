import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	private Socket client;//socket per la connesione al Server
	private String ip;//ip server (localhost per i test in locale)
	private int porta;//porta su cui il server � in listen
	private ObjectInputStream in;//oggetto per leggere dati dal socket
	private ObjectOutputStream out;//oggetto per scrivere dati sul socket
	private Messaggio messaggio;
	private boolean con = false;
	
	public Client(String ip, int porta) {
		super();
		
		this.ip = ip;
		this.porta = porta;
		in=null;
		out=null;
		
		connetti();

		messaggio = new Messaggio(null,null,null,null);
		
	}

	public boolean isConnected(){
		return con;
	}

	public void setCon(boolean con) {
		this.con = con;
	}

	private void connetti() {
		try {
			//connetti il client al server
			client=new Socket(ip,porta);
			out=new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			System.out.println("\nDopo essermi connesso al server invio il messaggio HELO server");
			out.writeObject("HELO server");//primo messaggio al server
			String rispostaServer=(String)in.readObject();//risposta
			System.out.println("Messaggio  dal server: "+rispostaServer);
			if(rispostaServer.indexOf("HELO client")!=0) {
				System.out.println("Il server non ha inviato il messaggio \"HELO client...\"");
				chiudiSocket();
			}else{
				con = true;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problemi di apertura del socket");
			chiudiSocket();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problemi nella ricezione dell'oggetto ricevuto dal server");
			chiudiSocket();
		}
		
	}
	
	private void chiudiSocket() {
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problemi nella chiusura del socket");
			System.exit(1);
		}
	}

	public Messaggio getMessaggio() {
		return messaggio;
	}

	public void comunica() {
        Scanner scanner = new Scanner(System.in);
        String messaggio;

        try {
            while (true) {
                System.out.print("Inserisci un messaggio da inviare al server ('FINE' per terminare): ");
                messaggio = scanner.nextLine();
                out.writeObject(messaggio);

                if (messaggio.equalsIgnoreCase("FINE")) {
                    System.out.println("Terminazione della connessione...");
                    break; // Esce dal ciclo e si prepara a chiudere il socket
                }

                String risposta = (String) in.readObject(); // Legge la risposta dal server
                System.out.println("Risposta ricevuta dal server: " + risposta);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            chiudiSocket();
        }
    }

	void mailFrom(String s){
		messaggio.setFrom(s);
	}

	void mailTo(String s){
		messaggio.setTo(s);
	}

	void mailHeader(String s){
		messaggio.setHeader(s);
	}

	void mailBody(String s){
		messaggio.setBody(s);
	}
}
