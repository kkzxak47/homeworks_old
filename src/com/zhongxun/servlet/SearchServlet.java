package com.zhongxun.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.util.Version;

import com.zhongxun.luceneUtil.Searcher;

/**
 * @author iNVAiN
 * @version 2013年12月5日
 * 
 *          任何servlet都必须实现Servlet接口，GenericServlet是个通用的、不特定于任何协议的Servlet，
 *          它实现了Servlet接口；
 *          而HttpServlet继承了GenericServlet，因此HttpServlet也实现了Servlet接口
 *          ，所以我们定义的Servlet只需要继承HttpServlet即可
 *          Servlet接口中定义了一个service方法，HttpServlet对该方法进行了实现，
 *          实现方法就是将ServletRequest和ServletResponse转换为HttpServletRequest和HttpServletResponse
 *          在HttpServlet类中所提供的doGet
 *          、doPost等方法都是直接返回错误信息，所以我们需要在自己定义的Servlet类中override这些方法
 * 
 */

public class SearchServlet extends HttpServlet {
	

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
		try {
			process(request, response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		try {
			process(request, response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		request.setCharacterEncoding("utf-8");

		String[] fields = request.getParameterValues("field");
//		for (String s : fields) {
//			System.out.println(s);
//		}
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int currentPage = Integer.parseInt(request.getParameter("targetPage"));
		request.setAttribute("currentPage", currentPage);
		String keyword = request.getParameter("keyword");
		
		int lastIndex = pageSize * (currentPage - 1);
		// 如果tomcat的配置文件中，URIEncoding未修改为UTF-8，用GET方法传递参数，则需要进行下面的重编码，否则汉字会乱码
		// String keyword = new
		// String(request.getParameter("keyword").getBytes("ISO-8859-1"),
		// "utf-8");
		// System.out.println(keyword);

		ScoreDoc lastDoc = (ScoreDoc) request.getAttribute("lastDoc");

		// System.out.println("field: "+ field);
		// System.out.println("keyword: "+ keyword);
		// Searcher searcher = new
		// Searcher("C:\\Users\\iNVAiN\\workspace\\test\\index");
//		Searcher searcher = new Searcher("C:\\Users\\iNVAiN\\Desktop\\倒排索引\\n多网页index");
//		Searcher searcher = new Searcher("C:/Users/iNVAiN/workspace/test/index");
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

		Query query = searcher.getQuery(fields, keyword);
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
					fields[0], searcher.getAnalyzer());
			String highlightedTitle = null;
			try {
				highlightedTitle = highlighter.getBestFragment(streamTitle, filename);
			} catch (InvalidTokenOffsetsException e1) {
				e1.printStackTrace();
			}

			TokenStream streamContent = TokenSources.getAnyTokenStream(searcher.getReader(), docID,
					fields[1], searcher.getAnalyzer());
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
			
			// IKQueryParser.setMaxWordLength(true);
//			QueryParser qp = new QueryParser(Version.LUCENE_35, fields[0], searcher.getAnalyzer());
//			qp.setDefaultOperator(QueryParser.OR_OPERATOR);
//			Query query = qp.parse(keyword);
//			// TokenStream tokenStream = new IKAnalyzer(true).tokenStream(field,
//			// new BufferedReader(new FileReader(new
//			// File(searcher.getDocPath(docID)))));
//			QueryScorer scorer = new QueryScorer(query, fields[0]);
//			Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
//			SimpleHTMLFormatter sFormatter = new SimpleHTMLFormatter("<span class='red'>",
//					"</span>");
//			Highlighter highlighter = new Highlighter(sFormatter, scorer);
//
//			highlighter.setTextFragmenter(fragmenter);
//
//			
//			filenameList.add(searcher.getDocName(docID));
//			pathList.add(path);
//			TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getReader(), docID,
//					"content", searcher.getAnalyzer());
//
//			try {
//				excerptList.add(highlighter.getBestFragment(tokenStream, text));
//			} catch (InvalidTokenOffsetsException e) {
//				e.printStackTrace();
//			}
		}

		request.setAttribute("filenameList", titleList);
		request.setAttribute("pathList", urlList);
		request.setAttribute("excerptList", excerptList);
		request.getRequestDispatcher("searchResult.jsp").forward(request, response);
	}
}
