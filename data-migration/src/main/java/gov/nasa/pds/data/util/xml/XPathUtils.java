package gov.nasa.pds.data.util.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XPathUtils 
{
	
	public static XPathExpression compileXPath(XPathFactory xpf, String str) throws Exception
	{
        XPath xpath = xpf.newXPath();
        XPathExpression expr = xpath.compile(str);
        return expr;
	}
	
	
	public static boolean exists(Document doc, XPathExpression expr) throws Exception
	{
	    Object node = expr.evaluate(doc, XPathConstants.NODE);
	    return node != null;
	}
	
	
    public static NodeList getNodeList(Document doc, XPathExpression expr) throws Exception
    {
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;
    }

	
	public static String getStringValue(Document doc, XPathExpression expr) throws Exception
	{
        Object res = expr.evaluate(doc, XPathConstants.STRING);
        if(res == null) return null;
        
        String val = res.toString();
        return (val.isEmpty()) ? null : val;
	}


	public static String[] getStringArray(Document doc, XPathExpression expr) throws Exception
	{
		NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if(nodes == null || nodes.getLength() == 0) return null;
		
		String vals[] = new String[nodes.getLength()];
        for(int i = 0; i < nodes.getLength(); i++)
        {
        	vals[i] = nodes.item(i).getTextContent();
        }
        
        return vals;
	}

	
    public static String[] getChildValues(Document doc, XPathExpression expr) throws Exception
    {
        NodeList nodes = getNodeList(doc, expr);
        if(nodes == null || nodes.getLength() == 0) return null;

        List<String> list = new ArrayList<>();
        
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            appendValues(node.getChildNodes(), list);
        }
        
        if(list.isEmpty()) return null;
        return list.toArray(new String[0]);
    }
    
    
    private static void appendValues(NodeList nodes, List<String> values)
    {
        if(nodes == null || nodes.getLength() == 0) return;
        
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                String value = node.getTextContent();
                values.add(value);
            }
        }
    }
	
}
