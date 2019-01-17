/**
 * 
 */
package za.co.pps.bank.mutual.data.disbursement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import za.co.pps.AbstractTest;
import za.co.pps.bank.mutual.utils.CSVWriter;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 07 Sep 2017
 * @version 1.0
 */
@Profile(value = "LOCAL" )
@ActiveProfiles("LOCAL")
public class DisbursementReportTest extends AbstractTest{
	
	
    @Autowired
    private CSVWriter csvwriter;
    
	@Value("${file.output.disbursement.report.location}") 
	private String fileOutput;
	
	private File file;
	
    @Before
	public void setUp() throws IOException{
	   
       String fileName = "Payments.DISBURSEMENT.RPT";
       String fileLocation = fileOutput.concat(fileName);
       this.file = new File( fileLocation );
       if(!this.file.exists()){
    	   this.file.delete();
    	   this.file.createNewFile();
       }
    	
	}
	
	
	@Test
	public  void test_create_and_read_disbursement_report() throws IOException {
		
		FileWriter fileWriter = new FileWriter(this.file);
		
		DisbursementReportFile.Header header = new DisbursementReportFile().new Header();
		
		header.setBankName("National Australia Bank");
		header.setProductName("Direct Link");
		header.setReportName("Direct Link - Direct Credit Disbursement Report");
		header.setRunDate("09/07/2017");
		header.setRunTime(System.currentTimeMillis()+"");
		header.setFundId("123456");
		header.setCustomerName("Sonwabo Singatha");
		header.setImportFileName("Original file name from CPH envelope");
		header.setPaymentDate("09/07/2017");
		header.setBatchNoLinks("156456456646");
		header.setExportFileName("NAB Connect file name field");
		header.setMeId("95231");
		header.setFileNameOfReport("testing file report");
		
		DisbursementReportFile.Header header2 = new DisbursementReportFile().new Header();
		
		header2.setBankName("National Australia Bank");
		header2.setProductName("Direct Link");
		header2.setReportName("Direct Link - Direct Credit Disbursement Report");
		header2.setRunDate("09/07/2017");
		header2.setRunTime(System.currentTimeMillis()+"");
		header2.setFundId("123456");
		header2.setCustomerName("James Brown");
		header2.setImportFileName("Original file name from CPH envelope");
		header2.setPaymentDate("09/07/2017");
		header2.setBatchNoLinks("156456456646");
		header2.setExportFileName("NAB Connect file name field");
		header2.setMeId("95231");
		header2.setFileNameOfReport("testing file report");
		
		List<List<String>> list = new ArrayList<List<String>>();
		list.add( header.getEntries());
		list.add( header2.getEntries());
	//	list.add( header.getEntries());

		csvwriter.writeRecord(fileWriter,list);

		
		fileWriter.flush();
		fileWriter.close();
		
		
		
	}

}
