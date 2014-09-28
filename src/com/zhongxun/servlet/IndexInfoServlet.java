package com.zhongxun.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.index.FilterIndexReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexInfoServlet extends HttpServlet {

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

		System.out.println("IndexInfo");
		request.setCharacterEncoding("utf-8");

		String path = "E:/��Ϣ��������ҵ/web_data/index";

		Directory dir = FSDirectory.open(new File(path));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher issearcher = new IndexSearcher(reader);
		
		System.out.println("getCurrentVersion : " + reader.getCurrentVersion(dir));
		System.out.println("getVersion() : " + reader.getVersion());
		System.out.println("��������ʱ�䣺" + new Date(reader.getVersion()));
		System.out.println("�ĵ�����maxDoc : " + issearcher.maxDoc());
		System.out.println("���������ֶΣ�");
		int i = 1;
		for (String s : reader.getFieldNames(IndexReader.FieldOption.ALL)) {
			System.out.println(i++ + " : " + s);
		}
		System.out.println("numDoc : " + reader.numDocs());
		long uniqueTermCount = 0;
		for(IndexReader rx : reader.getSequentialSubReaders()) {
			System.out.println(rx.getClass());
			long cur = rx.getUniqueTermCount();
			uniqueTermCount += cur;
			System.out.println(cur);
		}
		System.out.println("������������ : " + uniqueTermCount);
		
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
