package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;

public class NotepadGUIReal extends JFrame {
	public String appName = " - Notepad But Better";
	public String filename = "Untitled";
	public Clipboard clipBoard = getToolkit().getSystemClipboard();
	public boolean wordWrapOn = false;
	int fontSize = 12;
	public String fontType = "ARIAL";
	public Font f = new Font(fontType,Font.PLAIN,fontSize);
	
	private JPanel contentPane;
	private JMenuItem New;
	private JMenuItem Open;
	private JMenuItem Save;
	private JMenuItem Exit;
	private JMenuItem Paste;
	private JMenuItem Cut;
	private JMenuItem Copy;
	private JTextArea textArea;
	private JMenu Format;
	private JMenuItem saveAs;
	private JMenuItem WordWrap;
	private JMenuItem fontIncrease;
	private JMenuItem fontDecrease;
	private JMenu styleFont;
	private JMenuItem typeOne;
	private JMenuItem typeTwo;
	private int index = 0;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotepadGUIReal frame = new NotepadGUIReal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NotepadGUIReal() {

	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 420);
		this.setTitle("Untitled" + appName);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		New = new JMenuItem("New");
		New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				findField.setText("");
				replaceField.setText("");
				filename = null;
				setTitle("Untitled" + appName);
			}
		});
		fileMenu.add(New);
		
		Open = new JMenuItem("Open");
		Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				FileDialog fileDialog = new FileDialog(NotepadGUIReal.this,"Open File", FileDialog.LOAD);
				fileDialog.setVisible(true);
				fileDialog.setTitle(appName);
				
				if (fileDialog.getFile() != null) {
					filename = fileDialog.getDirectory() + fileDialog.getFile();
					setTitle(filename + appName);
				}
				try {
					BufferedReader reader = new BufferedReader(new FileReader(filename));
					StringBuilder sb = new StringBuilder();
					
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
						textArea.setText(sb.toString());
						findField.setText("");
						replaceField.setText("");
					}
					reader.close();
				} catch (IOException e) {
					System.out.println("File Not Found");
				}
		}});
		fileMenu.add(Open);
		
		saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				FileDialog fileDialog = new  FileDialog(NotepadGUIReal.this,"Save File",FileDialog.SAVE);
				fileDialog.setVisible(true);
				
				if(fileDialog.getFile() != null) {
					filename = fileDialog.getDirectory() + fileDialog.getFile();
					setTitle(filename + appName);
				}
				try {
					FileWriter saver = new FileWriter(filename);
					saver.write(textArea.getText());
					setTitle(filename + appName);
					saver.close();
				} catch (Exception e) {
					System.out.println("Unable to Save");
				}
			}
		});
		fileMenu.add(saveAs);
		
		Exit = new JMenuItem("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		Save = new JMenuItem("Save");
		Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				if (filename == null) {
		
				}
				else {
				try {
					FileWriter saveas = new FileWriter(filename);
					saveas.write(textArea.getText());
					setTitle(filename + appName);
					saveas.close();
				} catch (Exception exe) {
					System.out.println("Maybe Something Wrong!");
				}
			}
			}
		});
		fileMenu.add(Save);
		fileMenu.add(Exit);
		
		
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		Cut = new JMenuItem("Cut");
		Cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cutString = textArea.getSelectedText();
				StringSelection cutSelection = new StringSelection(cutString);
				clipBoard.setContents(cutSelection, cutSelection);
				textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
			}
		});
		editMenu.add(Cut);
		
		Copy = new JMenuItem("Copy");
		Copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String copyText = textArea.getSelectedText();
				StringSelection copySelection = new StringSelection(copyText);
				clipBoard.setContents(copySelection, copySelection);
			}
		});
		editMenu.add(Copy);
		
		Paste = new JMenuItem("Paste");
		Paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Transferable pasteText = clipBoard.getContents(NotepadGUIReal.this);
					String sel = (String) pasteText.getTransferData(DataFlavor.stringFlavor);
					textArea.replaceRange(sel, textArea.getSelectionStart(), textArea.getSelectionEnd());
				
				}catch (Exception exe) {
			System.out.println("Unsuccessful");	
			}
		}});
		editMenu.add(Paste);
		
		
		Format = new JMenu("Format");
		menuBar.add(Format);
		
		WordWrap = new JMenuItem("Word Wrap: OFF");
		WordWrap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wordWrapOn == false) {
					wordWrapOn = true;
					textArea.setLineWrap(true);
					textArea.setWrapStyleWord(true);
					WordWrap.setText("Word Wrap: ON");
				}
				else if (wordWrapOn = true) {
					wordWrapOn = false;
					textArea.setLineWrap(false);
					textArea.setWrapStyleWord(false);
					WordWrap.setText("Word Wrap: OFF");
				}
			}
		});
		Format.add(WordWrap);
		
		fontIncrease = new JMenuItem("Increase Font Size");
		fontIncrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        textArea.setFont(new java.awt.Font(fontType, 0, fontSize+=2)); 
			}
		});
		
		styleFont = new JMenu("Font Styles");
		Format.add(styleFont);
		
		typeOne = new JMenuItem("Times New Roman");
		typeOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontType = "Times New Roman";
		        textArea.setFont(new java.awt.Font(fontType, 0, fontSize)); 
			}
		});
		styleFont.add(typeOne);
		
		typeTwo = new JMenuItem("Calibri");
		typeTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontType = "Calibri";
		        textArea.setFont(new java.awt.Font(fontType, 0, fontSize)); 
			}
		});
		styleFont.add(typeTwo);
		
		JMenuItem typeThree = new JMenuItem("Italics");
		typeThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        textArea.setFont(new java.awt.Font(fontType, Font.ITALIC, fontSize)); 
			}
		});
		
		typeFour = new JMenuItem("Monospaced");
		typeFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontType = "Monospaced";
		        textArea.setFont(new java.awt.Font(fontType, 0, fontSize)); 
			}
		});
		styleFont.add(typeFour);
		styleFont.add(typeThree);
		
		JMenuItem typeFive = new JMenuItem("BOLD");
		typeFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        textArea.setFont(new java.awt.Font(fontType, Font.BOLD, fontSize)); 
			}
		});
		styleFont.add(typeFive);
		Format.add(fontIncrease);
		
		fontDecrease = new JMenuItem("Decrease Font Size");
		fontDecrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        textArea.setFont(new java.awt.Font(fontType, 0, fontSize-=2)); 
			}
		});
		Format.add(fontDecrease);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		textArea.setFont(f);
		
		
		panel = new Panel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		findField = new TextField();
		findField.setColumns(10);
		findField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTextArea(textArea, findField.getText());
			}
		});
		panel.add(findField);
		
		findButton = new Button("Find");
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTextArea(textArea, findField.getText());
			}
		});
		panel.add(findButton);
		
		replaceField = new TextField();
		replaceField.setColumns(10);
		panel.add(replaceField);
		
		repButton = new Button("Replace");
		repButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String from = textArea.getText();

					if(from != null)
	               {
	            	   String findr = findField.getText();
	                  String replacer = replaceField.getText();
	                  if(index >= 0 && replacer.length() > 0)
	                  {
	                     int end = index;
	                     textArea.replaceRange(replacer, index - findr.length(), end);
	                     index = textArea.getCaretPosition();
	                  }
	               }
			}
		});
		panel.add(repButton);
	}
	
	class myHighliter extends DefaultHighlighter.DefaultHighlightPainter {
		public myHighliter (Color c) {
			super(c);
		}
	}
	DefaultHighlighter.HighlightPainter highlighter = new myHighliter(Color.yellow);
	
	private Panel panel;
	private TextField findField;
	private TextField replaceField;
	private Button findButton;
	private Button repButton;
	private JMenuItem typeFour;

	public void searchTextArea(JTextComponent textComp, String textString){
		removeHighLight(textComp);
		try {
			Highlighter hilight = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			
			index = 0;
			int i = index;
			while ((i = text.toUpperCase().indexOf(textString.toUpperCase(),i))>=0) {
				hilight.addHighlight(i, i+textString.length(), highlighter);
				i += textString.length();
				//index = pos;
				index = i;
			}
			} catch (Exception e) {
				
			}
	}
	public void removeHighLight(JTextComponent textComp){
	Highlighter removeHighlighter = textComp.getHighlighter();
	Highlighter.Highlight[] remove = removeHighlighter.getHighlights();
	
	for (int i = 0; i < remove.length;i++) {
		if (remove[i].getPainter() instanceof myHighliter) {
			removeHighlighter.removeHighlight(remove[i]);
		}
	}
	}	

	public TextField getTextField() {
		return findField;
	}
}
