package com.zhongxun.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;

import com.zhongxun.luceneUtil.Searcher;

public class MultiFieldSearchServlet extends HttpServlet {

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
		String[] fields = request.getParameterValues("field");
		System.out.println(fields.length);
		for(String f : fields) {
			System.out.println(f);
		}
		
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int currentPage = Integer.parseInt(request.getParameter("targetPage"));
		request.setAttribute("currentPage", currentPage);
		String keyword = request.getParameter("keyword");
		System.out.println(keyword);
		int lastIndex = pageSize * (currentPage - 1);
		
		ScoreDoc lastDoc = (ScoreDoc) request.getAttribute("lastDoc");
		
		Searcher searcher = new Searcher("D:/lucene/localindex");
		
		TopDocs result = null;
		
		try {
			result = searcher.search(fields, keyword, pageSize, lastIndex, lastDoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalHits = (result == null) ? 0 : result.totalHits;
		request.setAttribute("totalHits", totalHits);
		int showingResult = pageSize < (totalHits - pageSize * (currentPage - 1)) ? pageSize
				: (totalHits - pageSize * (currentPage - 1));

		request.setAttribute("showingResult", showingResult);
		if (showingResult > 0)
			request.setAttribute("lastDoc", result.scoreDocs[showingResult - 1]);
		
		List<String> titleList = new ArrayList<String>();
		List<String> urlList = new ArrayList<String>();
		List<String> excerptList = new ArrayList<String>();

		Query query = null;
		try {
			query = searcher.getQuery(fields, keyword);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		QueryScorer scorer = new QueryScorer(query, fields[0]);
		SimpleHTMLFormatter sFormatter1 = new SimpleHTMLFormatter("<span style='color:red'>",
				"</span>");
		Highlighter highlighter = new Highlighter(sFormatter1, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
		
		for (int i = 0; i < showingResult; i++) {
			int docID = result.scoreDocs[i].doc;
			String fullpath = searcher.getDocPath(docID);
			String filename = searcher.getDocName(docID);
			String content = searcher.getContent(docID);
			
			TokenStream streamTitle = TokenSources.getAnyTokenStream(searcher.getReader(), docID,
					"filename", searcher.getAnalyzer());
			String highlightedTitle = null;
			try {
				highlightedTitle = highlighter.getBestFragment(streamTitle, filename);
			} catch (InvalidTokenOffsetsException e1) {
				e1.printStackTrace();
			}

			TokenStream streamContent = TokenSources.getAnyTokenStream(searcher.getReader(), docID,
					"content", searcher.getAnalyzer());
			String highlightedContent = null;
			try {
				highlightedContent = highlighter.getBestFragment(streamContent, content);
			} catch (InvalidTokenOffsetsException e) {
				e.printStackTrace();
			}

			if (null != highlightedTitle && highlightedTitle.length() != 0)
				titleList.add(highlightedTitle);
			else
				titleList.add(filename.length() > 140 ? filename.substring(0, 140) : filename);
			if (null != highlightedContent && highlightedContent.length() != 0)
				excerptList.add(highlightedContent);
			else
				excerptList.add(content.length() > 140 ? content.substring(0, 140) : content);

			urlList.add(fullpath);
			
		}

		request.setAttribute("filenameList", titleList);
		request.setAttribute("pathList", urlList);
		request.setAttribute("excerptList", excerptList);
		request.getRequestDispatcher("multiFieldSearchResult.jsp").forward(request, response);
		
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
