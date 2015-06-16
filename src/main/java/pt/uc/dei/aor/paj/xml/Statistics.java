package pt.uc.dei.aor.paj.xml;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Statistics {

	public static String getStats(String xmlPath) {

		int count[] = new int[7];
		int total12 = 0;
		
		//inicializar
		for (int i = 0; i < 7; i++) count[i] = 0;

		try {
			// Put XML in Document to parse
			File file = new File(xmlPath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);

			//Get all element "noticia"
			NodeList nList = doc.getElementsByTagName("noticia");

			//today's date
			Date hoje = new Date();

			for (int i = 0; i < nList.getLength(); i++) {

				//node NOTICIA
				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int index = getIndexLocal(eElement.getAttribute("local"));

					// element DATA
					String data = eElement.getElementsByTagName("data").item(0).getTextContent();
					if (less12hours(data, hoje)) {
						count[index]++;
						total12++;
					}

				}
			}	
			
			String stats = "Statistics for "+ hoje+"\n";
			stats += "CNN News from last 12 hours:\n\n";
			stats += "Africa: "+count[3]+"\n";
			stats += "Americas: "+count[4]+"\n";
			stats += "Asia: "+count[2]+"\n";
			stats += "China: "+count[5]+"\n";
			stats += "Europe: "+count[1]+"\n";
			stats += "Middle-East: "+count[6]+"\n";
			stats += "US: "+count[0]+"\n\n";
			stats += "Total: "+total12+"\n";
			return stats;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	private static int getIndexLocal(String l) {
		if (l.equals("us")) return 0;
		if (l.equals("europe")) return 1;
		if (l.equals("asia")) return 2;
		if (l.equals("africa")) return 3;
		if (l.equals("americas")) return 4;
		if (l.equals("china")) return 5;
		if (l.equals("middleeast")) return 6;
		return 0;
	}

	private static boolean less12hours(String d, Date hoje) {
		
		//split date string (example: 2015-06-15T18:29:00.000+01:00)
		String[] ds = d.split("T");
		String date = ds[0];
		String[] remain = ds[1].split(":");
		String newsDate = date+" "+remain[0]+":"+remain[1];

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dataN = null;
		try {
			//News date
			dataN = format.parse(newsDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//check if date is less than 12 hours
		if ((hoje.getTime()-dataN.getTime())/(1000*60*60) >= 12) return false;
		else return true;
		
	}
}
