package smartpaste;

import java.util.regex.Pattern;

public class MagicToken {
	// backwards strings
	public static final String hex_regex = "(^[a-fA-F0-9]+[xX]0)(.*)";
	public static final String dec_regex = "(^[0-9]+)(.*)";
	public static final String txt_regex = "(^[^\\,\\;\\:\\ (\\r\\n|[\\r\\n])0-9]+)(.*)";
	public static final String sep_regex = "(^[\\,\\;\\:\\ (\\r\\n|[\\r\\n])]+)(.*)";
	
	public static Pattern hex_pat;
	public static Pattern dec_pat;
	public static Pattern txt_pat;
	public static Pattern sep_pat;

	public enum Kind {
		HEX, DEC, TXT, SEP
	};

	public Kind kind = null;
	public String text = "";

}
