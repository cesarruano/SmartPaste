package smartpaste;

import java.util.List;

public class SmartPaste {
    public static void sample(){

        List<Token> tokens = Tokenizer.Tokenize("DDD1");
        tokens = Tokenizer.Tokenize("asdf1234fda[asdf}{}{asdf");
        String s = "";
        for(Token t : tokens){s += "Lex: "+t.lex + " Kind: "+t.kind.toString();}
        //System.out.println("Tokenized asdf1234fda[asdf}{}{asdf  : "+s);

        //System.out.println("number from a91234 is "+Tokenizer.getNumber("a91234"));
        //System.out.println("number from 91234adfasd is "+Tokenizer.getNumber("91234adfasd"));

        //System.out.println("alpha from alsdjf91234 is "+Tokenizer.getAlpha("alsdjf91234"));
        //System.out.println("alpha from 91234adfasd is "+Tokenizer.getAlpha("91234adfasd"));

        //System.out.println("mixer from alsdjf[][. is "+Tokenizer.getMixer("alsdjf[][."));
        //System.out.println("mixer from 91234ad[].fasd is "+Tokenizer.getMixer("91234ad[].fasd"));

        String prev = "this goes before the selection, fdsa85";
        String selection = "asdf95,";

        Pattern p = Pattern.Match(prev, selection);
        if(p != null){
            for(Progression prog : p.progressions){
                //System.out.println("Progression "+prog.current+" with increment "+prog.progression);
            }
            for(int i = 0; i < 100; i ++)
                System.out.println(p.getNext());
        } else {
            p = Pattern.getDefaultPattern(selection);
            for(Progression prog : p.progressions){
                System.out.println("Progression "+prog.current+" with increment "+prog.progression);
            }
            for(int i = 0; i < 100; i ++)
                System.out.println(p.getNext());
        }
        
        //System.out.println(getNext("asdf", "fdsa8"));
    }
    
/*
    public static void main(String args[]){
    	sample();
    }
    */
    public static String getNext(String base, String selection){
        Pattern p = Pattern.Match(base, selection);
        if(p != null){
            return p.getNext();
        } else {
        	p = Pattern.getDefaultPattern(selection);
            return p.getNext();
        }
    }
}
