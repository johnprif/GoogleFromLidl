package mySearchEngine;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.*;

public class Indexer {
	private String INDEX_DIR;
	private String CSV = "file.csv";
	private EnglishAnalyzer analyzer;
	private FSDirectory dir;
	private IndexWriterConfig config;
	private IndexWriter writer;
	private List<String[]> csvData;
	
	public Indexer(String INDEX_DIR, EnglishAnalyzer analyzer, FSDirectory dir, IndexWriterConfig config,IndexWriter writer) throws IOException
	{
		this.INDEX_DIR = INDEX_DIR;
		this.analyzer = analyzer;
		this.dir = dir;
		this.config = config;
		this.writer = writer;
		csvData = new ArrayList<String[]>();
	}		
	
	public void createIndex() throws IOException, CsvException
	{
		csvData.addAll(csvParser(CSV));
		addDataToDoc(csvData);
	}
	
	private List<String[]> csvParser(String csvPath) throws IOException, CsvException
	{
		List<String[]> csvData = new ArrayList<>();
		CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build(); // custom separator, --line headers
		try(CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).withCSVParser(csvParser).withSkipLines(1).build())
		{																	//custom csvParser
			csvData = reader.readAll();
		}
		return csvData;
	}
	
	private void addDataToDoc(List<String[]> csvData) throws IOException
	{
		for(int i=0; i<csvData.size(); i++)
		{
			String[] temp=csvData.get(i);
			//System.out.println("1" + temp[0]+"2" +temp[1] +"3"+temp[2] +"4"+temp[3]+"5"+temp[4]+"6"+ temp[5]+"7"+temp[6]+"8"+ temp[7]+"9"+temp[8]+"10"+temp[9]);
			writer.addDocument(createDocument(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7],temp[8],temp[9]));
		}
	}
	
	private Document createDocument(String created_at, String user_id, String username, String name, String tweet, String language, String replies_count, String retweets_count, String likes_count, String link)
	{
		Document document = new Document();
	    document.add(new TextField("created_at", created_at , Field.Store.YES));
	    document.add(new TextField("user_id", user_id , Field.Store.YES));
	    document.add(new TextField("username", username , Field.Store.YES));
	    document.add(new TextField("name", name , Field.Store.YES));
	    document.add(new TextField("tweet", tweet , Field.Store.YES));
	    document.add(new TextField("language", language , Field.Store.YES));
	    document.add(new TextField("replies_count", replies_count , Field.Store.YES));
	    document.add(new TextField("retweets_count", retweets_count , Field.Store.YES));
	    document.add(new TextField("likes_count", likes_count , Field.Store.YES));
	    document.add(new TextField("link", link , Field.Store.YES));
		return document;
	}
}
