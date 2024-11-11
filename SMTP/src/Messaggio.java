public class Messaggio {
    private String from;
    private String to;
    private String header;
    private String body;

    public Messaggio(String from, String to, String header, String body) {
        this.from = from;
        this.to = to;
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Messaggio{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
