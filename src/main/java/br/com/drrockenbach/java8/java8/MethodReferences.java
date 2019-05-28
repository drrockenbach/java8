package br.com.drrockenbach.java8.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MethodReferences {

	public static void main(String[] args) {
		List<String> palavras = new ArrayList<>();
		palavras.add("Diomar");
		palavras.add("Andressa");
		palavras.add("Rafael");
		palavras.add("Vinícius");
		
		comparacaoJava8MethodReferences(palavras);
		

	}

	private static void comparacaoJava8MethodReferences(List<String> palavras) {
		
		/**
		 * Melhorando ainda mais o exemplo de comparação de strings
		 */
//		palavras.sort((s1,  s2) -> Integer.compare(s1.length(), s2.length()) );
		
		/**************************
		 * Comparator.comparing - Factory de comparator. Vai utilizar para comparação o valor passado, conforme abaixo, onde está passando o length para comparar
		 **************************/
		palavras.sort(Comparator.comparing(s -> s.length()));
		
		// ou
		
		// Isso é a mesma coisa que o lambda acima. Ele diz, dado que vai receber uma String, utiliza o lenght para comparar
		palavras.sort(Comparator.comparing(String::length));
		
		/**
		 * O código acima, é o mesmo que essa abaixo, mais verboso
		 */
		
		
		// Essa funcion é o que o comparing espera. Essa interface funcional tem a função R apply(T t);
		// Com isso, estamos dizendo que essa função vai receber uma String e vai retornar um Integer, que é o lenght
		Function<String, Integer> funcao = s -> s.length();
		
		//ou
		
		Function<String, Integer> funcao2 = String::length;
		
		Comparator<String> comparator = Comparator.comparing(funcao);
		palavras.sort(comparator);
		
		System.out.println("Novo : "+palavras);
	}
	
	
	
	
	
	
	
	
	
}