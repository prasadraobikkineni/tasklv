package tech.automationlabs.xml;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlParser {
	private XPath xpath = XPathFactory.newInstance().newXPath();

	private Document doc;

	public XmlParser(String xml) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			builder.setEntityResolver(null);
			doc = builder.parse(new InputSource(new StringReader(xml)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getString(String expression) {
		try {
			return (String) xpath.compile(expression).evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public Node getNode(String expression) {
		try {
			return (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public NodeList getNodes(String expression) {
		try {
			return (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public String getAttribute(Node node, String attr) {
		return node.getAttributes().getNamedItem(attr).getNodeValue();
	}

	public int getIntAttribute(Node node, String attr) {
		return Integer.parseInt(node.getAttributes().getNamedItem(attr).getNodeValue());
	}
}
