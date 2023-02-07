package mySearchEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HistoryManager 
{
	private String query;
	private ArrayList<String> historyList;
	private File historyFile;
	
	public void create(String query) 
	{
		this.query = query;
		 try {
	         File f1 = new File("ImportantFiles/history.txt");
	         if(!f1.exists()) {
	            f1.createNewFile();
	         }

	         FileWriter fileWritter = new FileWriter(f1.getName(),true);
	         BufferedWriter bw = new BufferedWriter(fileWritter);
	         bw.write(query+"\n");
	         bw.close();
	         System.out.println("Done");
	      } catch(IOException e){
	         e.printStackTrace();
	      }
	}
	
	public void loadHistory() 
	{
		historyList = new ArrayList<String>();
        try {
           historyFile = new File("ImportantFiles/history.txt");
            Scanner myReader = new Scanner(historyFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                historyList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}
}
