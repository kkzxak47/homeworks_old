package com.zhongxun.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class DocAnalyzeServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String filePath = request.getParameter("filePath");
		File file = new File(filePath);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		Tika tika = new Tika();

		try {
			System.out.println("file format : " + tika.detect(bis));
			System.out.println("file content : " + tika.parseToString(bis));
			Metadata met = getMet(file);

			System.out.println(file);
			for (String name : met.names()) {
				for (String value : met.getValues(name)) {
					System.out.println(name + " : " + value);
				}
			}
		} catch (TikaException | SAXException e) {
			e.printStackTrace();
		}
	}

	public static Metadata getMet(File f) throws IOException, SAXException, TikaException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
		Metadata met = new Metadata();
		AutoDetectParser parser = new AutoDetectParser();
		parser.parse(bis, new BodyContentHandler(), met, new ParseContext());
		return met;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
