package smartpaste;

import java.util.ArrayList;
import java.util.List;


public class Pattern {
    public List<Token> tokens;
    public List<Progression> progressions;
    public String mixer;

    public static Pattern Match(String previousText, String selected){
        Pattern p = new Pattern();
        p.tokens = Tokenizer.Tokenize(selected);
        List<Token> rev = new ArrayList<Token>();
        p.mixer = "";
        p.progressions = new ArrayList<Progression>();
        /*Try to match backwards the previous text*/
        for(Token t : p.tokens){
            if(t.kind.equals(Token.Kind.ALPHA)){
                String alpha = Tokenizer.getAlpha(previousText);
                //System.out.println("Alpha expected "+t.lex+" found "+alpha);
                previousText = previousText.substring(0, previousText.length()-alpha.length());
                if(alpha.length() == 0){
                	//System.out.println("Alpha expected to match "+t.lex); 
                	return null;}
                else{p.progressions.add(new AlphaProgression(t.lex, Token.Kind.ALPHA, 0));}
            } else if(t.kind.equals(Token.Kind.NUM)){
                String num = Tokenizer.getNumber(previousText);
                //System.out.println("Num expected "+t.lex+" found "+num);
                previousText = previousText.substring(0, previousText.length()-num.length());
                if(num.length() == 0){
                	//System.out.println("Num expected to match "+t.lex); 
                	return null;}
                else{p.progressions.add(new NumericProgression(t.lex, Token.Kind.NUM, NumericProgression.getProgression(num, t.lex)));}
            } else {
                String mix = Tokenizer.getMixer(previousText);
                //System.out.println("Mixer expected "+t.lex+" found "+mix);
                previousText = previousText.substring(0, previousText.length()-mix.length());
                if(mix.length() == 0){
                	//System.out.println("Mixer expected to match "+t.lex);
                	return null;}
                else{p.progressions.add(new MixerProgression(t.lex, Token.Kind.MIXER, 0));}
            }
        }
        return p;

    }

    public static Pattern getDefaultPattern(String selected){
        Pattern p = new Pattern();
        p.tokens = Tokenizer.Tokenize(selected);
        p.progressions = new ArrayList<Progression>();
        for(Token t : p.tokens){
            if(t.kind.equals(Token.Kind.ALPHA)){
                p.progressions.add(new AlphaProgression(t.lex, Token.Kind.ALPHA, 0));
            } else if(t.kind.equals(Token.Kind.NUM)){
                p.progressions.add(new NumericProgression(t.lex, Token.Kind.NUM, 1));
            } else {
                p.progressions.add(new MixerProgression(t.lex, Token.Kind.MIXER, 0));
            }
        }
        p.mixer = "";
        return p;
    }

    public String getNext(){
        String result = "";
        for(Progression p : this.progressions){
            result = p.getNext()+result;
        }
        return mixer+result;
    }
}
