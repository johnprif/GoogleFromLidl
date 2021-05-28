package model;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.LevenshteinDistance;
import org.apache.lucene.search.spell.LuceneLevenshteinDistance;
import org.apache.lucene.search.spell.NGramDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Spell_Checker
{
    private Directory directory;
    private PlainTextDictionary txt_dict;
    private SpellChecker checker;
    private String[] suggestions;
    
	public Spell_Checker(String keyWord) throws IOException
    {
        directory = FSDirectory.open(Paths.get("Index_Dictionary"));
        txt_dict = new PlainTextDictionary(Paths.get("eng_dictionary.txt"));
        checker = new SpellChecker(directory);
        System.out.print("\nBuilding index from the .txt dictionary... ");
        checker.indexDictionary(txt_dict, new IndexWriterConfig(new KeywordAnalyzer()), false);
        System.out.print("\nfinish! ");
        directory.close();       
        chooseAlgorithm("NGramDistance");        
        suggestions = checker.suggestSimilar(keyWord, 6);
    }
    
    
	private void chooseAlgorithm(String argorithm)
	{
		if(argorithm.equals("JaroWinklerDistance"))
		{
			checker.setStringDistance(new JaroWinklerDistance());
		}else if(argorithm.equals("LevenshteinDistance"))
		{
			checker.setStringDistance(new LevenshteinDistance());
		}else if(argorithm.equals("LuceneLevenshteinDistance"))
		{
			checker.setStringDistance(new LuceneLevenshteinDistance());
		}else if(argorithm.equals("NGramDistance"))
		{
			//Default
			checker.setStringDistance(new NGramDistance()); 
		}
	}
	
	public String[] getSuggestions()
	{
		return suggestions;
	}
}
