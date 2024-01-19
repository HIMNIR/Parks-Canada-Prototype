import convert.PdfToText;
import convert.ToExcel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class DisplayManager {
	private static JFrame frame;
	private static JTextField folderPathField;
	private static JTextField outputPathField;
	private static JRadioButton banffRadioButton;
	private static JRadioButton mrgRadioButton;
	private static JRadioButton jasperRadioButton;

//
//
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			createAndShowGUI();
		});
	}

	private static void createAndShowGUI() {
		frame = new JFrame("PDF Conversion Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0, 1));

		// Folder Path
		JPanel folderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		folderPanel.add(new JLabel("Enter folder path: "));
		folderPathField = new JTextField(30);
		folderPanel.add(folderPathField);
		frame.add(folderPanel);

		// Output Path
		JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		outputPanel.add(new JLabel("Enter excel path: "));
		outputPathField = new JTextField(30);
		outputPanel.add(outputPathField);
		frame.add(outputPanel);

		// Radio Buttons
		JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		banffRadioButton = new JRadioButton("Licenses issued by Banff");
		mrgRadioButton = new JRadioButton("Licenses issued by MRG");
		jasperRadioButton = new JRadioButton("Licenses issued by Jasper");

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(banffRadioButton);
		radioGroup.add(mrgRadioButton);
		radioGroup.add(jasperRadioButton);

		radioPanel.add(banffRadioButton);
		radioPanel.add(mrgRadioButton);
		radioPanel.add(jasperRadioButton);
		frame.add(radioPanel);

		// Convert Button
		JButton convertButton = new JButton("Convert PDFs");
		convertButton.addActionListener(new ConvertButtonListener());
		frame.add(convertButton);

		// Apply some styling (adjust as needed)
		folderPathField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		outputPathField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		convertButton.setFont(new Font("Arial", Font.BOLD, 14));
		banffRadioButton.setFont(new Font("Arial", Font.PLAIN, 12));
		mrgRadioButton.setFont(new Font("Arial", Font.PLAIN, 12));
		jasperRadioButton.setFont(new Font("Arial", Font.PLAIN, 12));

		frame.pack();
		frame.setVisible(true);
	}

	private static class ConvertButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String folderPath = folderPathField.getText();

			String outputPath = outputPathField.getText();

			int choice = 0;

			if (banffRadioButton.isSelected()) {
				choice = 1;
			} else if (mrgRadioButton.isSelected()) {
				choice = 2;
			} else if (jasperRadioButton.isSelected()) {
				choice = 3;
			}

			File outputFile = new File(outputPath);

			File folder = new File(folderPath);

			File[] files = folder.listFiles();

			if (files != null && files.length > 0) {

				for (File file : files) {

					if (file.isFile()) {

						PdfToText.convertNew(file);
						try {
							switch (choice) {
							case 1:
								PdfToText.convertBanff();
								ToExcel.Banff(outputFile);
								break;
							case 2:
								PdfToText.convertMRG();
								ToExcel.MRG(outputFile);
								break;
							case 3:
								PdfToText.convertJasper();
								ToExcel.Jasper(outputFile);
								break;
							default:
								break;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			} else {

				System.out.println("No PDF files found in the specified folder.");
			}
		}
	}
}
