package br.com.drrockenbach.java8.livrocasacodigo.capitulo8;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamDeRandom {

	public static void main(String[] args) {

		/**
		 * Isso vai gerar um stream infinito de número randomicos. Mas ele não será
		 * gerado no momento do generate, só apenas quando for executada alguma
		 * operação.
		 */

		/** Muita atenção ***
		 *  
		 * Dependendo da operação que for chamada, nunca terminará de
		 * executar, conforme o exemplo abaixo:
		 * 
		 * Agora precisamos de cuidado. Qualquer operação que necessite passar por todos
		 * os elementos do Stream nunca terminará de executar. Por exemplo: int valor =
		 * stream.sum();
		 * 
		 * Você pode apenas utilizar operações de curto-circuito em Streams infinitos.
		 * 
		 */

		Random random = new Random(0);
		IntStream stream = IntStream.generate(() -> random.nextInt());

		/**
		 * Operações de curto circuito
		 * 
		 * São operações que não precisam processar todos os elementos. Um exemplo seria
		 * pegar apenas os 100 primeiros elementos com limit:
		 * 
		 */

		List<Integer> list = stream.limit(100).boxed().collect(Collectors.toList());

		/** Método boxed acima ***
		 * Repare a invocação de boxed. Ele retorna um
		 * Stream<Integer> em vez do IntStream, possibilitando a invocação a collect da
		 * forma que já vimos. Sem isso, teríamos apenas a opção de fazer
		 * IntStream.toArray, ou então de chamar o collect que recebe três argumentos,
		 * mas não teríamos onde guardar os números.
		 */

	}

}
