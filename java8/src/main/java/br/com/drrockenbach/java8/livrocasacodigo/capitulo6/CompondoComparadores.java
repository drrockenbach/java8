package br.com.drrockenbach.java8.livrocasacodigo.capitulo6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class CompondoComparadores {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);
		Usuario user4 = new Usuario("Diomar Vem Antes guilherme", 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);
		usuarios.add(user4);

		Comparator<Usuario> c = Comparator.comparingInt(Usuario::getPontos)
										  .thenComparing(Usuario::getNome);
		
		usuarios.sort(c);
		
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
		Comparator<Usuario> cReversed = Comparator.comparingInt(Usuario::getPontos)
				  .thenComparing(Usuario::getNome).reversed();

		usuarios.sort(cReversed);

		usuarios.forEach(u -> System.out.println(u.getNome()));

	}

}
