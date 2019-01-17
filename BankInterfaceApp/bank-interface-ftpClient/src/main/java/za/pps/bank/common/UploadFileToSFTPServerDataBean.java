/**
 * 
 */
package za.pps.bank.common;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 27 Mar 2018
 * @version 1.0
 */
public class UploadFileToSFTPServerDataBean {
	
	private String ddFilePath, ccFilePath;
	private String ddFileName, ccFileName;
	/**
	 * @return the ddFilePath
	 */
	public String getDdFilePath() {
		return ddFilePath;
	}
	/**
	 * @param ddFilePath the ddFilePath to set
	 */
	public UploadFileToSFTPServerDataBean setDdFilePath(String ddFilePath) {
		this.ddFilePath = ddFilePath;
		return this;
	}
	/**
	 * @return the ccFilePath
	 */
	public String getCcFilePath() {
		return ccFilePath;
	}
	/**
	 * @param ccFilePath the ccFilePath to set
	 */
	public UploadFileToSFTPServerDataBean setCcFilePath(String ccFilePath) {
		this.ccFilePath = ccFilePath;
		return this;
	}
	/**
	 * @return the ddFileName
	 */
	public String getDdFileName() {
		return ddFileName;
	}
	/**
	 * @param ddFileName the ddFileName to set
	 */
	public UploadFileToSFTPServerDataBean setDdFileName(String ddFileName) {
		this.ddFileName = ddFileName;
		return this;
	}
	/**
	 * @return the ccFileName
	 */
	public String getCcFileName() {
		return ccFileName;
	}
	/**
	 * @param ccFileName the ccFileName to set
	 */
	public UploadFileToSFTPServerDataBean setCcFileName(String ccFileName) {
		this.ccFileName = ccFileName;
		return this;
	}
	
	
}
