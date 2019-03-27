package com.lazarev.web.servlets.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import com.lazarev.web.Constants;

public class ImgConverter {
	
	private static final Logger LOGGER=Logger.getLogger(ImgConverter.class);
	
	public static String fileNameTo64BaseData(String file) {
		Path path = Paths.get(Constants.IMG_HOME + file);
		String result = null;
		try {
			String data = new String(Base64.encodeBase64(Files.readAllBytes(path)), "UTF-8");
			result = "data:image/jpeg;base64," + data;
		} catch (IOException e) {
			LOGGER.error("can't prepare img file", e);
		}
		return result;
	}
	
	public static boolean checkIsDiplomImageExistForUser(String userId) {
		
		File f=new File(Constants.IMG_HOME + userId);
		return f.exists();
	}
}
