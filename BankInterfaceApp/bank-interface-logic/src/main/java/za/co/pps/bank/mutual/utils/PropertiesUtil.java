/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 14 Sep 2017
 * @version 1.0
 */
@Component
@PropertySource("classpath:/config/application-${spring.profiles.active}.properties")
public class PropertiesUtil {
	
	@Value("${nab.response.acknowledgement.status.dd}")
	private String status_dd;
	@Value("${nab.response.acknowledgement.received.dd}")
	private String received_dd;
    @Value("${file.input.location}")
	private String tbInputLocation;
    @Value("${file.output.location.dd}")
    private String nabMessageDDOutput;
    @Value("${file.output.location.cc}")
    private String nabMessageCCOutput;
    @Value("${file.output.exception.location}")
    private String execptionLocation;
    @Value("${file.output.processed.location}")
    private String processedFileOutput;
    @Value("${file.output.disbursement.report.location}")
    private String disbursementFileOutput;
    @Value("${file.name}")
    private String inputFileName;
    
	@Value("${file.remote.dd.input}")
    private String remote_dd;
	@Value("${file.remote.cc.input}")
    private String remote_cc;
	
    
	public String getStatus_dd_folder() {
		return status_dd;
	}
	public String getReceived_dd_folder() {
		return received_dd;
	}
	public String getTbInputLocation() {
		return tbInputLocation;
	}
	public String getNabMessageDDOutput() {
		return nabMessageDDOutput;
	}
	public String getNabMessageCCOutput() {
		return nabMessageCCOutput;
	}
	public String getExecptionLocation() {
		return execptionLocation;
	}
	public String getProcessedFileOutput() {
		return processedFileOutput;
	}
	public String getDisbursementFileOutput() {
		return disbursementFileOutput;
	}
	/**
	 * @return the inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}
	/**
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	/**
	 * @return the remote_dd
	 */
	public String getRemote_dd() {
		return remote_dd;
	}
	/**
	 * @param remote_dd the remote_dd to set
	 */
	public void setRemote_dd(String remote_dd) {
		this.remote_dd = remote_dd;
	}
	/**
	 * @return the remote_cc
	 */
	public String getRemote_cc() {
		return remote_cc;
	}
	/**
	 * @param remote_cc the remote_cc to set
	 */
	public void setRemote_cc(String remote_cc) {
		this.remote_cc = remote_cc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertiesUtil [status_dd=" + status_dd + ", received_dd=" + received_dd + ", tbInputLocation="
				+ tbInputLocation + ", nabMessageDDOutput=" + nabMessageDDOutput + ", nabMessageCCOutput="
				+ nabMessageCCOutput + ", execptionLocation=" + execptionLocation + ", processedFileOutput="
				+ processedFileOutput + ", disbursementFileOutput=" + disbursementFileOutput + ", inputFileName="
				+ inputFileName + ", remote_dd=" + remote_dd + ", remote_cc=" + remote_cc + "]";
	}
}
