package smartpaste;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public static List<Token> Tokenize(String input){
        String c = input;
        ArrayList<Token> tokens = new ArrayList<Token>();
        while (c.length() > 0){
            String num = getNumber(c);
            String alpha = getAlpha(c);
            String mixer = getMixer(c);
            if(num.length() != 0){
                c = c.substring(0, c.length()-num.length());
                tokens.add(new Token(Token.Kind.NUM, num));
                //System.out.println("Kind: NUM"+" Lex:"+num);
            } else if(alpha.length() != 0){
                c = c.substring(0, c.length()-alpha.length());
                tokens.add(new Token(Token.Kind.ALPHA, alpha));
                //System.out.println("Kind: ALPHA"+" Lex:"+alpha);
            } else if(mixer.length() !=0){
                c = c.substring(0, c.length()-mixer.length());
                tokens.add(new Token(Token.Kind.MIXER, mixer));
                //System.out.println("Kind: MIXER"+" Lex:"+mixer);
            } else {
                c = "";
            }
        }

        return tokens;
    }

    public static String getNumber(String input){
        if(input.length() == 0) {
            return "";
        } else {
            char c = input.charAt(input.length()-1);
            if(c >= '0' && c <= '9'){
                return getNumber(input.substring(0, input.length()-1))+input.substring(input.length()-1);
            } else {return "";}
        }
    }

    public static String getAlpha(String input){
        if(input.length() == 0) {
            return "";
        } else {
            char c = input.charAt(input.length()-1);
            if(false && ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))){
                return getAlpha(input.substring(0, input.length()-1))+input.substring(input.length()-1);
            } else {return "";}
        }
    }

    public static String getMixer(String input){
        if(input.length() == 0) {
            return "";
        } else {
            char c = input.charAt(input.length()-1);
            //if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) && !(c >= '0' && c <= '9')){
            if(!(c >= '0' && c <= '9')){
                return getMixer(input.substring(0, input.length()-1))+input.substring(input.length()-1);
            } else {return "";}
        }
    }
}
