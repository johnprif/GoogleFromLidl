package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;

public class MListener implements MouseListener
{
	String tweet;
	String link;
	JLabel myLabel;
	String userChoise;
	 	
	public void setInfo(String tweet, String link, JLabel myLabel)
	{
		this.tweet = tweet;
	 	this.link = link;
	 	this.myLabel = myLabel; 
	}
	 	
	@Override
	public void mouseClicked(MouseEvent evt)
	{   	
		try{
			Desktop.getDesktop().browse(new URI(link));
	    }catch (IOException | URISyntaxException e)
	    {
	    	e.printStackTrace();
	    }
	}

	@Override
	public void mouseExited(MouseEvent evt)
	{	
		if (tweet != null) 
		{
			myLabel.setText(tweet);
		    myLabel.setForeground(Color.BLACK);
	    }
	}

	@Override
	public void mouseEntered(MouseEvent evt)
	{
		if (tweet != null)
	    {
			myLabel.setText(tweet);
		    myLabel.setForeground(Color.BLUE);
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		//nothing
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		//nothing
	}
	    
	public String setChoise()
	{
		return userChoise;
	}
}
