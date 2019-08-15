package zipcreator;

import java.io.File;

import javax.swing.JFileChooser;

public class FolderFinder extends JFileChooser {
	public FolderFinder()
	{
		super();
		setCurrentDirectory(new File("."));
		setMultiSelectionEnabled(false);
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	public File call()
	{
		showOpenDialog(null);
		File zip_dir = this.getSelectedFile();
		return zip_dir;
	}
	public static void main(String[] args)
	{
		FolderFinder f = new FolderFinder();
		File dir = f.call();
		System.out.println(dir.getPath());
	}
}
