/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;

import za.co.pps.AbstractTest;
import za.co.pps.bank.mutual.common.beans.NABMessageCCHeaderDTO;
import za.co.pps.bank.mutual.common.beans.NABMessageDDHeaderDTO;
/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 28 Aug 2017
 * @version 1.0
 */
@Profile(value = "LOCAL" )
@FixMethodOrder(MethodSorters.DEFAULT)
@ActiveProfiles("LOCAL")
public class NABMessageCreaterTest extends AbstractTest {
	
	
	@Autowired
	NABMessageCreater messageCreater;
	
	@Autowired
	CSVWriter csvWriter;
	
	@Autowired
	PropertiesUtil propertiesUtil;

	@Autowired
	private FileReaderUtil fileUtil;
	
	
	@Value("${file.output.location}") 
	private String fileOutput;
	
	@Value("${file.output.location.creditcard}")
	private String fileOutputCreditCard;
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	@Ignore
	public void test_create_message(){
		
		String name = "sonwabo" ; 
		String surname = "singatha";
		String template = "                    ";
		StringBuilder builder = new StringBuilder( template);
	    
		builder.replace(2, 9, name);
	    builder.replace(11, 19, surname);
		builder.replace(0, 1, "t");
	     
	    System.out.println("===================");
	    System.out.println(">>:"+builder+":<<");
	    System.out.println( builder.toString().length());
	}
	
