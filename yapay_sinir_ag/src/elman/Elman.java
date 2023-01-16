package elman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.ElmanNetwork;
import org.neuroph.nnet.learning.BackPropagation;

import odev.YSA;

public class Elman {
	private static final File egitimdosya=new File(Elman.class.getResource("egitim.txt").getPath());

	private static final File testdosya=new File(Elman.class.getResource("test.txt").getPath());

	
	public void egit() throws FileNotFoundException {
		
		ElmanNetwork elmanAg = new ElmanNetwork(6,5 , 6, 1);
		BackPropagation bp = new BackPropagation();
		bp.setLearningRate(0.2);
		bp.setMaxIterations(700);
		elmanAg.setLearningRule(bp);
		elmanAg.learn(veriSeti(egitimdosya));
		elmanAg.save("egitilmis.nnet");
		System.out.println("Egitim Tamamlandi");
		
	}
	public DataSet veriSeti(File dosya) throws FileNotFoundException {
		Scanner in = new Scanner(dosya);
		DataSet ds = new DataSet(3,1);
		
		while(in.hasNextDouble()) {
			double [] inputs = new double[3];
			for(int i = 0 ; i < 3 ; i++) {
				inputs[i] = in.nextDouble();
			}
			DataSetRow satir = new DataSetRow(inputs,new double[] {in.nextDouble()});
			ds.add(satir);
		}
		in.close();
		return ds;
	}
	
	public double test() throws FileNotFoundException {
		NeuralNetwork ysa = NeuralNetwork.createFromFile("egitilmis.nnet");
		
		double toplamHata = 0;
		DataSet testVeriSeti = veriSeti(testdosya);
		List<DataSetRow> satirlar = testVeriSeti.getRows();
		for(var satir : satirlar) {
			ysa.setInput(satir.getInput());
			ysa.calculate();
			toplamHata+=Math.pow(satir.getDesiredOutput()[0]-ysa.getOutput()[0],2);
		}
		return toplamHata/testVeriSeti.size();
	}
	public double egitim() throws FileNotFoundException {
		NeuralNetwork ysa = NeuralNetwork.createFromFile("egitilmis.nnet");
		
		double toplamHata = 0;
		DataSet egitimVeriSeti = veriSeti(egitimdosya);
		List<DataSetRow> satirlar = egitimVeriSeti.getRows();
		for(var satir : satirlar) {
			ysa.setInput(satir.getInput());
			ysa.calculate();
			toplamHata+=Math.pow(satir.getDesiredOutput()[0]-ysa.getOutput()[0],2);
		}
		return toplamHata/egitimVeriSeti.size();
	}
}
