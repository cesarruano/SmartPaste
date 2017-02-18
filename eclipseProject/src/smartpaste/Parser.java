package smartpaste;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Parser {

	
	public final int TOKENIZE_ALL = -1;
	
	/*get the longest token*/
	public MagicToken Max4(MagicToken mt0, MagicToken mt1, MagicToken mt2, MagicToken mt3){
		if((mt0.text.length() >= mt1.text.length()) && (mt0.text.length() >= mt2.text.length()) && (mt0.text.length() >= mt3.text.length())) return mt0;
		if((mt1.text.length() >= mt2.text.length()) && (mt1.text.length() >= mt3.text.length())) return mt1;
		if(mt2.text.length() >= mt3.text.length()) return mt2;
		return mt3;
	}
	
	public String matchToken(String pat, String text){
		String result = "";
		Pattern pattern = Pattern.compile(pat, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(text);
		boolean isMatched = matcher.matches();
		if (isMatched)result = matcher.group(1);
		return result;
	}
	
	public MagicToken getToken(String s){

		MagicToken hex_tok = new MagicToken(); hex_tok.kind = MagicToken.Kind.HEX; 
		hex_tok.text = matchToken(MagicToken.hex_regex, s);
		MagicToken dec_tok = new MagicToken(); dec_tok.kind = MagicToken.Kind.DEC; 
		dec_tok.text = matchToken(MagicToken.dec_regex, s);
		MagicToken txt_tok = new MagicToken(); txt_tok.kind = MagicToken.Kind.TXT; 
		txt_tok.text = matchToken(MagicToken.txt_regex, s);
		MagicToken sep_tok = new MagicToken(); sep_tok.kind = MagicToken.Kind.SEP; 
		sep_tok.text = matchToken(MagicToken.sep_regex, s);
		
		return Max4(hex_tok, dec_tok, txt_tok, sep_tok);
	}
	
	public List<MagicToken> tokenize(String txt, int n){
		System.out.println("tokenize:"+txt+":"+n);
		List<MagicToken> list = new ArrayList<MagicToken>();
		while(txt.length() > 0 && n != 0){
			n--;
			MagicToken t = getToken(txt);
			list.add(t);
			System.out.println("Loop: "+(n+1)+" "+txt+":"+t.text+":"+t.kind.toString());
			if (t.text.length() == 0) return list;
			txt = txt.substring(t.text.length());
		}
		return list;
	}
	
	public MagicToken progressToken(MagicToken t1, MagicToken t2){
		if(t1 == null){
			if(t2.kind == MagicToken.Kind.DEC){
				int v1 =  Integer.parseInt(new StringBuilder(t2.text).reverse().toString());
				int v2 = v1+1;
				System.out.println("progress dec single");
				System.out.println(v1);
				System.out.println(v2);
				MagicToken mt = new MagicToken();
				mt.kind = MagicToken.Kind.DEC;
				mt.text = String.format( "%0"+t2.text.length()+"d", v2);
				mt.text = new StringBuilder(mt.text).reverse().toString();
				return mt;
			} else if (t2.kind == MagicToken.Kind.HEX){
				int v1 =  Integer.parseInt(new StringBuilder(t2.text).reverse().toString().substring(2), 16);
				int v2 = v1+1;
				System.out.println("progress hex single");
				System.out.println(v1);
				System.out.println(v2);
				MagicToken mt = new MagicToken();
				mt.kind = MagicToken.Kind.HEX;
				mt.text = String.format(( new StringBuilder(t2.text).reverse().toString().substring(0,2))+"%0"+(t2.text.length()-2)+"X",v2);
				
				mt.text = new StringBuilder(t2.text).reverse().toString().substring(0,2);
				String hex = String.format("%0"+(t2.text.length()-2)+"X", v2);
				if(t2.text.substring(0, t2.text.length()-2).equals(t2.text.substring(0, t2.text.length()-2).toUpperCase()) ){
					hex = hex.toUpperCase();
				} else {
					hex = hex.toLowerCase();
				}
				mt.text += hex;
				
				mt.text = new StringBuilder(mt.text).reverse().toString();
				return mt;
			} else {
				return t2;
			}
		}
		if(t1.kind != t2.kind) return null;
		if(t1.kind == MagicToken.Kind.DEC){
			int v1 =  Integer.parseInt(new StringBuilder(t1.text).reverse().toString());
			int v2 =  Integer.parseInt(new StringBuilder(t2.text).reverse().toString());
			int v3 = v2 + (v2-v1);
			System.out.println("progress dec");
			System.out.println(v1);
			System.out.println(v2);
			MagicToken mt = new MagicToken();
			mt.kind = MagicToken.Kind.DEC;
			mt.text = String.format( "%0"+t2.text.length()+"d", v3);
			mt.text = new StringBuilder(mt.text).reverse().toString();
			return mt;
		} else if (t1.kind == MagicToken.Kind.HEX){
			int v1 =  Integer.parseInt(new StringBuilder(t1.text).reverse().toString().substring(2), 16);
			int v2 =  Integer.parseInt(new StringBuilder(t2.text).reverse().toString().substring(2), 16);
			int v3 = v2 + (v2-v1);
			System.out.println("progress hex");
			System.out.println(v1);
			System.out.println(v2);
			MagicToken mt = new MagicToken();
			mt.kind = MagicToken.Kind.HEX;
			mt.text = new StringBuilder(t1.text).reverse().toString().substring(0,2);
			String hex = String.format("%0"+(t2.text.length()-2)+"X", v3);
			if(t1.text.substring(0, t1.text.length()-2).equals(t1.text.substring(0, t1.text.length()-2).toUpperCase()) || 
			   t2.text.substring(0, t2.text.length()-2).equals(t2.text.substring(0, t2.text.length()-2).toUpperCase()) ){
				hex = hex.toUpperCase();
			} else {
				hex = hex.toLowerCase();
			}
			mt.text += hex;
			mt.text = new StringBuilder(mt.text).reverse().toString();
			return mt;
		}
		return t2;
	}
	
	public List<MagicToken> progressLists(List<MagicToken> l1, List<MagicToken> l2){
		List<MagicToken> l = new ArrayList<MagicToken>();
		if(l1.size() != l2.size()) return null;
		for(int i = 0; i < l1.size(); i++){
			l.add(progressToken(l1.get(i), l2.get(i)));
		}
		return l;
	}
	
	public List<MagicToken> progressSingleList(List<MagicToken> l1){
		List<MagicToken> l = new ArrayList<MagicToken>();
		for(int i = 0; i < l1.size(); i++){
			l.add(progressToken(null, l1.get(i)));
		}
		return l;
	}
	
	public boolean matchStructure(List<MagicToken> l1, List<MagicToken> l2){
		if(l1.size() != l2.size()) {System.out.println("bad size");return false;}
		for(int i = 0; i < l1.size(); i++){
			System.out.println(l1.get(i).kind+"vs"+l2.get(i).kind);
			if(l1.get(i).kind != l2.get(i).kind) return false;
		}
		return true;
	}
	
	public String tokList2String(List<MagicToken> r){
		if( r==null || r.size()==0) return "";
		String text = "";
		for(MagicToken mt : r){
			text += mt.text;
		}
		return new StringBuilder(text).reverse().toString();
	}
	
	public String getNext(String base, String sel){
//		MagicToken.Init();
        base = new StringBuilder(base).reverse().toString();
        sel = new StringBuilder(sel).reverse().toString();
        
//		System.out.println(getToken("asdf134r14").kind.toString()+getToken("asdf134r14").text);
//		System.out.println(getToken("134r14").kind.toString()+getToken("134r14").text);
//		System.out.println(getToken("ff14x0asdf134r14").kind.toString()+getToken("ff14x0asdf134r14").text);
//		System.out.println(getToken(",,  adf").kind.toString()+getToken(",,  adf").text);
//		
//		for(MagicToken mt : tokenize("1231234123x0asdfadfa123,,asdfs", TOKENIZE_ALL)){
//			System.out.println(mt.kind.toString()+" - "+mt.text);
//		}
//		
		List<MagicToken> sel_toks = tokenize(sel, TOKENIZE_ALL);
		List<MagicToken> base_toks = tokenize(base, sel_toks.size()+1);
		
		System.out.println("Obtained tokens: selection");
		for(MagicToken mt : sel_toks){
			System.out.println(mt.kind.toString()+" - "+mt.text);
		}
		System.out.println("Obtained tokens: base");
		System.out.println("base:");
		System.out.println(base);
		System.out.println("end base:");
		for(MagicToken mt : base_toks){
			System.out.println(mt.kind.toString()+" - "+mt.text);
		}
		
		List<MagicToken> result = new ArrayList<MagicToken>();
		if(base_toks.size() >= sel_toks.size()){
			if(matchStructure(sel_toks, base_toks.subList(0, sel_toks.size()))){
				System.out.println("Match in number and kind");
				result = progressLists(base_toks.subList(0, sel_toks.size()), sel_toks);
				System.out.println("Generated tokens:");
				for(MagicToken mt : result){
					System.out.println(mt.kind.toString()+" - "+mt.text);
				}
			} else {
				System.out.println("Mismatch in kind of tokens");
				result = progressSingleList(sel_toks);
			}
		} else {
			System.out.println("Mismatch in number of tokens");
			result = progressSingleList(sel_toks);
		}
		
		return tokList2String(result);
	}
}
