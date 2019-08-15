package zipcreator;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWIndow extends JFrame {
	private JButton add_files;
	private JButton add_zip;
	private JButton remove;
	private JButton execute;
	private JList<String> list;
	private DefaultListModel<String> listmodel;
	private JTextField zip_name;
	private JTextField zip_field;
	private ArrayList<File> files;
	private File zip_directory;
	private JScrollPane scroll;
	private JLabel name_label;
	private JLabel folder_label;
	private JLabel file_label;
	private JWindow window;
	
	public static  void add_to_zip(File file, ZipOutputStream zout, String parent_folder)
	{
		if(file.isFile())
		{
		try
		{
			ZipEntry entry;
			if(parent_folder == "")
			{
			entry = new ZipEntry(file.getName());
			}
			else 
			{
			entry = new ZipEntry(parent_folder + file.getName());
			}
			zout.putNextEntry(entry);
		Files.copy(file.toPath(), zout);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Ошибка в добавлении файлов в архив.");
			return;
		}
		try
		{
			zout.closeEntry();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Ошибка");
			return;
		}
		}
		else if (file.isDirectory())
		{
			ZipEntry entry;
			if (parent_folder == "")
			{
			entry = new ZipEntry(file.getName()+ "\\");
			}
			else 
			{
				entry = new ZipEntry(parent_folder + file.getName() + "\\");
			}
			try {
				zout.putNextEntry(entry);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				zout.closeEntry();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String parent = parent_folder + file.getName() + "\\";
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				add_to_zip(files[i], zout, parent);
			}
		}
	}
	public void update_directory()
	{
		String str = zip_field.getText();
		zip_directory = new File(str);
	}
	
	public static ArrayList <String> get_paths(String str)
	{
		ArrayList <String> buff = new ArrayList<String>();
		int begin = 0;
		str = str.concat(" ");
		while(!str.isEmpty())
		{
			int end = str.indexOf(' ');
			buff.add(str.substring(begin, end));
			str = str.substring(end+1, str.length());
		}
		return buff;
	}
	
	public MainWIndow()
	{
		super();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setResizable(false);
		window = new JWindow(this);
		folder_label = new JLabel("Путь к файлу");
		zip_name = new JTextField();
		name_label = new JLabel("Имя zip-файла");
		add_files = new JButton("Добавить");
		add_zip = new JButton("Выбрать путь zip-файла");
		remove = new JButton("Убрать");
		execute = new JButton("Выполнить");
		zip_field = new JTextField();
		listmodel = new DefaultListModel<String>();
		list = new JList<String>(listmodel);
		scroll = new JScrollPane(list);
		file_label = new JLabel("Список файлов");
		list.setLayoutOrientation(JList.VERTICAL);
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		Font font = new Font("TimesRoman", Font.ITALIC, 12);
		add_files.setFont(font);
		remove.setFont(font);
		zip_field.setFont(font);
		add_zip.setFont(font);
		execute.setFont(font);
		name_label.setFont(font);
		folder_label.setFont(font);
		file_label.setFont(font);
		this.add(add_files);
		this.add(remove);
		this.add(panel);
		this.add(zip_field);
		this.add(add_zip);
		this.add(execute);
		this.add(zip_name);
		this.add(name_label);
		this.add(folder_label);
		this.add(file_label);
		list.setVisibleRowCount(10);
		file_label.setBounds(60, 3, 100, 25);
		panel.setBounds(10, 30, 200, 150);
		panel.add(scroll, BorderLayout.CENTER);
		zip_name.setBounds(235, 60, 130, 25);
		name_label.setBounds(250, 33, 100, 25);
		add_files.setBounds(10, 190 , 98, 40);
		remove.setBounds(112, 190, 98, 40);
		zip_field.setBounds(235,130,130,25);
		add_zip.setBounds(250, 155, 100, 30);
		execute.setBounds(250, 205, 100, 30);
		folder_label.setBounds(250, 103, 100, 25);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
		add_files.setToolTipText("Добавить файлы, которые вы хотите поместить в архив");
		add_zip.setToolTipText("Выбрать путь к каталогу, в котором сохранится zip-архив");
		remove.setToolTipText("Убрать файл из списка");
		execute.setToolTipText("Создать и сохранить zip-архив");
		this.setVisible(true);
		add_files.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						Chooser ch = new Chooser();
						files = ch.call();
						for(int i = 0; i< files.size(); i++)
						{
							listmodel.addElement(files.get(i).getPath());
						}
					}
				});
		remove.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						try
						{
							listmodel.remove(list.getSelectedIndex());
						}
						catch(ArrayIndexOutOfBoundsException e1)
						{
							JOptionPane.showMessageDialog(null, "Не выбран ни один из элементов");
						}
					}
			
				});
		add_zip.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						FolderFinder fold = new FolderFinder();
						zip_directory = fold.call();
						if (zip_directory == null)
						{
							zip_directory = new File(".");
						}
						zip_field.setText(zip_directory.getPath());
					}
			
				});
		execute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update_directory();
				String name_z;
				try
				{
					name_z = zip_name.getText();
				}
				catch(NullPointerException e1)
				{
					name_z = "out";
				}
				File out = new File(zip_directory, name_z + ".zip");
				try
				{
					out.createNewFile();
				}
				catch(IOException e1)
				{
					JOptionPane.showMessageDialog(null, "Ошибка в создании zip-файла. Проверьте путь, где должен находиться архив");
					e1.printStackTrace();
				}
				FileOutputStream fout = null;
				try {
					fout = new FileOutputStream(out);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				ZipOutputStream zout = new ZipOutputStream(fout);
				for (int i = 0; i < listmodel.getSize(); i++)
				{
					File f = new File(listmodel.get(i));
					add_to_zip(f,zout,"");
				}
				try
				{
				zout.close();
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				window.setVisible(false);
			}
		});
	}
	
	public static void main(String[] args) {
		MainWIndow w = new MainWIndow();
	}
}

