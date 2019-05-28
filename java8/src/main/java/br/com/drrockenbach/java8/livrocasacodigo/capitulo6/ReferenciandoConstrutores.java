package br.com.drrockenbach.java8.livrocasacodigo.capitulo6;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class ReferenciandoConstrutores {

	public static void main(String[] args) {
		
		/**
		 * Capítulo 6.6 Referenciando construtores
		 */
		
		/**
		 * Vamos utilizar a interface Supplier,
		 * também presente no pacote java.util.function. Note a assinatura de seu método:

			@FunctionalInterface
			public interface Supplier<T>{
				T get();
			}
			
			Ela tem a forma de uma factory. Podemos guardar a expressão Usuario::new
			em um Supplier<Usuario> e chamar seu método get sempre que for desejada
			a instanciação de um novo objeto do tipo Usuario:
		 */
		
		Supplier<Usuario> criadorDeUsuarios = Usuario::new;
		Usuario novo = criadorDeUsuarios.get();
		
		
		/**
		 * Para chamar um construtor com apenas um parâmetro
		 */
		
		Function<String, Usuario> criadorDeUsuarios2 = Usuario::new;
		Usuario rodrigo = criadorDeUsuarios2.apply("Rodrigo Turini");
		Usuario paulo = criadorDeUsuarios2.apply("Paulo Silveira");
		
		
		/**
		 * Para chamar um construtor com dois parâmetro
		 */
		
		BiFunction<String, Integer, Usuario> criadorDeUsuarios3 = Usuario::new;
		Usuario rodrigo2 = criadorDeUsuarios3.apply("Rodrigo Turini", 50);
		Usuario paulo2 = criadorDeUsuarios3.apply("Paulo Silveira", 300);
		
		/**
		 * Para mais de 2 parâmetros não existe pronto.
		 * 
		 * Mas se eu quiser criar um usuário a partir de um
		   construtor com 3 argumentos a API padrão não vai me ajudar, não existe nenhuma
		   interface para esse caso! Não se preocupe, se fosse necessário poderíamos facilmente
		   implementar uma TriFunction que resolveria o problema.Você verá que a API
           não possui todas as combinações, pois não seria possível com essa abordagem de
		   lambda do Java 8.
		 * 
		 */
	
		
		/**
		 * Podemos referenciar um método sobreescrito da classe mãe. Neste
		   caso, usamos a palavra reservada super, como em super::toString.
		 */
		
	}
	
}
