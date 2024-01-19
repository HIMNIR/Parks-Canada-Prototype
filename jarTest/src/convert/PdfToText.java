
package convert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import java.util.ArrayList;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * This class converts PDF content to text and performs various text processing
 * tasks.
 */
public class PdfToText {
// doc file to store contents read from PDF doc
	public static File f;
	/**
	 * Converts a PDF document and extracts content from the first page.
	 */
	public static void convertNew(File file) {
		f = new File("PDFdoc");

		String firstPageText = "";
		try {
// Load the PDF document
			PDDocument doc = PDDocument.load(file);
			PDFTextStripper ts = new PDFTextStripper();

// Set the desired page range (1 to 1) to read only the first page
			ts.setStartPage(1);
			ts.setEndPage(1);
			firstPageText = ts.getText(doc);
			writeFile(firstPageText);
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the content to a file named "document" in the "src" directory.
	 *
	 * @param x The content to write.
	 */
	public static void writeFile(String x) {
		try {
			FileWriter fr = new FileWriter(f);
			BufferedWriter br = new BufferedWriter(fr);
			br.write(x);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the content of the PDF file issued by Banff National Park, removing
	 * irrelevant lines, and populates the `tempList` ArrayList with relevant data.
	 */
	public static ArrayList<String> convertBanff() {
		String businessYear = "BUSINESS LICENCE 2022-23";
		ArrayList<String> arrList = new ArrayList<String>();
		try {
// Read the content from the PDF file
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String lineofText;
			while ((lineofText = br.readLine()) != null) {
				arrList.add(lineofText);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

// Remove unnecessary lines from the beginning of the ArrayList
//Changes needed year?
// Iterate through the array list to process and filter relevant data
		for (int i = arrList.size() - 1; i >= 0; i--) {

			if (arrList.get(i).contains("Location:") || arrList.get(i).contains("Licence Fee:")
					|| arrList.get(i).contains("Expiry Date:")
					|| arrList.get(i).contains("Valid within (\"License Area(s)")
					|| arrList.get(i).equalsIgnoreCase("AB")

					|| arrList.get(i).equalsIgnoreCase("BUSINESS LICENCE 2023-24")
					|| arrList.get(i).contains("BUSINESS LICENCE 2022-23")
					|| arrList.get(i).equalsIgnoreCase(businessYear.trim())

					|| arrList.get(i).contains("Valid Within / (\"Licence Area(s)\"):")
					|| arrList.get(i)
							.contains("business, and no others.  The operation of any other business without an")
					|| arrList.get(i).contains("appropriate licence is strictly prohibited.")
					|| arrList.get(i).contains("GUIDED / OUTFITTER ACTIVITY BUSINESS LICENCE 2022-23")
					|| arrList.get(i).contains("The licence entitles the licensee to engage in the above enumerated")
					|| arrList.get(i).contains(
							"The licence entitles the licensee to engage in the above enumerated business, and no others. The operation of any other business")
					|| arrList.get(i).contains("BUSINESS LICENCE 2023-24")
					|| arrList.get(i).contains("Type of Business:") || arrList.get(i).contains("Issued at")
					|| arrList.get(i).contains(
							"The licence entitles the licensee to engage in the above enumerated business, and no others.  The operation of any other business ")
					|| arrList.get(i).contains("O/A (\"Licensee\"):")
					|| arrList.get(i).contains("This business licence is issued under the following authority:")
					|| arrList.get(i).equals("without an appropriate licence is strictly prohibited.")
					|| arrList.get(i).contains("Section 4.1 of the National Parks of Canada Businesses Regulations")
					|| arrList.get(i).contains("Date of Issue:")) {
				arrList.remove(i);
			}

		}
		int deleteIndex = 0;

		for (int i = 0; i < arrList.size(); i++) {
			if (arrList.get(i).contains("Issued to:") || arrList.get(i).contains("Issued to")) {
				deleteIndex = i;
			}

		}
		for (int i = (deleteIndex); i >= 0; i--) {
			arrList.remove(i);
		}

		for (int i = 0; i < arrList.size(); i++) {
			if (arrList.get(i).equals("Issued at : Banff National Park")) {
				String rarry[] = arrList.get(i).split(" : ");
				arrList.set(i, rarry[0]);
				arrList.add(i + 1, rarry[1]);
			}

		}

		if (arrList.get(3).length() < 7) {
			arrList.set(2, arrList.get(2) + arrList.get(3));
			arrList.remove(3);
		}

		int occurence = 0;
		for (int i = 0; i < arrList.size(); i++) {
			if (arrList.get(i).equals("Banff National Park")) {
				occurence++;
			}
			if (occurence == 2) {
				arrList.remove(arrList.get(arrList.indexOf("Banff National Park")));
				break;
			}
		}
		arrList.set(2, arrList.get(2) + " " + arrList.get(4));
		arrList.remove(4);
		for (int i = 0; i < arrList.size(); i++) {
			if (arrList.get(i).equals("MT. REVELSTOKE/GLACIER NATIONAL ")) {
				arrList.set(i, arrList.get(i) + arrList.get(i + 1));
				arrList.remove(i + 1);
			}

		}

		String builder = "";
		for (int i = arrList.size() - 1; i >= 0; i--) {
			if (arrList.get(i).contains("NATIONAL PARK") && i > 5) {
			
				builder = builder + " , " + arrList.get(i);
				arrList.remove(i);
			}
		}
		arrList.add(builder.trim());
// Print the final modified arrList
		String builder2 = "";
		int endIndex = arrList.size() - 1;

		if ((endIndex - 6) > 1) {
			for (int i = endIndex - 1; i >= 7; i--) {
				builder2 = builder2 + arrList.get(i);
				arrList.remove(i);
			}

		}

		arrList.add(arrList.size() - 1, builder2);

		return arrList;
	}

//
//    /**
//     * Processes the content of the PDF file issued by MRG National Park, removing irrelevant lines,
//     * and populates the `tempList` ArrayList with relevant data.
//     */
	public static ArrayList<String> convertMRG() {
		ArrayList<String> arrList = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();
		String outputFileName = "src/conversionDocs/tempDoc";

		try {
// Read the content from the "document" file
			BufferedReader inputFile = new BufferedReader(new FileReader(f));
			BufferedWriter outputFile = new BufferedWriter(new FileWriter(outputFileName));
			String lineOfText;

// Remove any leading or trailing whitespace and write non-empty lines to
// "tempDoc" file
			while ((lineOfText = inputFile.readLine()) != null) {
				lineOfText = lineOfText.trim();
				if (!lineOfText.isEmpty()) {
					outputFile.write(lineOfText);
					outputFile.write("\n");
				}
			}
			inputFile.close();
			outputFile.close();

// Read content from "tempDoc" file and process it further
			BufferedReader reader = new BufferedReader(new FileReader("src/conversionDocs/tempDoc"));
			String read;

// Loop through each line and process the ArrayList
			while ((read = reader.readLine()) != null) {
				arrList.add(read);

			}
			reader.close();
		
			int indexCount = 0;

			for (int i = arrList.size() - 1; i >= 0; i--) {
				if (arrList.get(i).contains("Licence Fee:")) {
					indexCount = i;
				}
			}
			for (int i = arrList.size() - 1; i > indexCount; i--) {
				arrList.remove(i);
			}

			for (int i = arrList.size() - 1; i >= 0; i--) {
				if ((arrList.get(i).contains("Type of Business:")) || (arrList.get(i).contains("O/A:"))
						|| (arrList.get(i).contains("Location:")) || (arrList.get(i).contains("Valid within:"))) {
					arrList.remove(i);
				}
				if (arrList.get(i).contains("Issued to:")) {
					String rarry[] = arrList.get(i).split("(“Licensee”)");
					arrList.set(i, rarry[0]);
					arrList.add(i + 1, rarry[1]);
				}

			}
			for (int i = 0; i < arrList.size(); i++) {
				if (arrList.get(i).contains(":")) {

					String rarry[] = arrList.get(i).split(":");
					arrList.set(i, rarry[0].trim());
					arrList.add(i + 1, rarry[1].trim());
				}
			}
			for (int i = arrList.size() - 1; i >= 0; i--) {
				if ((arrList.get(i).contains("Licence Fee") || (arrList.get(i).contains("B US I NE SS  L I CE N C E"))

						|| (arrList.get(i).contains("Issued to")) || (arrList.get(i).contains(") Issued at"))
						|| (arrList.get(i).contains("Business Address")) || (arrList.get(i).contains("Date of Issue"))
						|| (arrList.get(i).contains("Commencement Date"))
						|| (arrList.get(i).contains("Expiry Date")))) {

					arrList.remove(i);
				}
			}
			indexCount = 0;

			int count = 0;

			String builder = "";
//
			for (int i = arrList.size() - 1; i >= 0; i--) {
				if (arrList.get(i).contains("Mount Revelstoke and Glacier National Parks")) {
					count = i;
				}
				if (arrList.get(i).contains("National Park") && !arrList.get(i).contains("National Parks")) {
					count++;
					builder = builder + " , " + arrList.get(i);
					arrList.remove(i);
				}

			}

			for (int i = count; i >= 0; i--) {
				arrList.remove(i);
			}

			arrList.add(builder.trim());


			String regex = "\\b(?:January|February|March|April|May|June|July|August|September|October|November|December)\\s\\d{1,2},\\s\\d{4}\\b";

			Pattern pattern = Pattern.compile(regex);
			for (int i = arrList.size() - 1; i >= 0; i--) {
				Matcher matcher = pattern.matcher(arrList.get(i));
				if (matcher.find()) {
					count = i;

				}
			}
			tempList.removeAll(tempList);
			builder = "";
			for (int i = 5; i < count; i++) {
				builder = builder + " " + arrList.get(i);
				tempList.add(arrList.get(i));
			}
			arrList.removeAll(tempList);
			arrList.add(builder.trim());

// reference no
// issued to
// issued at
// O/A

// date of issue
// commencement date
// expiry date
// type of business
// fee
// valid at

//
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;

	}

	public static ArrayList<String> convertJasper() {
		String businessYear = "BUSINESS LICENCE 2022-23";
		ArrayList<String> arrList = new ArrayList<String>();
		ArrayList<String> deleteList = new ArrayList<String>();
		try {
// Read the content from the PDF file
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String lineofText;
			while ((lineofText = br.readLine()) != null) {
				arrList.add(lineofText);
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < arrList.size(); i++) {
			if (arrList.get(i).isBlank()) {
				deleteList.add(arrList.get(i));
			}
		}
		arrList.removeAll(deleteList);
		deleteList.removeAll(deleteList);
		int deleteIndex = 0;
		
		for (int i = 0; i < arrList.size(); i++) {
			if (arrList.get(i).contains("Signed and Delivered by:")
					|| arrList.get(i).contains("Signed and Delivered by:")) {
				deleteIndex = i;
			}

		}
		for (int i = deleteIndex; i <= arrList.size() - 1; i++) {
			deleteList.add(arrList.get(i));
		}
		arrList.removeAll(deleteList);
		deleteList.removeAll(deleteList);
		for (int i = arrList.size() - 1; i >= 0; i--) {

			if (arrList.get(i).contains("Park Location/") || arrList.get(i).contains("Lieu")
					|| arrList.get(i).contains("LOT BLOCK")
					|| arrList.get(i).contains("Valid within (\"License Area(s)")
					|| arrList.get(i).contains("Type of Business/")
					|| arrList.get(i).contains("TYPE OF BUSINESS/ TYPE D’ENTREPRISE  FEES/  DRO ITS")
					|| arrList.get(i).equalsIgnoreCase(businessYear.trim())

					|| arrList.get(i).contains("Valid within/ Territoire visé")
					|| arrList.get(i).contains("TYPE OF BUSINESS/ TYPE D’ENTREPRISE  FEES/  DROITS")
					|| arrList.get(i).contains("Titulaire") || arrList.get(i).contains("Issued at/")
					|| arrList.get(i).contains("Délivrance JASPER NATIONAL PARK")
					|| arrList.get(i).contains("2023 – 2024 BUSINESS LICENCE/PERMIS D’EXPLOITATION COMMERCIALE")
					|| arrList.get(i).contains("Issued to/")
					|| arrList.get(i).contains(
							"This Business Licence may be executed using facsimile of signatures, and a facsimile of a signature shall be deemed to be the same, and equally enforceable, as an original of")
					|| arrList.get(i).contains("Date") || arrList.get(i).contains("Type d’Entreprise DELIVERY SERVICE")
					|| arrList.get(i).contains("Date of Issue/")
					|| arrList.get(i).contains(
							"The licence entitles the licensee to engage in the above enumerated business, and no others.  The operation of any other business ")
					|| arrList.get(i).contains("Lieu de") || arrList.get(i).contains("Date de ")
					|| arrList.get(i).contains(
							"such signature. / Le présent permis d’exploitation de commerce est valide même lorsque les signatures sont transmises par voie électronique. Ces signatures transmises")
					|| arrList.get(i).contains(
							"électroniquement sont réputées être identiques aux signatures originales et avoir la même force exécutoire.")
					|| arrList.get(i).contains("Expiry Date/")) {
				arrList.remove(i);
			}

		}
		deleteList.removeAll(deleteList);
		String builder = "";
		for (int i = arrList.size() - 1; i >= 0; i--) {
			if (arrList.get(i).contains("$")) {
// String rarry[] = arrList.get(i).split(" ");
// arrList.set(i, rarry[0]);
// arrList.add(i + 1, rarry[1]);
				builder = builder + " , " + arrList.get(i);
				arrList.remove(i);

			}

		}

		arrList.set(arrList.size() - 2, arrList.get(arrList.size() - 2) + "  " + arrList.get(arrList.size() - 1));
		arrList.remove(arrList.size() - 1);
		arrList.add(builder.trim());
		builder = "";
		for (int i = arrList.size() - 1; i >= 0; i--) {
			if (arrList.get(i).contains("N:")) {
				deleteIndex = i;
			}
		}
		for (int i = arrList.size() - 3; i > deleteIndex; i--) {
			builder = builder + " ," + arrList.get(i);
			arrList.remove(i);
		}

		arrList.add(builder.trim());

		return arrList;
	}

}
