package smartpaste;

public class MixerProgression extends Progression{

    public MixerProgression(String start, Token.Kind kind, int progression){
        super(start, kind, progression);
    }

    protected void increment(){
    }

    public static int getProgression(String first, String second){
        return 0;
    }
}
