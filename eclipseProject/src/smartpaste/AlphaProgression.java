package smartpaste;

public class AlphaProgression extends Progression{

    public AlphaProgression(String start, Token.Kind kind, int progression){
        super(start, kind, progression);
    }

    protected void increment(){
    }

    public static int getProgression(String first, String second){
        return 0;
    }
}
