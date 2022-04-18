package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public DataPanel(File file) {
		setLayout(null);
		String text = "";
		try (Scanner s = new Scanner(file)) {
			s.useDelimiter(";|\\r?\\n|\\r");
			while (s.hasNext()) {
				text = text + s.nextLine() +"\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(text);
		textArea.setBounds(10, 10, 430, 280);
		add(textArea);

	}
}
