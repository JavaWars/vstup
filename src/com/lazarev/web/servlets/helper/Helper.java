package com.lazarev.web.servlets.helper;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class Helper {
	public static String getJsonQuery(HttpServletRequest req) throws IOException{
		BufferedReader br = req.getReader();
		StringBuilder sb = new StringBuilder();
		String buf = null;
		while ((buf = br.readLine()) != null) {
			sb.append(buf);
			System.out.println(buf);
		}
		return sb.toString();
	}

}
