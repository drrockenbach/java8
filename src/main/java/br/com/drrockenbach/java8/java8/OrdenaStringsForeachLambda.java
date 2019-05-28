package br.com.drrockenbach.java8.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class OrdenaStringsForeachLambda {

	public static void main(String[] args) {
		List<String> palavras = new ArrayList<>();
		palavras.add("Diomar");
		palavras.add("Andressa");
		palavras.add("Rafael");
		palavras.add("Vinícius");
		
		Comparator<String> comparador = new ComparadorPorTamanho();
		
		comparacaoModoAntigo(palavras, comparador);
		comparacaoJava8ListSort(palavras, comparador);
		comparacaoJava8ListSort2(palavras);
		comparacaoJava8ListForeach1(palavras);
		comparacaoJava8ListForeach2(palavras);
		comparacaoJava8ListForeachLambda(palavras);

	}

	private static void comparacaoModoAntigo(List<String> palavras, Comparator<String> comparador) {
		
		Collections.sort(palavras, comparador);
		System.out.println("Antigo: "+ palavras);
	}
	
	/**
	 * No java 8, a interface list ganhou um método sort
	 * @param palavras
	 */
	private static void comparacaoJava8ListSort(List<String> palavras, Comparator<String> comparador) {
		
		palavras.sort(comparador);
		System.out.println("Novo : "+palavras);
	}
	
	/**
	 * No java 8, a interface list ganhou um método sort. Utilização com Lambda
	 * @param palavras
	 */
	private static void comparacaoJava8ListSort2(List<String> palavras) {
		
//		palavras.sort(new Comparator<String>() {
//
//			@Override
//			public int compare(String s1, String s2) {
//				if (s1.length() < s2.length()) 
//					return -1;
//				if (s1.length() > s2.length()) 
//					return 1;
//				return 0;
//			};
//		});
		
		/**
		 * Lambda equivalente ao código acima
		 */
		
//		palavras.sort((s1,  s2) -> {
//				if (s1.length() < s2.length()) 
//					return -1;
//				if (s1.length() > s2.length()) 
//					return 1;
//				return 0;
//			}
//		);
		
		/************************************
		 * Melhor ainda.
		 * Na classe MethodReferences.java, existe uma forma ainda mais resumida de aplicar a comparação
		 ************************************/
		palavras.sort((s1,  s2) -> Integer.compare(s1.length(), s2.length()) );
		
		System.out.println("Novo : "+palavras);
	}
	
	/**
	 * Java 8 List forEach, utilizando uma class que implementa Consumer
	 * @param palavras
	 */
	private static void comparacaoJava8ListForeach1(List<String> palavras) {
		
		Consumer<String> consumer = new ImprimeNaLinha();
		
		palavras.forEach(consumer);
	}
	
	/**
	 * Java 8 List forEach, utilizando uma class anonima que implementa Consumer.
	 * @param palavras
	 */
	private static void comparacaoJava8ListForeach2(List<String> palavras) {
		
//		Consumer<String> consumer = new Consumer<String>() {
//
//			@Override
//			public void accept(String s) {
//				System.out.println(s);
//			}
//			
//		};
		
		palavras.forEach(new Consumer<String>() {

			@Override
			public void accept(String s) {
				System.out.println(s);
			}
			
		});
	}
	
	/**
	 * Java 8 List forEach, utilizando lambda
	 * @param palavras
	 */
	private static void comparacaoJava8ListForeachLambda(List<String> palavras) {
		
		palavras.forEach((String s) -> {
				System.out.println(s);
		});
		
		
		/**
		 * Da pra fazer assim, ou conforme no próximo exemplo abaixo
		 */
		Consumer<String> impressor = s -> System.out.println(s);
		
		// ou 
		
		Consumer<String> impressor2 = System.out::println;
		
		palavras.forEach(impressor);
		
		/**
		 * Como só tem um parâmetro, não é obrigado e utilizar os parenteses e nem o tipo.
		 * E se dentro da chaves só tem um comando, elas não são necessárias.
		 */
		System.out.println("-----------Lambda---------------");
		palavras.forEach(s -> System.out.println(s));
		
		// ou 
		
		palavras.forEach(System.out::println);
			
	}
	
}

class ImprimeNaLinha implements Consumer<String> {

	@Override
	public void accept(String s) {
		System.out.println(s);
	}
	
}

class ComparadorPorTamanho implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		if (s1.length() < s2.length()) 
			return -1;
		if (s1.length() > s2.length()) 
			return 1;
		return 0;
	}
	
}