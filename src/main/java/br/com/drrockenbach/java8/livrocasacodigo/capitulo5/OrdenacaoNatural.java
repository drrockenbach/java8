package br.com.drrockenbach.java8.livrocasacodigo.capitulo5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OrdenacaoNatural {

	/**
	 * E se tivermos uma lista de objetos que implementam Comparable, como porexemplo String?
	 *  <p>
	 * Antes faríamos: 
	 * <p>
	 * {@code List<String> palavras = Arrays.asList("Casa do Código", "Alura", "Caelum"); Collections.sort(palavras); } 
	 * </p>
	 * </p>
	 * Tentar fazer, no Java 8, palavras.sort() não compila. Não há esse método sort em List que não recebe parâmetro. 
	 * Nem teria como haver, pois o compilador não teria como saber se o objeto invocado é uma List de Comparable ou de suas filhas. 
	 * 
	 * Como resolver? Criar um lambda de Comparator que simplesmente delega o trabalho para o compareTo? 
	 * 
	 * Não é necessário, pois isso já existe, conforme exemplo abaixo.
	 */

	public static void main(String[] args) {
		
		/**
		 * Forma antiga. Da pra fazer assim, com Collections.sort. Mas tentar fazer paravras.sort() nem compila, pois sort espera parâmetros 
		 * Para resolver, passar para o palavras.sort(Comparator.naturalOrder()). Isso vai utilizar a ordem natural do Objeto, que no caso é string.
		 * 
		 * Caso tivesse uma classe usuário por exemplo, que implementa Comparable e tivesse seu proprio comparador, iria utilizar esse como ordem natural
		 * 
		 * Comparator.naturalOrder() retorna um Comparator que delega para o próprio objeto. Há também o Comparator.reverseOrder().
		 * 
		 */
		List<String> palavras = Arrays.asList("Casa do Código", "Alura", "Caelum");
//		Collections.sort(palavras);
		
		palavras.sort(Comparator.naturalOrder());
		
		palavras.forEach(s -> System.out.println(s));
		
	}

}
