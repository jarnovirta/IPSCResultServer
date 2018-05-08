package fi.ipscResultServer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.stereotype.Component;

@Component
public class EncodeURITag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String encodeURI(String value) throws UnsupportedEncodingException {
	return URLEncoder.encode(value, "UTF-8");
	}
}
