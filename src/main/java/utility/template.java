package utility;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sign.DigestAlgorithm;
import sign.SignatureLevel;
import sign.SignaturePattern;
import sign.SignatureVisibility;
import timestamp.TSAAuthenticationType;
import timestamp.TimeStampType;


public class template {
	private String gsPath;
	private int xbar;
	private int ybar;
	private int hv;
	private int wv;
	private int pNumber;
	
	
	
	public template() {
		this.gsPath = null;
		this.xbar = 0;
		this.ybar = 0;
		this.hv = 0;
		this.wv = 0;
		this.pNumber = 1;
	}
	
	public template(String xmlFilePath,String parentPath) throws Exception {
		try {
			Document doc = convertXMLFileToXMLDocument(xmlFilePath);
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			NodeList conf_gsPath = (NodeList) xpath.compile("//temp/pathGraphicSignature/text()").evaluate(doc, XPathConstants.NODESET);
			String _gsPath =conf_gsPath.item(0).getTextContent();
			String path = parentPath+"\\"+_gsPath;
			setGsPath(path);
			
			NodeList conf_x = (NodeList) xpath.compile("//temp/x/text()").evaluate(doc, XPathConstants.NODESET);
			String _x =conf_x.item(0).getTextContent();
			setXbar(_x);
			
			NodeList conf_y = (NodeList) xpath.compile("//temp/y/text()").evaluate(doc, XPathConstants.NODESET);
			String _y =conf_y.item(0).getTextContent();
			setYbar(_y);
			
			NodeList conf_height = (NodeList) xpath.compile("//temp/height/text()").evaluate(doc, XPathConstants.NODESET);
			String _height =conf_height.item(0).getTextContent();
			setHv(_height);
			
			NodeList conf_width = (NodeList) xpath.compile("//temp/width/text()").evaluate(doc, XPathConstants.NODESET);
			String _width =conf_width.item(0).getTextContent();
			setWv(_width);
			
			NodeList conf_pNumber = (NodeList) xpath.compile("//temp/pageNumber/text()").evaluate(doc, XPathConstants.NODESET);
			String _pN =conf_pNumber.item(0).getTextContent();
			setpNumber(_pN);
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			throw new XPathExpressionException("Incomplete certificate input");
			
		} catch (DOMException e) {
			
			e.printStackTrace();
			throw new DOMException((short) 1, "Incomplete certificate input");
		}
        
        
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
	
	public String getGsPath() {
		return gsPath;
	}
	public int getXbar() {
		return xbar;
	}
	public int getYbar() {
		return ybar;
	}
	public int getHv() {
		return hv;
	}
	public int getWv() {
		return wv;
	}
	public int getpNumber() {
		return pNumber;
	}
	
	public void setGsPath(String gsPath) {
		this.gsPath = gsPath;
	}
	public void setXbar(String xbar) {
		this.xbar = Integer.parseInt(xbar);
	}
	public void setYbar(String ybar) {
		this.ybar = Integer.parseInt(ybar);
	}
	public void setHv(String hv) {
		this.hv = Integer.parseInt(hv);
	}
	public void setWv(String wv) {
		this.wv = Integer.parseInt(wv);
	}
	public void setpNumber(String pn) {
		this.pNumber = Integer.parseInt(pn);
	}
	
	
	
	
}
