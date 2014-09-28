package com.zhongxun.luceneUtil;

import java.io.File;

import junit.framework.TestCase;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class PrefixQueryTest extends TestCase {
	public void testPrefix() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		Term term = new Term("filename", "´ó");
		PrefixQuery query = new PrefixQuery(term);
		
		TopDocs matches = searcher.search(query, 10);
		int pAndBelow = matches.totalHits;
		Document doc = searcher.doc((matches.scoreDocs)[0].doc);
		System.out.println(doc.get("filename"));
		
		matches = searcher.search(new TermQuery(term), 10);
		int jP = matches.totalHits;
		
		System.out.println("pAndBelow : " + pAndBelow);
		System.out.println("jP : " + jP);
		searcher.close();
		dir.close();
	}
}
