package utility;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sign.DigestAlgorithm;
import sign.SignatureLevel;
import sign.SignaturePattern;
import sign.SignatureVisibility;
import timestamp.TSAAuthenticationType;
import timestamp.TimeStampType;


public class config {
	private String url;
	private String name;
	private String libPath;
	private String slot;
	private String pkcs11SearchKeyword;
	
	private TimeStampType timeStampType;
	private TSAAuthenticationType tsaAuthenticationType;
	private String tsaCertificateType_tsaPKCS12File;
	private String tsaCertificateType_tsaPKCS12Password;
	private String tsaUesrPassword_tsaUsername;
	private String tsaUesrPassword_tsaPassword;

	private String signLocation;
	private String signReason;
	private SignatureLevel signSignatureLevel;
	private SignatureVisibility signSignatureVisibility;
	private SignaturePattern signSignaturePattern;
	private DigestAlgorithm signDigestAlgorithm;
	
	
	
	
	public config(String xmlFilePath) throws XPathExpressionException {
		Document doc = convertXMLFileToXMLDocument(xmlFilePath);
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList conf_url = (NodeList) xpath.compile("//configuration/tsa/url/text()").evaluate(doc, XPathConstants.NODESET);
		String _url =conf_url.item(0).getTextContent();
		setUrl(_url);
		
		NodeList conf_name = (NodeList) xpath.compile("//configuration/pkcs11/name/text()").evaluate(doc, XPathConstants.NODESET);
        String _name =conf_name.item(0).getTextContent();
        setName(_name);
        
        NodeList conf_slot = (NodeList) xpath.compile("//configuration/pkcs11/slot/text()").evaluate(doc, XPathConstants.NODESET);
        String _slot =conf_slot.item(0).getTextContent();
        setSlot(_slot);
        
        NodeList conf_driver = (NodeList) xpath.compile("//configuration/pkcs11/driver/text()").evaluate(doc, XPathConstants.NODESET);
        String _driver =conf_driver.item(0).getTextContent();
        setLibPath(_driver);
        
        NodeList conf_pkcs11SearchKeyword = (NodeList) xpath.compile("//configuration/pkcs11/pkcs11SearchKeyword/text()").evaluate(doc, XPathConstants.NODESET);
        String _pkcs11SearchKeyword =conf_pkcs11SearchKeyword.item(0).getTextContent();
        setPkcs11SearchKeyword(_pkcs11SearchKeyword);
        
        NodeList conf_timeStampType = (NodeList) xpath.compile("//configuration/tsa/timeStampType/text()").evaluate(doc, XPathConstants.NODESET);
        String _timeStampType =conf_timeStampType.item(0).getTextContent();
        setTimeStampType(_timeStampType);
        
        NodeList conf_tsaAuthenticationType = (NodeList) xpath.compile("//configuration/tsa/tsaAuthenticationType/text()").evaluate(doc, XPathConstants.NODESET);
        String _tsaAuthenticationType =conf_tsaAuthenticationType.item(0).getTextContent();
        setTsaAuthenticationType(_tsaAuthenticationType);
        
        NodeList conf_tsaPKCS12File = (NodeList) xpath.compile("//configuration/tsa/tsaCertificateType/tsaPKCS12File/text()").evaluate(doc, XPathConstants.NODESET);
        String _tsaPKCS12File =conf_tsaPKCS12File.item(0).getTextContent();
        setTsaCertificateType_tsaPKCS12File(_tsaPKCS12File);
        
        NodeList conf_tsaPKCS12Password = (NodeList) xpath.compile("//configuration/tsa/tsaCertificateType/tsaPKCS12Password/text()").evaluate(doc, XPathConstants.NODESET);
        String _tsaPKCS12Password =conf_tsaPKCS12Password.item(0).getTextContent();
        setTsaCertificateType_tsaPKCS12Password(_tsaPKCS12Password);
        
        NodeList conf_tsaUsername = (NodeList) xpath.compile("//configuration/tsa/tsaUesrPassword/tsaUsername/text()").evaluate(doc, XPathConstants.NODESET);
        String _tsaUsername =conf_tsaUsername.item(0).getTextContent();
        setTsaUesrPassword_tsaUsername(_tsaUsername);
        
        NodeList conf_tsaPassword = (NodeList) xpath.compile("//configuration/tsa/tsaUesrPassword/tsaPassword/text()").evaluate(doc, XPathConstants.NODESET);
        String _tsaPassword =conf_tsaPassword.item(0).getTextContent();
        setTsaUesrPassword_tsaPassword(_tsaPassword);
        
        NodeList conf_location = (NodeList) xpath.compile("//configuration/sign/location/text()").evaluate(doc, XPathConstants.NODESET);
        String _location =conf_location.item(0).getTextContent();
        setSignLocation(_location);
        
        NodeList conf_reason = (NodeList) xpath.compile("//configuration/sign/reason/text()").evaluate(doc, XPathConstants.NODESET);
        String _reason =conf_reason.item(0).getTextContent();
        setSignReason(_reason);
        
        NodeList conf_SignatureLevel = (NodeList) xpath.compile("//configuration/sign/signatureLevel/text()").evaluate(doc, XPathConstants.NODESET);
        String _SignatureLevel =conf_SignatureLevel.item(0).getTextContent();
        setSignSignatureLevel(_SignatureLevel);
        
        
        NodeList conf_signaturePattern = (NodeList) xpath.compile("//configuration/sign/signaturePattern/text()").evaluate(doc, XPathConstants.NODESET);
        String _signaturePattern =conf_signaturePattern.item(0).getTextContent();
        setSignSignaturePattern(_signaturePattern);
        
        NodeList conf_digestAlgorithm = (NodeList) xpath.compile("//configuration/sign/digestAlgorithm/text()").evaluate(doc, XPathConstants.NODESET);
        String _digestAlgorithm =conf_digestAlgorithm.item(0).getTextContent();
        setSignDigestAlgorithm(_digestAlgorithm);
        
	}
	public static Document convertXMLFileToXMLDocument(String filePath) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new File(filePath));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getPkcs11SearchKeyword() {
		return pkcs11SearchKeyword;
	}
	public void setPkcs11SearchKeyword(String pkcs11SearchKeyword) {
		this.pkcs11SearchKeyword = pkcs11SearchKeyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLibPath() {
		return libPath;
	}
	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public TimeStampType getTimeStampType() {
		return timeStampType;
	}
	public void setTimeStampType(String timeStampType) {
		if(timeStampType.equals("TSA")) {
			this.timeStampType = this.timeStampType.TSA;
		}else {
			this.timeStampType = this.timeStampType.COMPUTER_CLOCK;
		}
	}
	public TSAAuthenticationType getTsaAuthenticationType() {
		return tsaAuthenticationType;
	}
	public void setTsaAuthenticationType(String tsaAuthenticationType) {
//		NO_AUTHENTICATION,USERNAME_PASSWORD,CERTIFICATE
		if(tsaAuthenticationType.equals("NO_AUTHENTICATION")) {
			this.tsaAuthenticationType = this.tsaAuthenticationType.NO_AUTHENTICATION;
		}else if(tsaAuthenticationType.equals("USERNAME_PASSWORD")) {
			this.tsaAuthenticationType = this.tsaAuthenticationType.USERNAME_PASSWORD;
		}else if(tsaAuthenticationType.equals("CERTIFICATE")) {
			this.tsaAuthenticationType = this.tsaAuthenticationType.CERTIFICATE;
		}else {
			this.tsaAuthenticationType = this.tsaAuthenticationType.NO_AUTHENTICATION;
		}
	}
	public String getTsaCertificateType_tsaPKCS12File() {
		return tsaCertificateType_tsaPKCS12File;
	}
	public void setTsaCertificateType_tsaPKCS12File(String tsaCertificateType_tsaPKCS12File) {
		this.tsaCertificateType_tsaPKCS12File = tsaCertificateType_tsaPKCS12File;
	}
	public String getTsaCertificateType_tsaPKCS12Password() {
		return tsaCertificateType_tsaPKCS12Password;
	}
	public void setTsaCertificateType_tsaPKCS12Password(String tsaCertificateType_tsaPKCS12Password) {
		this.tsaCertificateType_tsaPKCS12Password = tsaCertificateType_tsaPKCS12Password;
	}
	public String getTsaUesrPassword_tsaUsername() {
		return tsaUesrPassword_tsaUsername;
	}
	public void setTsaUesrPassword_tsaUsername(String tsaUesrPassword_tsaUsername) {
		this.tsaUesrPassword_tsaUsername = tsaUesrPassword_tsaUsername;
	}
	public String getTsaUesrPassword_tsaPassword() {
		return tsaUesrPassword_tsaPassword;
	}
	public void setTsaUesrPassword_tsaPassword(String tsaUesrPassword_tsaPassword) {
		this.tsaUesrPassword_tsaPassword = tsaUesrPassword_tsaPassword;
	}
	public String getSignLocation() {
		return signLocation;
	}
	public void setSignLocation(String signLocation) {
		this.signLocation = signLocation;
	}
	public String getSignReason() {
		return signReason;
	}
	public void setSignReason(String signReason) {
		this.signReason = signReason;
	}
	public SignatureLevel getSignSignatureLevel() {
		return signSignatureLevel;
	}
	public void setSignSignatureLevel(String signSignatureLevel) {
		if(signSignatureLevel.equals("APPROVAL")) {
			this.signSignatureLevel = SignatureLevel.APPROVAL;
		}else if(signSignatureLevel.equals("CERTIFIED_NO_CHANGES_ALLOW")) {
			this.signSignatureLevel = SignatureLevel.CERTIFIED_NO_CHANGES_ALLOW;
		}else if(signSignatureLevel.equals("CERTIFIED_FORM_FILLING")) {
			this.signSignatureLevel = SignatureLevel.CERTIFIED_FORM_FILLING;
		}else if(signSignatureLevel.equals("CERTIFIED_FORM_FILLING_AND_ANNOTATIONS")) {
			this.signSignatureLevel = SignatureLevel.CERTIFIED_FORM_FILLING_AND_ANNOTATIONS;
		}else {
			this.signSignatureLevel = SignatureLevel.APPROVAL;
		}
	}
	public SignatureVisibility getSignSignatureVisibility() {
		return signSignatureVisibility;
	}
	public void setSignSignatureVisibility(String signSignatureVisibility) {
	    if(signSignatureVisibility.equals("INVISIBLE")) {
			this.signSignatureVisibility = SignatureVisibility.INVISIBLE;
		}else if(signSignatureVisibility.equals("VISIBLE")) {
			this.signSignatureVisibility = SignatureVisibility.VISIBLE;
		}
	}
	public SignaturePattern getSignSignaturePattern() {
		return signSignaturePattern;
	}
	public void setSignSignaturePattern(String signSignaturePattern) {
		if(signSignaturePattern.equals("DESCRIPTION")) {
			this.signSignaturePattern = SignaturePattern.DESCRIPTION;
		}else if(signSignaturePattern.equals("NAME_AND_DESCRIPTION")) {
			this.signSignaturePattern = SignaturePattern.NAME_AND_DESCRIPTION;
		}else if(signSignaturePattern.equals("GRAPHIC_AND_DESCRIPTION")) {
			this.signSignaturePattern = SignaturePattern.GRAPHIC_AND_DESCRIPTION;
		}else {
			this.signSignaturePattern = SignaturePattern.GRAPHIC;
		}
		
	}
	public DigestAlgorithm getSignDigestAlgorithm() {
		return signDigestAlgorithm;
	}
	public void setSignDigestAlgorithm(String signDigestAlgorithm) {
		if(signDigestAlgorithm.equals("SHA256")) {
			this.signDigestAlgorithm = DigestAlgorithm.SHA256;
		}else if(signDigestAlgorithm.equals("SHA384")) {
			this.signDigestAlgorithm = DigestAlgorithm.SHA384;
		}else if(signDigestAlgorithm.equals("SHA512")) {
			this.signDigestAlgorithm = DigestAlgorithm.SHA512;
		}else {
			this.signDigestAlgorithm = DigestAlgorithm.SHA256;
		}
	}
	
}
