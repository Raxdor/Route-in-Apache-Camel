package MainPackage;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j // Used for logging
@Component

//Route
public class MyRoute extends RouteBuilder {

	// Overriding of method configure(), which defines the essence of the
	// application, when extending RouteBuilder
	@Override
	public void configure() throws Exception {
		Scanner input = new Scanner(System.in);
		log.info("Choose an operation:");
		log.info("1 - Copy all files:");
		log.info("2 - Move all files:");
		// Select an operation
		int a = input.nextInt();
		// PRESS ALT+TAB to select files in explorer if running application in IDE
		switch (a) {
		case 1: {
			System.setProperty("java.awt.headless", "false");
			String from = openFile("FROM:"); // folder, from which we will execute the function
			String to = openFile("TO:"); // folder, which сontains results of executed function
			copyAllFiles(from, to);
		}
			break;
		case 2: {
			System.setProperty("java.awt.headless", "false");
			String from = openFile("FROM:"); // folder, from which we will execute the function
			String to = openFile("TO:"); // folder, which сontains results of executed function
			moveAllFiles(from, to);
		}
			break;
		}
		System.setProperty("java.awt.headless", "true");
		log.info("End.");
	}

	// Function to copy files
	public void copyAllFiles(String from, String to) {
		from("file:" + from + "?noop=true").to("file:" + to);
	}

	// Function to move files (i.e removes files from path:from to path:to)
	public void moveAllFiles(String from, String to) {
		from("file:" + from + "?delete=true").to("file:" + to);
	}

	// Opening File Explorer
	public String openFile(String word) {
		JFileChooser file = new JFileChooser();
		file.setDialogTitle(word);
		String s = "";
		file.setMultiSelectionEnabled(true);
		file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // option to view both files and directories
		file.setFileHidingEnabled(false);
		if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = file.getSelectedFile();
			s = f.getPath(); // path to the selected folder
		}
		return s;
	}
}
