package smartpaste;

public class NumericProgression extends Progression{

    public NumericProgression(String start, Token.Kind kind, int progression){
        super(start, kind, progression);
    }

    protected void increment(){
        int n = Integer.parseInt(this.current);
        n += this.progression;
        String ns = Integer.toString(n);
        int padding = this.current.length() - ns.length();
        for(int i = 0; i < padding; i++){
            ns = "0"+ns;
        }
        this.current = ns;
    }

    public static int getProgression(String first, String second){
        return Integer.parseInt(second) - Integer.parseInt(first);
    }
}
