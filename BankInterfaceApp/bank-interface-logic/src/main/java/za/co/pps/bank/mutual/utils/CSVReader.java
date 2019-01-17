/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Javadoc
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */

@Component
public class CSVReader {

	private static final Logger LOG = LoggerFactory.getLogger(CSVReader.class);
	
	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';
	
	public String printCSVFileContent(File csvfile) throws FileNotFoundException{
		 StringBuilder builder = new StringBuilder();
		 Scanner scanner = new Scanner(csvfile);
	     while (scanner.hasNext()) {
	        List<String> line = parseLine(scanner.nextLine());
	        if(builder.length() > 0){
	        	builder.append("\n");
	        }
	        builder.append(line.toString());
	     }
	     scanner.close();
	     return builder.toString();
	}
	
	public  List<List<String>>  readCSVFile(File csvfile) throws FileNotFoundException, Exception{
		 List<List<String>> listEntries = new ArrayList<List<String>>();
		 
		 /*
		 Scanner scanner = new Scanner(csvfile);
	     while (scanner.hasNext()) {
	        List<String> line = parseLine(scanner.nextLine());
	        listEntries.add(line);
	     }
	     scanner.close();
	     */
	     BufferedReader breader = new BufferedReader(new FileReader(csvfile));
	     String st;
	     while ((st = breader.readLine()) != null){
	    	 List<String> line = parseLine(st);
		     listEntries.add(line);
	     }
	     return listEntries;
	}
	private static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    private static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }
    
    private static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<String>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {
                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }
        }
        result.add(curVal.toString().trim());
        return result;
    }
	
}
