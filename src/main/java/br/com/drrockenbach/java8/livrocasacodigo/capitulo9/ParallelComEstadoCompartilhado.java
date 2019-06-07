package br.com.drrockenbach.java8.livrocasacodigo.capitulo9;

import java.util.stream.LongStream;

public class ParallelComEstadoCompartilhado {

	private static long total = 0;

	public static void main(String[] args) {

		/**
		 * Problema ao utilizar estado compartilhado com parallel. Nesse caso está sendo
		 * utilizado o atributo total para realizar a soma. O problema disso, é que ao
		 * ter mais de uma thread, cada thread pode acessar isso em um momento
		 * diferente, e acontece que a cada execução pode (e vai) resultar em um valor
		 * diferente.
		 * 
		 */

		LongStream.range(0, 1_000_000_000).parallel().filter(x -> x % 2 == 0).forEach( n -> total += n);

		System.out.println(total);

		/**
		 * Se você possuir mais de um core, cada vez que você rodar esse código obterá
		 * provavelmente um resultado diferente! O uso concorrente de uma variável
		 * compartilhada possibilita o interleaving de operações de forma indesejada.
		 * Claro, você pode utilizar o   synchronized   dentro do bloco do lambda para
		 * resolver isso, mas perdendo muita performance. Estado compartilhado entre
		 * threads continua sendo um problema.
		 */

	}

}
