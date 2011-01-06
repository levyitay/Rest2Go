package com.Rest2Go;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.Buffer;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class xmlBuilder {
	
	public static Document buildXMLFromString (String inputString) throws Exception
	{
		DocumentBuilderFactory dbf =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(inputString));
        
        Document doc = db.parse(is);
        return doc;
	}
	
	
	public static Document buildXMLFromResultSet(ResultSet resSet) throws SQLException
	{
		
		Document document=null;
		
		DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
		 try {
		 DocumentBuilder builder = dBF.newDocumentBuilder(); // java xml documentbuilder
		 document = builder.newDocument();
		 } 
		 catch (ParserConfigurationException parserException) {
			 parserException.printStackTrace();
		 }
		 Element root = document.createElement("USERS");
		 document.appendChild(root);
		 
		 
			 int i=0;
				while (resSet.next())
				{
					Element User = document.createElement("USER");
					root.appendChild(User);
					
					Element FirstName = document.createElement("FIRSTNAME");
					String fn = resSet.getString("First_Name");
					FirstName.appendChild(document.createTextNode(fn));
					User.appendChild(FirstName);
					
					Element Last_Name = document.createElement("LASTNAME");
					Last_Name.appendChild(document.createTextNode(resSet.getString("Last_Name")));
					User.appendChild(Last_Name);
					
					Element Address = document.createElement("ADDRESS");
					Address.appendChild(document.createTextNode(resSet.getString("Address")));
					User.appendChild(Address);
					
					Element City = document.createElement("CITY");
					City.appendChild(document.createTextNode(resSet.getString("City")));
					User.appendChild(City);
					
					Element Country = document.createElement("COUNTRY");
					Country.appendChild(document.createTextNode(resSet.getString("Country")));
					User.appendChild(Country);
					
					Element Birth_Date = document.createElement("BIRTH_DATE");
					Birth_Date.appendChild(document.createTextNode(resSet.getDate("Birth_Date").toString()));
					
					i++;
				}
				document.normalize();
				return document;
	}
	

	

}
