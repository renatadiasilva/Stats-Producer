package pt.uc.dei.aor.paj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pt.uc.dei.aor.paj.xml.Statistics;

public class RunStatsProducer {

	public static void main(String[] args) throws Exception {
		
		//recebe string com XML
		//transforma em ficheiro (XSL ou JAVA?)
//		Subscriber r = new Subscriber();
//		r.subscribe();
		
		String filePathXML = "..\\src\\main\\resources\\ricardo.xml";
		String stats = Statistics.getStats(filePathXML);
		System.out.println("Estatísticas calculadas a partir do ficheiro "+filePathXML);
		
		FileWriter fw = null;		
		try {
			//mudar!!
			String filePath= Subscriber.outputNameFile("stats","txt");
			File file = new File(filePath);
			fw = new FileWriter(file);
			fw.write(stats);
			fw.close();
			System.out.println("Estatísticas guardadas no ficheiro "+filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}