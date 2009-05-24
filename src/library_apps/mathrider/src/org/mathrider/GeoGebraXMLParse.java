package org.mathrider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class parses GeoGebra Element tags and converts them into MathPiper
 * associative arrays.
 */

public class GeoGebraXMLParse {

    private StringBuilder list = new StringBuilder();
    private SAXParser parser;
    DefaultHandler handler;

    public GeoGebraXMLParse() {
        try {
            // Get SAX Parser Factory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // Turn on validation, and turn off namespaces
            factory.setValidating(true);
            factory.setNamespaceAware(false);
            parser = factory.newSAXParser();
            handler = new MyHandler();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end constructor.

    public synchronized String parse(String xml) {
        ByteArrayInputStream xmlStream = new ByteArrayInputStream(xml.getBytes());
        try {
            parser.parse(xmlStream, handler);
            list.append("}");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.toString();

    }

    class MyHandler extends DefaultHandler {
        // SAX callback implementations from DocumentHandler, ErrorHandler, etc.

        private Writer out;

        public MyHandler() throws SAXException {
            try {
                out = new OutputStreamWriter(System.out, "UTF8");
            } catch (IOException e) {
                throw new SAXException("Error getting output handle.", e);
            }
        }

        public void startDocument() throws SAXException {
            //print("<?xml version=\"1.0\"?>\n");
            list.delete(0, list.length());
            list.append("{");
        }

        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if(!qName.equalsIgnoreCase("element"))
            {
                list.append("{\"" + qName + "\",{");
            }
            //print("<" + qName);
            if (atts != null) {
                for (int i = 0, len = atts.getLength(); i < len; i++) {
                    //print(" " + atts.getQName(i) +  "=\"" + atts.getValue(i) + "\"");
                    list.append("{\"" + atts.getQName(i) + "\"," + atts.getValue(i) + "},");

                }//end for.
            }//end if.
        //print(">");
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            //print("</" + qName + ">\n");
            //print(list.toString());
            if(!qName.equalsIgnoreCase("element"))
            {
                list.append("}},");
            }
        }

        public void characters(char[] ch, int start, int len) throws SAXException {
            //String chars = new String(ch, start, len);
            //print(chars);
        }

        private void print(String s) throws SAXException {
            try {
                out.write(s);
                out.flush();
            } catch (IOException e) {
                throw new SAXException("IO Error Occurred.", e);
            }
        }
    }//end class.

    public static void main(String[] args) {
        GeoGebraXMLParse parseXML = new GeoGebraXMLParse();

        String xml = "<element type=\"point\" label=\"A\"><show object=\"true\" label=\"true\"/> <objColor r=\"0\" g=\"0\" b=\"255\" alpha=\"0.0\"/> <layer val=\"0\"/><animation step=\"0.1\" speed=\"1\" type=\"0\" playing=\"false\"/><coords x=\"2.0\" y=\"2.0\" z=\"1.0\"/><pointSize val=\"3\"/></element>";

        String result = parseXML.parse(xml);
        System.out.println(result);

    }//end main.
}//end class
