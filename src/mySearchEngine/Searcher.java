package mySearchEngine;


import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;


public class Searcher {


	private EnglishAnalyzer analyzer;
	private ScoreDoc[] hits;
	private IndexSearcher searcher;
	//private int hitsPerPage = 10;
	private int MAX_HITS = 100;					
	private IndexWriter writer;
	private Query trueQuery;
	    


	public Searcher( EnglishAnalyzer analyzer, IndexWriter writer) throws IOException
	{
		this.analyzer = analyzer;
		this.writer = writer;
	}
	
	public ScoreDoc[] searchIndex(String queryStr, String sortByValue) throws IOException, ParseException	//Ï„Ï‰Ï�Î± ÎµÏ€Î¹ÏƒÏ„Ï�ÎµÏ†ÎµÎ¹ Ï„Î± 100 Ï€Ï�Ï‰Ï„Î± Ï‡Î¹Ï„Ïƒ ÎºÎ±Î¹ Î¼ÎµÏ„Î± Ï„Î± ÏƒÏ€Î±Î¼Îµ ÏƒÎµ ÏƒÎµÎ»Î¹Î´ÎµÏ‚
	{
		String[] fields = {"username", "name", "tweet", "created_at"};

		trueQuery = new MultiFieldQueryParser(fields, analyzer).parse(queryStr);
		IndexReader reader = DirectoryReader.open(writer);
		searcher = new IndexSearcher(reader);

		hits = sortBy(sortByValue, trueQuery);
		
		return hits;
	}
	
	public ScoreDoc[] sortBy(String sortByValue, Query query) throws IOException {
		//System.out.println("rrrrrrrrrrrrrrrrrrrrr"+ sortByValue);
        if (sortByValue.equals("Relevance")){
            hits = searcher.search(query, MAX_HITS,Sort.RELEVANCE).scoreDocs;
        } else if (sortByValue.equals("IndexOrder")){
            hits = searcher.search(query, MAX_HITS,Sort.INDEXORDER).scoreDocs;
        }
        return hits;
    }
	

	
	public ArrayList<ArrayList<String>> getTweetsAndLinks(ScoreDoc[] tenHits) throws IOException
	{
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> tweets = new ArrayList<String>();
		ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
		
	
		for (int i = 0; i < tenHits.length ; i++) 
		{
			//System.out.println("??????????"+ tenHits[i]);
            int docID = tenHits[i].doc;
            Document d = searcher.doc(docID);
            //System.out.println((i+1)+". "+d.get("tweet")+"\t"+d.get("link"));
            System.out.println((i)+". "+d.get("likes_count")+"----------------------"+d.get("created_at")+"------------"+d.get("tweet")+"\t"+d.get("link"));
            links.add(d.get("link"));
            tweets.add(d.get("tweet"));
		}
		info.add(tweets);
		info.add(links);
		//System.out.println("---------------"+"tweets = "+info.get(0).get(0)+"-------"+"links = "+info.get(1).get(0));
		return info;
	}
	
	public void filterSorter(String field) throws IOException
	{
	
		ScoreDoc[] temp = new ScoreDoc[1];
		int myLenght = hits.length;
		for (int i = 0; i< myLenght - 1; i++) 
		{ 																		//implementing selection sort
			int min = i;
			for (int j = i+1; j< myLenght; j++) 
			{
				String s1 = (searcher.doc(hits[min].doc)).get(field);
				String s2 = (searcher.doc(hits[j].doc)).get(field);
				if (field != "created_at") 
				{
					
					int n1 = Integer.parseInt(s1);
					int n2 = Integer.parseInt(s2);
					if (n1 < n2) {
						min = j;
					}
				}else 
				{
					if (s1.compareTo(s2) < 0) 
					{  
						min = j;
					}
				}
			}
			temp[0] = hits[i];
			hits[i] = hits[min];
			hits[min] = temp[0];	
		}
	}
	

	
	public void highlightKeywords() { // den kanei kati
		QueryScorer queryScorer = new QueryScorer(trueQuery);
		Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
        Highlighter highlighter = new Highlighter(queryScorer); // Set the best scorer fragments
        highlighter.setTextFragmenter(fragmenter); // Set fragment to highlight
        

	}
}
