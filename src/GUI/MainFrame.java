package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Logic.Spy;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPutDownThe;
	private JTextField pathTextField;
	private Spy spy = new Spy();
	private boolean test = false;
	private int memoryImageCounter = 0;
	private int driveImageCounter = 0;
	private int memoryDataCounter = 0;
	private int driveDataCounter = 0;
	private String[] directories;
	private int totalFiles = 0;
	private int numberOfDirectories;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtPutDownThe = new JTextField();
		txtPutDownThe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPutDownThe.setEditable(false);
		txtPutDownThe.setText("Put down the path");
		txtPutDownThe.setBounds(10, 10, 274, 19);
		contentPane.add(txtPutDownThe);
		txtPutDownThe.setColumns(10);

		pathTextField = new JTextField();
		pathTextField.setBounds(10, 40, 274, 19);
		contentPane.add(pathTextField);
		pathTextField.setColumns(10);

		JList list = new JList();
		list.setBounds(10, 81, 274, 372);
		contentPane.add(list);

		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pathTextField.getText().isEmpty()) {
					JFrame frame = new JFrame("Dialog");
					JOptionPane.showMessageDialog(frame, "Directory can't be empty!");
				}

				else {
					memoryImageCounter = 0;
					driveImageCounter = 0;
					memoryDataCounter = 0;
					driveDataCounter = 0;
					totalFiles = 0;
					directories = spy.findAllDirectories(pathTextField.getText());
					list.setListData(directories);
					numberOfDirectories = directories.length;

				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(309, 9, 98, 50);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Spy");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (list.getSelectedIndex() == -1) {
					JFrame frame = new JFrame("Dialog");
					JOptionPane.showMessageDialog(frame, "Choose directory to spy!");
				}

				else {
					String dataHeader = "From memory";
					String imageHeader = "From memory";
					File info = spy.findDataFile(pathTextField.getText() + "\\" + list.getSelectedValue());
					if (info == null) {
						info = spy.findDataFile(pathTextField.getText() + "\\" + list.getSelectedValue());
						dataHeader = "From drive";
					}

					File image = spy.findImageFile(pathTextField.getText() + "\\" + list.getSelectedValue());
					if (image == null) {
						image = spy.findImageFile(pathTextField.getText() + "\\" + list.getSelectedValue());
						imageHeader = "From drive";

					}

					if (info != null) {
						JFrame f = new JFrame(dataHeader);
						f.setBounds(100, 100, 450, 300);
						f.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

						DataPanel panel = new DataPanel(info);
						f.getContentPane().add(panel);
						f.setPreferredSize(new Dimension(650, 400));
						f.pack();
						f.setVisible(true);
					}

					else {
						JFrame frame = new JFrame("Dialog");
						JOptionPane.showMessageDialog(frame, "Something went wrong. :(");
					}

					if (image != null) {
						JFrame f = new JFrame(imageHeader);
						f.setBounds(100, 100, 450, 300);
						f.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

						try {
							ImagePanel panel = new ImagePanel(image);
							f.getContentPane().add(panel);
							f.pack();
							f.setVisible(true);
						} catch (IOException e1) {

						}
					}

					else {
						JFrame frame = new JFrame("Dialog");
						JOptionPane.showMessageDialog(frame, "Something went wrong. :(");
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(309, 81, 98, 50);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Test");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (int i = 0; i < directories.length; i++) {
					
					totalFiles++;
					
					String dataHeader = "From memory";
					String imageHeader = "From memory";
					
					File info = spy.findDataFile(pathTextField.getText() + "\\" + directories[i]);
					
					if (info == null) {
						info = spy.findDataFile(pathTextField.getText() + "\\" + directories[i]);
						dataHeader = "From drive";
					}

					File image = spy.findImageFile(pathTextField.getText() + "\\" + directories[i]);
					
					if (image == null) {
						image = spy.findImageFile(pathTextField.getText() + "\\" + directories[i]);
						imageHeader = "From drive";

					}

					if (info != null) {
						if (dataHeader.equals("From drive"))
							driveDataCounter++;
						else
							memoryDataCounter++;
					}

					else {
						JFrame frame = new JFrame("Dialog");
						JOptionPane.showMessageDialog(frame, "Something went wrong. :(");
					}

					if (image != null) {
						if (imageHeader.equals("From drive"))
							driveImageCounter++;
						else
							memoryImageCounter++;
					}
				}
				System.out.println();
				System.out.println();
				System.out.println("Number of directories:" + numberOfDirectories);
				System.out.println("Number of directories opened:" + totalFiles);
				System.out.println("Image drive:" + driveImageCounter);
				System.out.println("Image memory:" + memoryImageCounter);
				System.out.println("Data drive:" + driveDataCounter);
				System.out.println("Data memory:" + memoryDataCounter);

			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(309, 201, 98, 52);
		contentPane.add(btnNewButton_2);
	}
}
