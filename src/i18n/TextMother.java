package i18n;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class TextMother implements Serializable{

	private static final long serialVersionUID = 3181022600291753863L;

	public TextMother() {

	}

	private static final Logger LOGGER = Logger.getLogger(TextMother.class);

	private static final String BASE_NAME = "i18n.resources";
	
	public String getText(String language, String text) {
//		LOGGER.debug("call translation module lang= " + language + " " + text);
		ResourceBundle bundle = null;
		
		if (language==null || language==""){
			bundle=ResourceBundle.getBundle(BASE_NAME, new Locale("en"));
		}
		else{
			bundle=ResourceBundle.getBundle(BASE_NAME, new Locale(language));
		}
		String result=bundle.getString(text);
		return result;
	}

}
