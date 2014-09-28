package com.zhongxun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.zhongxun.luceneUtil.Indexer;

public class AddIndexServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		System.out.println(request.getParameter("filepath"));
//		Map<String, String> fieldsContainer = new HashMap<String, String>(); 
//		fieldsContainer.put("title", request.getParameter("title"));
//		fieldsContainer.put("creator", request.getParameter("creator"));
//		fieldsContainer.put("subject", request.getParameter("subject"));
//		fieldsContainer.put("description", request.getParameter("description"));
//		fieldsContainer.put("publisher", request.getParameter("publisher"));
//		fieldsContainer.put("date", request.getParameter("date"));
//		fieldsContainer.put("type", request.getParameter("type"));
//		fieldsContainer.put("source", request.getParameter("source"));
//		fieldsContainer.put("language", request.getParameter("language"));
//		fieldsContainer.put("rights", request.getParameter("rights"));
//		
		Indexer indexer = new Indexer("d:/lucene/localindex");
		File file = null;
		Document doc = null;
		String info = "success";
		try {
			 file = new File(request.getParameter("filepath"));
			 doc = indexer.getDocument(file);
			 doc.add(new Field("title", request.getParameter("title"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("creator", request.getParameter("creator"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("subject", request.getParameter("subject"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("description", request.getParameter("description"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("publisher", request.getParameter("publisher"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new NumericField("date", Field.Store.YES, true).setIntValue(Integer.parseInt(request.getParameter("date"))));
//			System.out.println("date: "+Integer.parseInt(request.getParameter("date")));
			doc.add(new Field("type", request.getParameter("type"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("source", request.getParameter("source"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("language", request.getParameter("language"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			doc.add(new Field("rights", request.getParameter("rights"), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
			indexer.writeIndex(doc);
		} catch (Exception e) {
			e.printStackTrace();
			info = "failed" + e;
		} finally {
			indexer.close();
		}
		
		request.setAttribute("info", info);
		request.getRequestDispatcher("indexResult.jsp").forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
