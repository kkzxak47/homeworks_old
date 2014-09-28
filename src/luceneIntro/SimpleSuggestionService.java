package luceneIntro;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.NGramDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SimpleSuggestionService {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		File dir = new File("c:/spellchecker/");
		
		Directory directory = FSDirectory.open(dir);
		
		SpellChecker spellChecker = new SpellChecker(directory);
		spellChecker.setStringDistance(new NGramDistance());
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, null);
		
		spellChecker.indexDictionary(new PlainTextDictionary(new File(dir + File.separator + "dic.txt")), config, false);
		
		String wordForSuggestions = "жа";
		
		int suggestionsNumber = 5;
		
		String[] suggestions = spellChecker.suggestSimilar(wordForSuggestions, suggestionsNumber);
		
		if(suggestions != null && suggestions.length > 0) {
			for (String word : suggestions) {
				System.out.println("Did you mean: " + word);
			}
		}
		else {
			System.out.println("No suggestions found for word: " + wordForSuggestions);
		}
		
		//close
		directory.close();
		spellChecker.close();
	}

}
