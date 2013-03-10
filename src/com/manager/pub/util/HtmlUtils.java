package com.manager.pub.util;

public class HtmlUtils {

	public static String encodeHtml(String value) {
		if ((value == null) || (value.length() == 0)) {
			return value;
		}
		StringBuffer result = null;
		String filtered = null;
		for (int i = 0; i < value.length(); ++i) {
			filtered = null;
			switch (value.charAt(i)) {
			case '<':
				filtered = "&lt;";
				break;
			case '>':
				filtered = "&gt;";
				break;
			case '&':
				filtered = "&amp;";
				break;
			case '"':
				filtered = "&quot;";
				break;
			case '\'':
				filtered = "&#39;";
			}

			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
					if (i > 0) {
						result.append(value.substring(0, i));
					}
					result.append(filtered);
				}

			} else if (filtered == null) {
				result.append(value.charAt(i));
			} else {
				result.append(filtered);
			}
		}

		return ((result == null) ? value : result.toString());
	}

	public static String decodeHtml(String value) {
		if ((value == null) || (value.length() == 0)) {
			return value;
		}
		StringBuffer result = new StringBuffer(value.length());
		String v = null;

		int index = 0;
		while (index < value.length()) {
			char c = value.charAt(index);
			if (c == '&') {
				v = value.substring(index);
				if (v.startsWith("&lt;")) {
					index += "&lt;".length();
					c = '<';
				} else if (v.startsWith("&gt;")) {
					index += "&gt;".length();
					c = '>';
				} else if (v.startsWith("&amp;")) {
					index += "&amp;".length();
					c = '&';
				} else if (v.startsWith("&quot;")) {
					index += "&quot;".length();
					c = '"';
				} else if (v.startsWith("&#39;")) {
					index += "&#39;".length();
					c = '\'';
				} else {
					++index;
				}
			} else {
				++index;
			}
			result.append(c);
		}
		return ((result == null) ? value : result.toString());
	}

	public static void main(String[] args) {
		String txt = "<html></html>";
		System.out.println(encodeHtml(txt));
		System.out.println(decodeHtml("&lt;html&gt;&lt;/html&gt;"));
	}

	public static String encodeHtml(String inStr, boolean allowLineFeed) {
		if ((inStr == null) || (inStr.length() == 0)) {
			return "";
		}
		StringBuffer htmlCode = new StringBuffer("");
		char[] charsSrc = { '\t', '\n', '\r', '\26', ' ', '&', '>', '<', '"' };
		String[] strsDest = { "&nbsp;&nbsp;&nbsp;&nbsp;",
				(allowLineFeed) ? "<BR>\n" : "<BR>", "", "", " ", "&amp;",
				"&gt;", "&lt;", "&quot;" };

		for (int i = 0; i < inStr.length(); ++i) {
			char cc = inStr.charAt(i);
			boolean replaceFlag = false;
			for (int j = 0; j < charsSrc.length; ++j) {
				if (cc == charsSrc[j]) {
					htmlCode.append(strsDest[j]);
					replaceFlag = true;
					break;
				}
			}
			if (!(replaceFlag)) {
				htmlCode.append(cc);
			}
		}
		return htmlCode.toString();
	}

	public static String addParam(String path, String key, String value) {
		if ((path == null) || (path.length() == 0))
			return path;

		StringBuffer bf = new StringBuffer(path);
		boolean isRoot = path.indexOf("?") >= 0;
		if (isRoot)
			bf.append("&");
		else
			bf.append("?");

		bf.append(key);
		bf.append("=");
		bf.append(value);
		return bf.toString();
	}
}