	@Test
	@Repeat(4)
	@Ignore
	public void test_create_nab_Direct_Debit_header_message(){
		
		String names[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR"}; 

		NABMessageCreater.NABHeaderMessageDD header = new NABMessageCreater().new NABHeaderMessageDD();
		header.setValues("0","01","NAB",names[new Random().nextInt(4)],"123456","SALARIES","280817");
	    String headerString = header.createNABHeaderMessage();//.replace(Character.MIN_VALUE, '@');  
	    String tmpHeaderStr = headerString.replaceAll("@", " ");
	    System.out.println("======== Header Message ========");
	    System.out.println(">>:"+tmpHeaderStr+":<<");
	    assertThat(tmpHeaderStr).doesNotContain("@");
	    assertThat(tmpHeaderStr).isNotNull();
	    assertThat(tmpHeaderStr.length()).isEqualTo(120);
	}
	
	
	
	@Test
	@Ignore
	public void test_create_nab_direct_debit_header_object_from_string(){
		
		String names[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR"}; 

		NABMessageCreater.NABHeaderMessageDD header = new NABMessageCreater().new NABHeaderMessageDD();
		header.setValues("0","01","NAB",names[new Random().nextInt(4)],"123456","SALARIES","030418");
	    String headerString = header.createNABHeaderMessage();//.replace(Character.MIN_VALUE, '@');  
	    String tmpHeaderStr = headerString.replaceAll("@", " ");
		 

	    NABMessageDDHeaderDTO headerDTO = header.createNABHeaderMessageObjectBean(tmpHeaderStr);
	    
	    assertThat(headerDTO.getRecordType()).isNotNull();
	    assertThat(headerDTO.getReelSequenceNumber()).isNotNull();
	    assertThat(headerDTO.getNameOfUserSupplyingFile()).isNotNull();
	    assertThat(headerDTO.getNumberOfUserSupplyingFile()).isNotNull();
	    assertThat(headerDTO.getDescriptionOfentriesOn()).isNotNull();
	    assertThat(headerDTO.getDateOfProcessing()).isNotNull();
	    assertThat(headerDTO.getCollectionDate()).isNotNull();
		
	}
	
	
	
	
	@Test
	@Repeat(6)
	@Ignore
	public void test_create_nab_Direct_Debit_details_message(){
		
		String names[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR","RAWLINSON. KENNETH FREDERICK", "BUTTRESS. MARTYN A & KAREN K"}; 
		String accountNumber[] = {"431796089","671103","409856509","13115","10824990"};
		
		NABMessageCreater.NABDirectEntryMessageDD entry = new NABMessageCreater().new NABDirectEntryMessageDD();
		entry.setValues("1","063-422", accountNumber[new Random().nextInt(5)] ," ","50","0000033716",names[new Random().nextInt(5)], "00005-05/02/00", "083-2354","431796089","AJAX CRACKERS","00004545");
		String entryStr = entry.createDiretEntryMessage();
		
		System.out.println("====== Details Message =========");
		System.out.println( entryStr);
		
		assertThat('-').isEqualTo(entryStr.charAt(83));
		assertThat(entryStr).isNotNull();
	    assertThat(entryStr.length()).isEqualTo(120);

	}
	
	@Test
	@Ignore
	public void test_create_nab_Direct_Debit_totals_message(){
		
		NABMessageCreater.NABTransactionTotalsDD totals = new NABMessageCreater().new NABTransactionTotalsDD();
		totals.setValues("7","999-999","0000000000","0000678325","0000678325","000011");
		String totalsStr = totals.createNABTotalsMessage();
		//recordType,bsb num,netTotal, creaditTotal, debitTotal, file
		System.out.println("====== Totals Message =========");
		System.out.println( totalsStr );

		assertThat(totalsStr).isNotNull();
	    assertThat(totalsStr.length()).isEqualTo(120);
		
	}
	
	
	@Test
	@Ignore
	public void test_write_nab_Direct_Debit_file() throws IOException{
		
		List<String> data = new ArrayList<String>();
		File outputFile = new File(fileOutput);
		FileWriter writer = null;
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		writer = new FileWriter(outputFile);
				
		
		//====== Create Header Information
		String names[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR"}; 
		NABMessageCreater.NABHeaderMessageDD header = new NABMessageCreater().new NABHeaderMessageDD();
		header.setValues("0","01","NAB",names[new Random().nextInt(4)],"123456","SALARIES","280817");
	    String headerString = header.createNABHeaderMessage().replace(Character.MIN_VALUE, '@');  
	    String tmpHeaderStr = headerString.replaceAll("@", " ");
	    
	    assertThat(tmpHeaderStr.length()).isEqualTo(120);
	    data.add(tmpHeaderStr);
	    ///============= Create Details 
	    for(int i=0;i<6;i++){
	    	String names_[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR","RAWLINSON. KENNETH FREDERICK", "BUTTRESS. MARTYN A & KAREN K"}; 
			String accountNumber[] = {"431796089","671103","409856509","13115","10824990"};
			
			NABMessageCreater.NABDirectEntryMessageDD entry = new NABMessageCreater().new NABDirectEntryMessageDD();
			entry.setValues("1","063-422", accountNumber[new Random().nextInt(5)] ," ","50","0000033716",names_[new Random().nextInt(5)], "00005-05/02/00", "083-2354","431796089","AJAX CRACKERS","00004545");
			String entryStr = entry.createDiretEntryMessage();
		    
			data.add(entryStr);
	    }
	    
	    //=============== Creat1e Totals
		NABMessageCreater.NABTransactionTotalsDD totals = new NABMessageCreater().new NABTransactionTotalsDD();
		String recordsTotal = StringUtils.padLeft(new Integer((data.size()-1)).toString() , 6);
		
		totals.setValues("7","999-999","0000000000","0000678325","0000678325",recordsTotal);
		String totalsStr = totals.createNABTotalsMessage();

		data.add(totalsStr);
	    
		csvWriter.writeLine(writer, data,'`');// I use the tilder as whitespace identifier
		
		writer.flush();
		writer.close();
		
	}
	
	
	@Test
	@Ignore
	public void test_create_nab_Credit_Card_header_message(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		NABMessageCreater.NABHeaderMessageCC header = new NABMessageCreater().new NABHeaderMessageCC();
		
		header.setValues( NABMessageCreater.RecordType.HEADER.getType(), 
				      new Integer(1).toString(),
				      new Integer(800).toString(),
				      dateFormat.format(new java.util.Date()),
				      new Integer(0).toString()
				       );
		
		List<String> entries = header.createEntries();
		assertThat(entries).isNotEmpty();

		System.out.println( entries.toString());
	}
	
	@Test 
	@Ignore
	public void test_create_nab_Credit_Card_header_object_from_string(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		NABMessageCreater.NABHeaderMessageCC header = new NABMessageCreater().new NABHeaderMessageCC();
		
		header.setValues( NABMessageCreater.RecordType.HEADER.getType(), 
				      new Integer(1).toString(),
				      new Integer(800).toString(),
				      dateFormat.format(new java.util.Date()),
				      new Integer(0).toString()
				       );
		
		List<String> entries = header.createEntries();
		NABMessageCCHeaderDTO headerDTO = header.createNABHeaderMessageObjectBean(entries.toString());

		assertThat(headerDTO.getRecordCount()).isNotNull();
		assertThat(headerDTO.getBatchFileVersion()).isNotNull();
		assertThat(headerDTO.getClientID()).isNotNull();
		assertThat(headerDTO.getFileCreationDate()).isNotNull();
		assertThat(headerDTO.getRecordCount()).isNotNull();
	
	}
	
	@Test 
	@Ignore
	public void test_get_collection_date_from_header_column_for_direct_debit() throws Exception{
		
		String ddOutputFolder = propertiesUtil.getNabMessageDDOutput();
		File ddLatestFile = fileUtil.getLatestFile(ddOutputFolder);
		 
		List<String> ddFileContent = fileUtil.readContentFromFile(ddLatestFile);
		String[] str = ddFileContent.get(0).split(" ");
		
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		Date date = format.parse(str[str.length-1]);
	    
	}
	
	@Test 
	@Ignore
	public void test_get_collection_date_from_header_column_for_credit_card() throws Exception{
		
		String ccOutputFolder = propertiesUtil.getNabMessageCCOutput();
		File ccLatestFile = fileUtil.getLatestFile(ccOutputFolder);
		 
		List<String> ddFileContent = fileUtil.readContentFromFile(ccLatestFile);
		String[] str = ddFileContent.get(0).split(",");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse(str[3]);
	    
	}
	
	
	
	
	
	@Test
	@Ignore
	public void test_create_nab_Credit_Card_detail_message(){
		
		String names[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR","RAWLINSON. KENNETH FREDERICK", "BUTTRESS. MARTYN A & KAREN K"}; 
		String accountNumber[] = {"431796089","671103","409856509","13115","10824990"};

		NABMessageCreater.NABDirectEntryMessageCC details = new NABMessageCreater().new NABDirectEntryMessageCC();
		
		details.setValues(NABMessageCreater.RecordType.DETAILS.getType() 
					,new Integer(29835907).toString()
					,NABMessageCreater.TransactionType.PAY.name()
					,"E"
					,"1200000"//Memeber Number
					,"5555 5555 5555 5555"
					,"1212"
					,"AUD"
					,"56254"
					,""
					,"Sonwabo Singatha"
				);
		
		List<String> entries = details.createEntries();
		assertThat(entries).isNotEmpty();
		System.out.println( entries.toString());
		
	}
	
	@Test
	@Ignore
	public void test_create_nab_Credit_Card_trailer_message(){

		NABMessageCreater.NABTransactionTotalsCC trailer = new NABMessageCreater().new NABTransactionTotalsCC();
		trailer.setValues(NABMessageCreater.RecordType.CC_TOTALS.getType()
				,"AUD"
				,"10"
				,"23555"
				,"0"
				,"0"
				);
		
		List<String> entries = trailer.createEntries();
		assertThat(entries).isNotEmpty();
		System.out.println( entries.toString());
	}
	
	@Test
	@Ignore
	public void test_write_nab_Credit_Card_file() throws Exception{
		
		List<List<String>> data = new ArrayList<List<String>>();
		File outputFile = new File(fileOutputCreditCard);
		FileWriter writer = null;
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		writer = new FileWriter(outputFile);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		NABMessageCreater.NABHeaderMessageCC header = new NABMessageCreater().new NABHeaderMessageCC();
		
		header.setValues( NABMessageCreater.RecordType.HEADER.getType(), 
				      new Integer(1).toString(),
				      new Integer(800).toString(),
				      dateFormat.format(new java.util.Date()),
				      new Integer(0).toString()
				       );
		
		List<String> heardEntry = header.createEntries();
		data.add(heardEntry);

		String names[] = {"Sonwabo. Singatha","James. Fischer", "Jan. Kos","SANDERS. LEE TREVOR","RAWLINSON. KENNETH FREDERICK", "BUTTRESS. MARTYN A & KAREN K"}; 
		String memberNumbers[] = {"431796089","671103","409856509","13115","10824990"};
		String premiums[] = {"651","986","654","985","500","200"};
		

		for(int i = 0; i<7; i++){
			NABMessageCreater.NABDirectEntryMessageCC details = new NABMessageCreater().new NABDirectEntryMessageCC();
			
			details.setValues(NABMessageCreater.RecordType.DETAILS.getType() 
						,new Integer(29835907).toString()
						,NABMessageCreater.TransactionType.PAY.name()
						,"E"
						,memberNumbers[new Random().nextInt(memberNumbers.length)]//Memeber Number
						,"5555 5555 5555 5555"
						,"1212"
						,"AUD"
						,"56254"
						,""
						,names[new Random().nextInt(names.length)]
					);
			
			List<String> entries = details.createEntries();
			data.add(entries);
		}
		
		NABMessageCreater.NABTransactionTotalsCC trailer = new NABMessageCreater().new NABTransactionTotalsCC();
		trailer.setValues(NABMessageCreater.RecordType.CC_TOTALS.getType()
				,"AUD"
				,"10"
				,"23555"
				,"0"
				,"0"
				);
		
		List<String> trailerEntries = trailer.createEntries();
		data.add(trailerEntries);   

		csvWriter.writeRecord(writer, data);
		writer.flush();
		writer.close();
		
	}
	
	
	//@Test
	public static void main(String...strings){
		
		String na = "21a5bc93-9a4f-4c77-944f-2297533f01e6_DirectEntry.csv";
		
		String[] ts = na.split("\\.");
		List<String> ls = Arrays.asList(ts);
		
		System.out.println(ls.toString());
		
	
	}
	
	
	
	
}