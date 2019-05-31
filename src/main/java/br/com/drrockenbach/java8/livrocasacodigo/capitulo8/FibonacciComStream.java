package br.com.drrockenbach.java8.livrocasacodigo.capitulo8;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public class FibonacciComStream {

	public static void main(String[] args) {

		/**
		 * O Supplier passado ao generate pode servir para gerar um Stream infinito de
		 * constantes, por exemplo IntStream.generate(() -> 1) e Stream.generate(() ->
		 * new Object()).
		 */

		/**
		 * Pode ser útil para um Supplier manter estado. Nesse caso, precisamos usar uma
		 * classe ou classe anônima, pois dentro de um lambda não podemos declarar
		 * atributos. Va mos gerar a sequência infinita de números de Fibonacci de
		 * maneira lazy e imprimir seus 10 primeiros elementos:
		 */

		IntStream.generate(new Fibonacci()).limit(100).forEach(System.out::println);

		/**
		 * Aqui a class Fibonacci é gerada uma única vez e chamada 10 vezes pelo limit.
		 */

		/**
		 * Veremos quemanter o estado em uma interface funcional pode limitar os
		 * recursos de paralelização que um Stream fornece.
		 */
	}

}

class Fibonacci implements IntSupplier {
	private int anterior = 0;
	private int proximo = 1;

	public int getAsInt() {
		proximo = proximo + anterior;
		anterior = proximo - anterior;
		return anterior;
	}
}
