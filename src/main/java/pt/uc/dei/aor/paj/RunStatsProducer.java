package pt.uc.dei.aor.paj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pt.uc.dei.aor.paj.xml.TransformXML;
import pt.uc.dei.aor.paj.xml.XMLValidation;


public class RunStatsProducer implements MessageListener {

	private ConnectionFactory cf;
	private Topic t;

	private boolean stop = false;

	//inicializa
	public RunStatsProducer() {
		try {
			cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			t = InitialContext.doLookup("jms/topic/PlayTopic");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	//recebe mensagens
	public void subscribe() {
		try (JMSContext jcontext = cf.createContext("joao", "pedro");) {
			jcontext.setClientID("user1");
			JMSConsumer mc = jcontext.createDurableConsumer(t, "user1");
			mc.setMessageListener(this);
			//Wait for stop
			while (!stop) {
				Thread.sleep(5000);
			}
			//Exit
			System.out.println("Exiting...");
			System.out.println("Goodbye!");
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// MAIN
	public static void main(String[] args) throws NamingException, JMSException {
		RunStatsProducer r = new RunStatsProducer();
		r.subscribe();
	}

	@Override
	public void onMessage(Message message) {
		try {
			String msgText = ((TextMessage) message).getText();
			if ("stop".equals(msgText))
				stop = true;
			else {
				System.out.println("Message received.");
				//pass String received to XML
				String filePathXML = outputNameFile("output", "xml");
				TransformXML.convertStringToXMLFile(msgText, filePathXML);
				System.out.println("String transformed to XML.");

				//XSD verification
				String filePathXSD = "..\\src\\main\\resources\\noticia.xsd";
				if (XMLValidation.validateXMLSchema(filePathXSD, filePathXML)) {
					System.out.println("XSD Validation: OK.");
					
					//Compute Statistics
					String stats = Statistics.getStats(filePathXML);
					System.out.println("Estatísticas calculadas a partir do ficheiro "+filePathXML);

					FileWriter fw = null;		
					try {
						String filePath = outputNameFile("stats","txt");
						File file = new File(filePath);
						fw = new FileWriter(file);
						fw.write(stats);
						fw.close();
						System.out.println("Estatísticas guardadas no ficheiro "+filePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("XSD Validation: FAILED!");
					System.out.println("No Statistics File Created.");
				}

			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String outputNameFile(String name, String ext) {

		Calendar now = new GregorianCalendar();
		String filename = "..\\src\\main\\resources\\"+name;
		filename += "_"+now.get(Calendar.YEAR);
		int mes = now.get(Calendar.MONTH)+1;
		filename += "_"+mes+"";
		filename += "_"+now.get(Calendar.DAY_OF_MONTH);
		filename += "_"+now.get(Calendar.HOUR_OF_DAY);
		filename += "_"+now.get(Calendar.MINUTE);
		filename += "_"+now.get(Calendar.SECOND);
		filename += "_"+now.get(Calendar.MILLISECOND);
		filename += "."+ext;
		return filename;

	}

}