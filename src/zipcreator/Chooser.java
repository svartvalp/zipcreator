package zipcreator;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Chooser extends JFileChooser 
{
	public Chooser()
	{
		super();
		setCurrentDirectory(new File("."));
		setMultiSelectionEnabled(true);
		this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}
	public ArrayList <File> call()
	{
		showOpenDialog(null);
		File[] files = getSelectedFiles();
		ArrayList <File> fil = new ArrayList <File> ();
		for (int i = 0; i < files.length; i++)
		{
			fil.add(files[i]);
		}
		return fil;
	}
	public static void main(String[] args)
	{
		Chooser h = new Chooser();
	}
}
