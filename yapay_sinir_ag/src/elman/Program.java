package elman;

import java.io.FileNotFoundException;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {
		Elman elman = new Elman();
		elman.egit();
		System.out.println("egitim Elde edilen Hata: "+elman.egitim());
		System.out.println("Testter Elde edilen Hata: "+elman.test());

	}

}

