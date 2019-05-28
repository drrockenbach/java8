package br.com.drrockenbach.java8.livrocasacodigo.capitulo4;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class ExemploAndThen {

	public static void main(String[] args) {
		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);
		
		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);
		
		Consumer<Usuario> mostraMensagem = u -> System.out.println("antes de imprimir os nomes");
		
		Consumer<Usuario> imprimeNome = u -> System.out.println(u.getNome());
		
		usuarios.forEach(mostraMensagem.andThen(imprimeNome));
	}
	
}
