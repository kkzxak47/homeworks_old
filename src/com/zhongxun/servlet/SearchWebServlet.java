package com.zhongxun.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
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
import org.apache.lucene.search.spell.NGramDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
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

public class SearchWebServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("SearchWebServlet已加载");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1773882830638242707L;

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");

		String[] fields = request.getParameterValues("field");
		// for (String s : fields) {
		// System.out.println(s);
		// }
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
		// Searcher searcher = new
		// Searcher("C:\\Users\\iNVAiN\\Desktop\\倒排索引\\n多网页index");
//		Searcher searcher = new Searcher("C:/solr_index/index");
		Searcher searcher = new Searcher("E:/信息检索课作业/web_data/index");
		TopDocs result = null;

		try {
			result = searcher.search(fields, keyword, pageSize, lastIndex, lastDoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
//			System.out.println(docID);
			String url = searcher.getURL(docID);
			if (null == url) {
				url = "";
			}
			String title = searcher.getTitle(docID);
			if (null == title) {
				title = "";
			}
			String content = searcher.getWebContent(docID);
			if (null == content) {
				content = "";
			}
			
			
			// System.out.println("text1:\n" + title);
			// System.out.println("text2:\n" + content);

			TokenStream streamTitle = null;
			String highlightedTitle = null;
			try {
				streamTitle = TokenSources.getAnyTokenStream(searcher.getReader(), docID,
						fields[0], searcher.getAnalyzer());
				highlightedTitle = highlighter.getBestFragment(streamTitle, title);
			} catch (InvalidTokenOffsetsException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			TokenStream streamContent = null;
			String highlightedContent = null;
			try {
				streamContent = TokenSources.getAnyTokenStream(searcher.getReader(), docID,
						fields[1], searcher.getAnalyzer());
				highlightedContent = highlighter.getBestFragment(streamContent, content);
			} catch (InvalidTokenOffsetsException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			if (null != highlightedTitle && highlightedTitle.length() != 0)
				titleList.add(highlightedTitle);
				
			else
				titleList.add(title.length() > 140 ? title.substring(0, 140) : title);
			
			if (null != highlightedContent && highlightedContent.length() != 0) {
				excerptList.add(highlightedContent);
				if ("".equals(title)) {
					title = content.substring(0, 30>highlightedContent.length()?highlightedContent.length():30);
				}
			} else {
				excerptList.add(content.length() > 140 ? content.substring(0, 140) : content);
				if ("".equals(title)) {
					title = content.substring(0, 30>content.length()?content.length():30);
				}
			}
			urlList.add(url);
			
			// IKQueryParser.setMaxWordLength(true);
			// QueryParser qp1 = new QueryParser(Version.LUCENE_35, fields[0],
			// searcher.getAnalyzer());
			// qp1.setDefaultOperator(QueryParser.OR_OPERATOR);
			// Query query1 = qp1.parse(keyword);
			// QueryScorer scorer1 = new QueryScorer(query1, fields[0]);
			// Fragmenter fragmenter1 = new SimpleSpanFragmenter(scorer1);
			// SimpleHTMLFormatter sFormatter1 = new
			// SimpleHTMLFormatter("<span class='red'>",
			// "</span>");
			// Highlighter highlighter1 = new Highlighter(sFormatter1, scorer1);
			// highlighter1.setTextFragmenter(fragmenter1);
			//
			// QueryParser qp2 = new QueryParser(Version.LUCENE_35, fields[1],
			// searcher.getAnalyzer());
			// qp2.setDefaultOperator(QueryParser.OR_OPERATOR);
			// Query query2 = qp2.parse(keyword);
			// QueryScorer scorer2 = new QueryScorer(query2, fields[1]);
			// Fragmenter fragmenter2 = new SimpleSpanFragmenter(scorer2);
			// SimpleHTMLFormatter sFormatter2 = new
			// SimpleHTMLFormatter("<span class='red'>",
			// "</span>");
			// Highlighter highlighter2 = new Highlighter(sFormatter2, scorer2);
			// highlighter2.setTextFragmenter(fragmenter2);
			//
			// // String title = new String(.getBytes("gb2312"), "utf-8");
			// // titleList.add(searcher.getTitle(docID));
			// urlList.add(url);
			// // excerptList.add(text);
			// TokenStream tokenStream1 =
			// TokenSources.getAnyTokenStream(searcher.getReader(), docID,
			// "content", searcher.getAnalyzer());
			// TokenStream tokenStream2 =
			// TokenSources.getAnyTokenStream(searcher.getReader(), docID,
			// "title", searcher.getAnalyzer());
			// String highlighted1 = null;
			// String highlighted2 = null;
			// try {
			// highlighted1 = highlighter1.getBestFragment(tokenStream1, text1);
			// // highlighted2 = highlighter2.getBestFragment(tokenStream2,
			// text2);
			// } catch (InvalidTokenOffsetsException e) {
			// e.printStackTrace();
			// }
			//
			// if(null != highlighted1 && highlighted1.length() != 0)
			// excerptList.add(highlighted1);
			// else
			// excerptList.add(text1);
			// if(null != highlighted2 && highlighted2.length() != 0)
			// titleList.add(highlighted2);
			// else
			// titleList.add(text2);

		}

		request.setAttribute("filenameList", titleList);
		request.setAttribute("pathList", urlList);
		request.setAttribute("excerptList", excerptList);

		String[] suggestList = getSuggestion(keyword);
		request.setAttribute("suggestList", suggestList);
		request.getRequestDispatcher("searchWebResult.jsp").forward(request, response);

	}

	private String[] getSuggestion(String keyword) throws Exception {
		File dir = new File("c:/spellchecker/");

		Directory directory = FSDirectory.open(dir);

		SpellChecker spellChecker = new SpellChecker(directory);
		spellChecker.setStringDistance(new NGramDistance());
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, null);

		spellChecker.indexDictionary(new PlainTextDictionary(new File(dir + File.separator
				+ "dic.txt")), config, false);

		String wordForSuggestions = keyword;
		int suggestionsNumber = 3;

		String[] suggestions = spellChecker.suggestSimilar(wordForSuggestions, suggestionsNumber);

		// close
		directory.close();
		spellChecker.close();
		return suggestions;
	}
}
