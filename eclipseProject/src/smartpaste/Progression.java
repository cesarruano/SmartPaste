package smartpaste;

public abstract class Progression {
    protected Token.Kind kind;
    protected String current;
    protected int progression;

    public Progression(String start, Token.Kind kind,int progression){
        this.current = start;
        this.progression = progression;
        this.kind = kind;
    }

    public String getNext(){
        increment();
        return this.current;
    }

     protected abstract void increment();

}