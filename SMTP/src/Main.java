import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //Client mittente e server a cui si collega
        Client clientMittente = new Client("172.16.254.1",25);
        Server server1 = new Server(25);

        //Client destinatari e server a cui si collegano
        Server server2 = new Server(25);
        Client clientDestinatarioUno = new Client("192.158.1.1",25);
        Client clientDestinatarioDue = new Client("192.158.1.2",25);




        Scanner scanner = new Scanner(System.in);
        while (clientMittente.isConnected()) {

            stampaListaComandi();
            System.out.println("Inserisci un comando (MAIL FROM, RCPT TO, SUBJECT, DATA, LIST, QUIT): ");
            String comando = scanner.nextLine();

            switch (comando.toUpperCase()) {

                case "MAIL FROM":

                    System.out.print("Inserisci l'indirizzo del mittente: ");
                    String mittente = scanner.nextLine();
                    clientMittente.mailFrom(mittente);
                    break;

                case "RCPT TO":

                    if(clientMittente.getMessaggio().getFrom() == null){
                        System.out.print("Inserisci prima l'indirizzo del mittente (MAIL FROM)");
                    }else {
                        System.out.print("Inserisci l'indirizzo del destinatario: ");
                        String destinatario = scanner.nextLine();
                        clientMittente.mailTo(destinatario);
                    }

                    break;

                    //DATA richiede sia il subject che il contenuto del messaggio
                case "DATA":

                    if(clientMittente.getMessaggio().getFrom() == null || clientMittente.getMessaggio().getTo() == null){
                        System.out.print("Inserisci prima l'indirizzo del mittente (DATA)");
                    }else{
                        System.out.print("Inserisci l'oggetto del messaggio: ");
                        String oggetto = scanner.nextLine();
                        clientMittente.mailHeader(oggetto);

                        System.out.println("Inizia a scrivere il messaggio di posta. Digita una linea vuota per terminare.");
                        StringBuilder messaggio = new StringBuilder();
                        String linea;
                        while (!(linea = scanner.nextLine()).isEmpty()) {
                            messaggio.append(linea).append("\n");
                        }
                        clientMittente.mailBody(messaggio.toString());

                    }

                    break;

                case "LIST":
                    inviaComando("LIST");
                    break;

                case "QUIT":
                    inviaComando("QUIT");
                    clientMittente.setCon(false); // Termina il loop
                    break;

                default:
                    System.out.println("Comando non riconosciuto.");
                    stampaListaComandi();
                    break;
            }
        }
        chiudiSocket();
        scanner.close();

    }
    static void invia(String comando) {

    }
    static void stampaListaComandi(){
        System.out.println("-----Comandi-----");
        System.out.println("MAIL FROM: specifica l'indirizzo del mittente");
        System.out.println("RCPT TO: specifica l'indirizzo del destinatario");
        System.out.println("DATA: indica l'inizio del messaggio di posta");
        System.out.println("LIST: il server mostra la lista di mail salvate");
        System.out.println("QUIT: termina la connessione tra client e server");
    }

}