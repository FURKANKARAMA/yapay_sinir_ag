package odev;

import java.io.FileNotFoundException;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {
		YSA ysa = new YSA(1000,0.00001,0.2);
		ysa.egit();
		System.out.println("test hata:"+ysa.test());
		//System.out.println("egitim hata:"+());

	}

}