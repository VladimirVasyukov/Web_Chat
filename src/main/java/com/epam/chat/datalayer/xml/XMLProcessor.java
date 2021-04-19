package com.epam.chat.datalayer.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLProcessor {
    private static final String TRANSFORMER_WHITESPACE_YES = "yes";
    private static final File MESSAGES_XML_FILE_PATH = new File("src/main/resources/messages.xml");
    private static final File MESSAGES_XSD_FILE_PATH = new File("src/main/resources/messages.xsd");
    private static final File USERS_XML_FILE_PATH = new File("src/main/resources/users.xml");
    private static final File USERS_XSD_FILE_PATH = new File("src/main/resources/users.xsd");
    private static final Logger LOG = LogManager.getLogger(XMLProcessor.class.getName());

    public Document parseMessagesXML() throws FileNotFoundException {
        return parseXMLFile(MESSAGES_XML_FILE_PATH, MESSAGES_XSD_FILE_PATH);
    }

    public Document parseUsersXML() throws FileNotFoundException {
        return parseXMLFile(USERS_XML_FILE_PATH, USERS_XSD_FILE_PATH);
    }

    public void updateMessagesXML(Document document) throws FileNotFoundException {
        updateXMLFile(document, MESSAGES_XML_FILE_PATH);
    }

    public void updateUsersXML(Document document) throws FileNotFoundException {
        updateXMLFile(document, USERS_XML_FILE_PATH);
    }

    public Element getChild(Element parent, String childName) {
        NodeList nodeList = parent.getElementsByTagName(childName);
        return (Element) nodeList.item(0);
    }

    public String getChildValue(Element parent, String childName) {
        Element child = getChild(parent, childName);
        Node node = child.getFirstChild();
        return node.getNodeValue();
    }

    public <E extends Enum<E>> E getEnumValueFromString(String string, E[] values) {
        E result = null;
        for (E enumValue : values) {
            if (enumValue.name().equals(string)) {
                result = enumValue;
                break;
            }
        }
        return result;
    }

    private Document parseXMLFile(File xmlFile, File xsdFile) throws FileNotFoundException {
        Document document;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            LOG.error(e.getMessage(), e);
            throw new FileNotFoundException(String.format(
                "Can't parse file because one or both files is missing or damaged:%n%s%n%s%n",
                xmlFile.getName(), xsdFile.getName()));
        }
        return document;
    }

    private void updateXMLFile(Document document, File xmlFile) throws FileNotFoundException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, TRANSFORMER_WHITESPACE_YES);
            transformer.transform(new DOMSource(document), new StreamResult(xmlFile));
        } catch (TransformerException e) {
            LOG.error(e.getMessage(), e);
            throw new FileNotFoundException(String.format(
                "Can't write to file %s because it's missing or damaged%n", xmlFile.getName()));
        }
    }

}
