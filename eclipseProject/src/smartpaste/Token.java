package smartpaste;

public class Token {
    public enum Kind {NUM, ALPHA, MIXER};

    public Kind kind;
    public String lex;

    public Token(Kind k, String l){
        this.kind = k;
        this.lex = l;
    }
}
