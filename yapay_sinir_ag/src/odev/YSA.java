package odev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.visrec.ml.data.DataSet;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

import odev.YSA;

public class YSA {
	private static final File dosya=new File(YSA.class.getResource("egit.txt").getPath());
	private static final File dosya2=new File(YSA.class.getResource("test.txt").getPath());
	
	BackPropagation bp;
	int maxEpoch;
	double minHata;
	
	public YSA(int epoch,double hata, double ogrenmeKatsayisi) {
		this.maxEpoch=epoch;
		this.minHata=hata;
		bp=new BackPropagation();
		bp.setLearningRate(ogrenmeKatsayisi);
		bp.setMaxIterations(epoch);
		bp.setMaxError(minHata);
	}
	
	public org.neuroph.core.data.DataSet testVeriSeti() throws FileNotFoundException
	{
		Scanner in=new Scanner(dosya2);
		org.neuroph.core.data.DataSet testDs =new org.neuroph.core.data.DataSet(2, 1);
		while(in.hasNextDouble()) {
			DataSetRow satir= 
					new DataSetRow(new double[] {in.nextDouble(),in.nextDouble()},new double[]
							{in.nextDouble()});
			testDs.add(satir);
		}
		
		return testDs;
		
	}
	
	public org.neuroph.core.data.DataSet egitimVeriSeti() throws FileNotFoundException {
		Scanner in=new Scanner(dosya);
		org.neuroph.core.data.DataSet egitimDs = new org.neuroph.core.data.DataSet(2, 1);
		while(in.hasNextDouble()) {
			DataSetRow satir= 
					new DataSetRow(new double[] {in.nextDouble(),in.nextDouble()},new double[]
							{in.nextDouble()});
			egitimDs.add(satir);
		}
		return egitimDs;
	}
	
	public void egit() throws FileNotFoundException
	{
		NeuralNetwork<BackPropagation> sinirselAg = 
				new MultiLayerPerceptron(TransferFunctionType.SIGMOID,2,5,1);
		sinirselAg.setLearningRule(bp);
		sinirselAg.learn(egitimVeriSeti());
		sinirselAg.save("ogrenenAg.nnet");
		System.out.println("Eğitim tamamlandı");
		return ;
	}
	
	
public double test() throws FileNotFoundException {
		
		NeuralNetwork<BackPropagation> sinirselAg = NeuralNetwork.createFromFile("ogrenenAg.nnet");
		double toplamHata = 0;
		DataSet testVeriSeti = testVeriSeti();
		org.neuroph.core.data.DataSet satirlar = ((org.neuroph.core.data.DataSet) testVeriSeti);
		for(var satir : satirlar) {
			sinirselAg.setInput(satir.getInput());
			sinirselAg.calculate();
			toplamHata+=Math.pow(satir.getDesiredOutput()[0]-sinirselAg.getOutput()[0],2);
		}
		return toplamHata/testVeriSeti.size();
		
	}
	
	
}
