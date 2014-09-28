package com.zhongxun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ShowIndexServlet extends HttpServlet {

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
		System.out.println("showIndex");
		request.setCharacterEncoding("utf-8");
		
		int docID = Integer.parseInt(request.getParameter("docID"));
		
//		String path = "C:/solr_index/index";
		String path = "E:/信息检索课作业/web_data/index";
		
		Directory dir = FSDirectory.open(new File(path));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher issearcher = new IndexSearcher(reader);
		
		Document result = issearcher.doc(docID);
		
		System.out.println(result.get("anchor"));
		System.out.println(result.get("boost"));
		System.out.println(result.get("cache"));
		System.out.println(result.get("content"));
		System.out.println(result.get("digest"));
		System.out.println(result.get("host"));
		System.out.println(result.get("id"));
		System.out.println(result.get("segment"));
		System.out.println(result.get("title"));
		System.out.println(result.get("tstamp"));
		System.out.println(result.get("url"));
		
		
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
