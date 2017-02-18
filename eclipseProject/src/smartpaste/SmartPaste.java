package smartpaste;


public class SmartPaste {
    public static void sample(){

    }
    
    public static String getNext(String base, String selection){
    	Parser p = new Parser();
    	return p.getNext(base, selection);
    }
}
