package model;

import java.util.ArrayList;

public class OurHighLighter 
{
	private String keyWord;
	private ArrayList<ArrayList<String>> tweetsLinks;
	
	public OurHighLighter(String keyWord, ArrayList<ArrayList<String>> tweetsLinks)
	{
		this.keyWord = keyWord;
		this.tweetsLinks = tweetsLinks;
		highLigthing();
	}
	
	private void highLigthing()
	{
		for(int i=0; i<tweetsLinks.get(0).size(); i++)
		{
			if(tweetsLinks.get(0).get(i).contains(keyWord))
			{
				String[] subString = tweetsLinks.get(0).get(i).split(" ");
				for(int j=0; j<subString.length; j++)
				{
					if(subString[j].contains(keyWord))
					{
						subString[j]="\u001B[34m"+keyWord;
					}
				}
				tweetsLinks.get(0).set(i, String.join(" ", subString));
			}
		}
	}
	
	public void print()
	{
		for(int i=0; i<tweetsLinks.get(0).size(); i++)
		{
			System.out.println("------------"+tweetsLinks.get(0).get(i));
		}
	}
}
