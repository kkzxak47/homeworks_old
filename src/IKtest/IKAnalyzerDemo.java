package IKtest;

/** 
 * IK Analyzer Demo 
 * @param args 
 */
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
//����IKAnalyzer3.0���� 
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author linly
 * 
 */
public class IKAnalyzerDemo {

	
}