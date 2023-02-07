package mySearchEngine;

import java.awt.Color;
import java.awt.color.ICC_ColorSpace;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JLabel;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;

import com.opencsv.exceptions.CsvException;


public class Controller {
	private static String INDEX_DIR = "ImportantFiles/index";
	private static EnglishAnalyzer analyzer;
	private static FSDirectory dir;
	private static IndexWriterConfig config;
	private static IndexWriter writer;
	private static ScoreDoc[] hits;
	private int pagesNumber;
	private String filter;
	private String sort;
	private ArrayList<ArrayList<String>> tweetsLinks;
	private ArrayList<JLabel> listOfLabels;
	private ArrayList<MListener> listOfListeners;
	private ArrayList<ArrayList<ArrayList<String>>> contents = new ArrayList<ArrayList<ArrayList<String>>>(); // exei mesa ola ta tweetsLinks
	private int ypoloipo;
	private int currentPage=0;
	private String[] suggestions;

	
	public Controller() throws IOException {
		analyzer = new EnglishAnalyzer();
		dir = FSDirectory.open(Paths.get(INDEX_DIR));
		config = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(dir, config);
	}
	
	public void makeIndex() throws IOException, CsvException {
		Indexer indexer = new Indexer(INDEX_DIR, analyzer,dir,config, writer);
		indexer.createIndex();
	}
	
	public int searchIndex(String query) throws IOException, CsvException, ParseException
	{				
		//clearResults();
		Searcher mySearcher = new Searcher(analyzer, writer);
		hits= mySearcher.searchIndex(query, sort);		//gienaei ooola ta hits
		if (isNullHits(query)) 
		{
			return -1; //den brika hits kanw suggestions
		}
		pagesNumber = findPagesNumber();
		System.out.println(" PAGE NUMBER "+ pagesNumber);
		if(!filter.equals("default")) 
		{
			
			mySearcher.filterSorter(filter);
		}
		createPages(mySearcher);
		return pagesNumber;

	}
	
	private boolean isNullHits(String queryStr) throws IOException 
	{
		if (hits.length == 0) 
		{
			Spell_Checker myChecker = new Spell_Checker(queryStr);
			suggestions = myChecker.getSuggestions();
			return true;
		}
		return false;
	}
	
	private int findPagesNumber() {
		int n = hits.length;
		System.out.println("POSA HITS "+ n);
		if (n%10 == 0) {
			return n/10;
		}
		return (n/10+1);
	}
	
	private void createPages(Searcher mySearcher) throws IOException
	{
		for (int n=0; n< pagesNumber; n++) {
			ScoreDoc[] tenHits = getNextPagesHits(n+1);
			tweetsLinks=mySearcher.getTweetsAndLinks(tenHits);
			//setLabelsAndMouseListeners();
			contents.add(tweetsLinks);
			setContent(tweetsLinks.get(0).size());
		}
		//setVisibleResults(true);
	}
	public void getResults(int page) {
		tweetsLinks = contents.get(page);
		
		setContent(tweetsLinks.get(0).size());
		setVisibleResults(true, page);
		OurHighLighter kati = new OurHighLighter("Trump's", tweetsLinks);
		kati.print();
	}
	
	public void setVisibleResults(boolean flag, int ee)
	{
		if(ypoloipo != 0 && ee == pagesNumber-1)
		{			
			for (int i = 0; i< ypoloipo; i++) {
				System.out.println("ypoloipo = "+ ypoloipo);
				listOfLabels.get(i).setVisible(flag);
			}
			for(int j=ypoloipo; j<10; j++)
			{
				listOfLabels.get(j).setVisible(!flag);
			}
		}else
		{
			System.out.println("ee = "+ ee);
			for (int i = 0; i< 10; i++) {
				listOfLabels.get(i).setVisible(flag);
			}
		}
	}
	
	private ScoreDoc[] getNextPagesHits(int currentPage) {
		 // Î±Î½ ÎµÎ¹Î¼Î±Î¹ ÏƒÏ„ 1Î· ÏƒÎµÎ»Î¹Î´Î±Î¸ÎµÎ»Ï‰ Ï„Î± Î±Ï€Î¿Ï„ÎµÎ» Î±Ï€Î¿ Ï„Î¿ 0 mexri to 9 Î´Î»Î´ Î±Ï€Î¿ Ï„Î¿ top-10 Î¼ÎµÏ‡Ï�Î¹ Ï„Î¿ top
		int top = currentPage *10;
		ScoreDoc[] tenHits = new ScoreDoc[10];
		ypoloipo = (hits.length) % 10;					//einai ta apotelesmata tis teleytas selidas, 
		int tenHitsIterator = 0; 	
		
		for (int hitsIterator = (top-10); hitsIterator < top; hitsIterator++) {			
			if (hasLessThan10ResultsInLastPage(tenHitsIterator, currentPage)) {
				return findLastPagesResults(ypoloipo, top);
			}
			tenHits[tenHitsIterator] = hits[hitsIterator];
			tenHitsIterator++;
		}
		return tenHits;	
	}
	
	private boolean hasLessThan10ResultsInLastPage(int tenHitsIterator, int currentPage) {
		if (tenHitsIterator == ypoloipo && pagesNumber == currentPage && ypoloipo!= 0) {
			return true;
		}
		return false;
	}
	
	private ScoreDoc[] findLastPagesResults(int ypoloipo, int top) {
		ScoreDoc[] tempDocs = new ScoreDoc[ypoloipo];            //den xrisimopoiw to tenHits gt exei 10 theseis kai edw thelw ligoteres
		int lastPageIterator = 0;							
		for (int k = top-10; k<hits.length; k++) {
			tempDocs[lastPageIterator] = hits[k];
			lastPageIterator++;
		}
		return tempDocs;
	}
	
	public void clearResults() {
		contents.clear();
		//tweetsLinks.clear();
	}
	
	public void setContent(int listSize)
	{
		System.out.println("SIZE = "+listSize);
		if(listSize != 0)
		{
			for(int i=0; i<listSize; i++)
			{
				listOfLabels.get(i).setText(tweetsLinks.get(0).get(i));
				listOfLabels.get(i).setForeground(Color.BLACK);
				listOfListeners.get(i).setInfo(tweetsLinks.get(0).get(i), tweetsLinks.get(1).get(i), listOfLabels.get(i));
			}
		}else
		{
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}
	}
	
	public void setSuggestions(String[] suggestions) {
		if(suggestions.length != 0)
		{
			listOfLabels.get(0).setText("Could not find results for this query. You could type one of the following and search again");
			for(int i=1; i<=suggestions.length; i++)
			{
				listOfLabels.get(i).setText(suggestions[i-1]);
				listOfLabels.get(i).setForeground(Color.BLACK);
				listOfListeners.get(i).setInfo(suggestions[i-1], null, listOfLabels.get(i));
				
			}
		}else
		{
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}
		setVisibleResults(true, 1);
	}
	
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public void setLabels(ArrayList<JLabel> listOfLabels) {
		this.listOfLabels = listOfLabels;
	}
	
	public void setListeners(ArrayList<MListener> listOfListeners) {
		this.listOfListeners = listOfListeners;
	}
	
	public ArrayList<ArrayList<String>> getTweetsLinks(){
		return tweetsLinks;
	}
	
	public String[] getSuggestions()
	{
		return suggestions;
	}
}
