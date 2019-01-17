/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */

@Component
public class CSVWriter {
	
	    private static final char DEFAULT_SEPARATOR = ',';

	    public static void writeLine(Writer w, List<String> values) throws IOException {
	        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	    }

	    public static void writeRecord(Writer w, List<List<String>> values) throws IOException {
	    	writeRecord(w, values, DEFAULT_SEPARATOR, ' ');
	    }
	    
	    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
	        writeLine(w, values, separators, ' ');
	    }

	    public Writer writeToExceptionLog(Writer writer,List<String> values, List<String> validateCollationData) {
  		  	StringBuilder sb = new StringBuilder();

	    	for(String str : validateCollationData){
	    		if(sb.length() > 0){
	    			sb.append(",");
	    		}
	    		sb.append(str);
	    	}
	    	
	    	List<String> tmpValues = new ArrayList<String>();
	    	tmpValues.add(0,String.format(" Date[%s] : Required values [%s] , are missing for enrty :",DateTime.now(),sb.toString()));
	    	tmpValues.addAll(values);

	    	try {
				writeExceptionLine( writer, tmpValues, DEFAULT_SEPARATOR, ' ');
	    	} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	    	return writer;
	    }
	    
	    public Writer writeToValidationExceptionLog(Writer writer,String...values) {
  		  	StringBuilder sb = new StringBuilder();
	    	
	    	List<String> tmpValues = new ArrayList<String>();
	    	tmpValues.add(0,String.format(" Date[%s] : Credit Card with Name :[%s] and Number [%s] is Invalid ",DateTime.now(),values[0],values[1]));

	    	try {
				writeExceptionLine( writer, tmpValues, DEFAULT_SEPARATOR, ' ');
	    	} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	    	return writer;
	    }


	    private static String followCVSformat(String value) {

	        String result = value;
	        if (result.contains("\"")) {
	            result = result.replace("\"", "\"\"");
	        }
	        return result;

	    }
	    public static void writeExceptionLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

	        boolean first = true;
	        
	        if (separators == ' ') {
	            separators = DEFAULT_SEPARATOR;
	        }

	        StringBuilder sb = new StringBuilder();
	        for (String value : values) {
	            if (!first) {
	                sb.append(",");;
	            }
	            if (customQuote == ' ') {
	                sb.append(followCVSformat(value));
	            } else {
	                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
	            }

	            first = false;
	        }
	        sb.append("\r\n");
	        w.append(sb.toString());
	    }

	    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

	        boolean first = true;
	        
	        if (separators == ' ') {
	            separators = DEFAULT_SEPARATOR;
	        }

	        StringBuilder sb = new StringBuilder();
	        for (String value : values) {
	            if (!first) {
	                sb.append("\r\n");;
	            }
	            if (customQuote == ' ') {
	                sb.append(followCVSformat(value));
	            } else {
	                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
	            }

	            first = false;
	        }
	        sb.append("\r\n");
	        w.append(sb.toString());
	    }
	    
	    public static void writeRecord(Writer w, List<List<String>> valueLists, char separators, char customQuote) throws IOException {

	        
	        if (separators == ' ') {
	            separators = DEFAULT_SEPARATOR;
	        }


	        for (List<String> list : valueLists) {
		        boolean first = true;
	        	List<String> tmpList = list;
		        StringBuilder sb = new StringBuilder();

	        	  for(String value : tmpList) {
	        	  
	  	            if (!first) {
	  	                sb.append(separators);
	  	            }
	  	            if (customQuote == ' ') {
	  	                sb.append(followCVSformat(value));
	  	            } else {
	  	                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
	  	            }
	  	            first = false;
	  	        }
	        	sb.append("\r\n");
	  	        w.append(sb.toString());
			}
	    }
}
