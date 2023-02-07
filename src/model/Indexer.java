package model;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

public class Indexer {
	private String CSV = "ImportantFiles/file.csv";
	private IndexWriter writer;
	private List<String[]> csvData;
	
	public Indexer(IndexWriter writer) throws IOException
	{
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
		CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
		try(CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).withCSVParser(csvParser).withSkipLines(1).build())
		{																	
			csvData = reader.readAll();
		}
		return csvData;
	}
	
	private void addDataToDoc(List<String[]> csvData) throws IOException
	{
		for(int i=0; i<csvData.size(); i++)
		{
			String[] temp=csvData.get(i);
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
