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


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
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
