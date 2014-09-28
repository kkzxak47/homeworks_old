package com.zhongxun.luceneUtil;

import java.io.File;

import junit.framework.TestCase;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class BasicSearchingTest extends TestCase {
	public void testTerm() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		
		Term t = new Term("contents", "public");
		Query query = new TermQuery(t);
		TopDocs docs = searcher.search(query,  10);
//		System.out.println(docs.totalHits);
		ScoreDoc[] sd = docs.scoreDocs;
		for(ScoreDoc doc : sd){
			int id = doc.doc;
			System.out.println(searcher.doc(id));
		}
//		assertEquals("public", 14, docs.totalHits);
//		t = new Term("filename", "灯光上网.txt");
//		docs = searcher.search(new TermQuery(t), 10);
//		assertEquals("上网", 1, docs.totalHits);
		searcher.close();dir.close();
	}
	public void testQueryParser() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		
		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new IKAnalyzer(true));
		
		Query query = parser.parse("+class +public -驱逐舰");
		TopDocs docs = searcher.search(query, 10);
//		System.out.println(docs.totalHits);
		assertEquals(2, docs.totalHits);
		Document d = searcher.doc(docs.scoreDocs[0].doc);
		assertEquals("class_field_modified", d.get("filename"));
		
		query = parser.parse("战斗 OR 美国");
		docs = searcher.search(query, 10);
		assertEquals("战斗 OR 美国", 7, docs.totalHits);
		
		searcher.close();
		dir.close();
	}
}
