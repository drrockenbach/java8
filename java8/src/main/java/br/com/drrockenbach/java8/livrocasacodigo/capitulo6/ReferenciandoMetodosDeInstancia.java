package br.com.drrockenbach.java8.livrocasacodigo.capitulo6;

import java.util.function.Consumer;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class ReferenciandoMetodosDeInstancia {

	public static void main(String[] args) {

		Usuario rodrigo = new Usuario("Rodrigo Turini", 50);
		/**
		 * Referencia a uma única instância de usuário, que retorna um runnable
		 */
		Runnable bloco = rodrigo::tornaModerador;
		bloco.run();

		/**
		 * O bloco acima é o mesmo que o abaixo
		 */

		Runnable bloco2 = () -> rodrigo.tornaModerador();

		/**
		 * Repare que isso é bastante diferente de quando fazemos
		 * Usuario::tornaModerador, pois estamos referenciando o método do meu usuário
		 * existente, e não de qualquer objeto do tipo Usuario. Como enxergar melhor a
		 * diferença? O lambda rodrigo::tornaModerador pode ser inferido para um
		 * Runnable, pois esse método não recebe argumentos e deixamos claro de qual
		 * usuário é para ser invocado. No caso do Usuario::tornaModerador, o compilador
		 * pode inferir o lambda para Consumer<Usuario> pois, apesar de tornaModerador
		 * não receber um argumento, é necessário saber de qual usuário estamos falando.
		 * Veja:
		 */

		Usuario rodrigo2 = new Usuario("Rodrigo Turini", 50);
		Consumer<Usuario> consumer = Usuario::tornaModerador;
		consumer.accept(rodrigo2); // Isso vai invocar o método tornarModerador

		/**
		 * O que não pode é misturar expectativas de número e tipos diferentes dos
		 * argumentos e retorno! Por exemplo, o Java não vai aceitar fazer Runnable
		 * consumer = Usuario::tornaModerador, pois esse method reference só pode ser
		 * atribuído auma interface funcional que necessariamente recebaumparâmetro em
		 * seumétodo abstrado, que seria utilizado na invocação de tornaModerador.
		 */
	}

}
