package br.com.drrockenbach.java8.livrocasacodigo.capitulo11;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Testes {

	public static void main(String[] args) {

		Customer paulo = new Customer("Paulo Silveira");
		Customer rodrigo = new Customer("Rodrigo Turini");
		Customer guilherme = new Customer("Guilherme Silveira");
		Customer adriano = new Customer("Adriano Almeida");

		Product bach = new Product("Bach Completo", Paths.get("/music/bach.mp3"), new BigDecimal(100));
		Product poderosas = new Product("Poderosas Anita", Paths.get("/music/poderosas.mp3"), new BigDecimal(90));
		Product bandeira = new Product("Bandeira Brasil", Paths.get("/images/brasil.jpg"), new BigDecimal(50));
		Product beauty = new Product("Beleza Americana", Paths.get("beauty.mov"), new BigDecimal(150));
		Product vingadores = new Product("Os Vingadores", Paths.get("/movies/vingadores.mov"), new BigDecimal(200));
		Product amelie = new Product("Amelie Poulain", Paths.get("/movies/amelie.mov"), new BigDecimal(100));

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime yesterday = today.minusDays(1);
		LocalDateTime lastMonth = today.minusMonths(1);
		Payment payment1 = new Payment(asList(bach, poderosas), today, paulo);
		Payment payment2 = new Payment(asList(bach, bandeira, amelie), yesterday, rodrigo);
		Payment payment3 = new Payment(asList(beauty, vingadores, bach), today, adriano);
		Payment payment4 = new Payment(asList(bach, poderosas, amelie), lastMonth, guilherme);
		Payment payment5 = new Payment(asList(beauty, amelie), yesterday, paulo);
		
		List<Payment> payments = asList(payment1, payment2, payment3, payment4, payment5);
		
		BigDecimal monthlyFee = new BigDecimal("99.90");
		
		Subscription s1 = new Subscription(monthlyFee, yesterday.minusMonths(5), paulo);
		Subscription s2 = new Subscription(monthlyFee, yesterday.minusMonths(8), today.minusMonths(1), rodrigo);
		Subscription s3 = new Subscription(monthlyFee, yesterday.minusMonths(5), today.minusMonths(2), adriano);
		
		List<Subscription> subscriptions = asList(s1, s2, s3);
		
		ordenandoOsPagamentosPorDataEImprimir(payments);
		
		calcularValorTotalDoPayment1(payment1);
		
		calcularValorDeTodosPagamentos(payments);
		
		produtosMaisVendidos(payments);
		
		vendasPorProduto(payments);
		
		produtosCadaCliente(payments);
		
		clienteMaisEspecial(payments);
		
		agrupandoPorDatas(payments);
		
		faturamentoPorMes(payments);
		
		calcularQuantidadeMesesPagos(subscriptions);
		
	}

	private static void calcularQuantidadeMesesPagos(List<Subscription> subscriptions) {

		Subscription s1 = subscriptions.get(0);
		Subscription s2 = subscriptions.get(1);
		Subscription s3 = subscriptions.get(2);
		
		/**
		 * Se a assinatura ainda estiver ativa, ou seja, a data end não estiver preenchida, compara com o dia atual.
		 */
		long meses = ChronoUnit.MONTHS.between(s1.getBegin(), LocalDateTime.now());
		
		long meses2 = ChronoUnit.MONTHS.between(s2.getBegin(), s2.getEnd().orElse(LocalDateTime.now()));
		
		BigDecimal valorTotalS1 = s1.getMonthlyFee().multiply(new BigDecimal(meses));
		
		
		BigDecimal totalPago = subscriptions.stream().map(s -> s.getTotalPaid()).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		System.out.println("Meses: "+meses);
		System.out.println("Meses2: "+meses2);
		System.out.println("valorTotalS1: "+valorTotalS1);
		System.out.println("totalPago: "+totalPago);
		
	}

	private static void faturamentoPorMes(List<Payment> payments) {

		Map<YearMonth, List<Payment>> pagamentosAgrupadosPorData = payments.stream().collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate())));
		
		
		Map<YearMonth, BigDecimal> pagamentosAnoMes = pagamentosAgrupadosPorData.entrySet().stream()
			.collect(Collectors.groupingBy(Map.Entry::getKey, 
					 Collectors.reducing(BigDecimal.ZERO, entry -> entry.getValue().stream().map(Payment::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add), BigDecimal::add)));
		
		pagamentosAnoMes.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(System.out::println);
		
	}

	private static void agrupandoPorDatas(List<Payment> payments) {

		Map<YearMonth, List<Payment>> paymentsPerMonth = payments.stream().collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate())));
		
		paymentsPerMonth.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey())).forEach(System.out::println);;
		
	}

	private static void clienteMaisEspecial(List<Payment> payments) {

		// Function que soma todos os valores de produtos de um payment
		Function<Payment, BigDecimal> paymentToTotal = p -> p.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		/**
		 * Agrupa os payments por customer, somando os totais de cada payment do customer. Assim, se um customer tiver mais de um payment vai somar todos num único
		 */
		Map<Customer, BigDecimal> totalValuePerCustomer = payments.stream().collect(Collectors.groupingBy(Payment::getCustomer,
								  Collectors.reducing(BigDecimal.ZERO, paymentToTotal, BigDecimal::add)));
		
		System.out.println("--------------------");
		System.out.println("Vendas por cliente: ");
		
		totalValuePerCustomer.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getValue())).forEach(System.out::println);
		
		
	}

	private static void produtosCadaCliente(List<Payment> payments) {

		/**	
		 * Primeiro agrupa os pagamentos de cada cliente. O problema é que queremos os produtos agrupados por cliente, 
		 * então é necessário a próxima implementação.
		 */
		
		Map<Customer, List<Payment>> mapPaymentsByCustomer = payments.stream().collect(Collectors.groupingBy(p -> p.getCustomer()));
		
		/**
		 * Essa abordagem retorna um map de customer com o value sendo um List<List<Product>>. É necessário aplicar um flat nessa lista.
		 * O problema é que não existe nesse caso um método pronto para isso. 
		 */
		Map<Customer, List<List<Product>>> productsbyCustomer = payments.stream()
			.collect(Collectors.groupingBy(Payment::getCustomer, Collectors.mapping(Payment::getProducts, Collectors.toList())));
		
		/**
		 * Solução com flatMap
		 */
		
		/**
		 * Usa mos o Collectors.toMap para criar um novo mapa no qual a chave continua a
		 * mesma ( Map.EntrygetKey) mas o valor é o resultado do flatMap dos Liststream
		 * de todas as listas.
		 */
		
		Map<Customer, List<Product>> productsbyCustomer2 = 
				productsbyCustomer.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().flatMap(List::stream).collect(Collectors.toList())));
		
		System.out.println("---------------- Produtos por cliente --------------");
		
		productsbyCustomer2.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getName()))
		.forEach(System.out::println);
		
		
		/***** 
		 * 
		 * *********** Outra possível solução ************
		 * 
		 * Como sempre, há outras formas de resolver o mesmo problema. Podemos usar o 
		 * reducing mais uma vez, pois queremos acumular as listas de cada cliente agrupado.
		 * 
		 */

		// O código do livro, comentando abaixo, estava com erro		
		
//		payments.stream()
//			.collect(Collectors.groupingBy(Payment::getCustomer,
//					 Collectors.reducing(Collections.emptyList(), 
//							 			 Payment::getProducts,
//							 			 (l1, l2) -> {
//							 				 List<Product> l = new ArrayList<>();
//							 				 l.addAll(l1);
//							 				 l.addAll(l2);
//							 				 return l;
//							 			 })));
		
		Map<Customer, List<Product>> productsbyCustomer3 = payments.stream().collect(Collectors.groupingBy(Payment::getCustomer,
				Collectors.reducing(new ArrayList<Product>(), p -> p.getProducts(), (l1, l2) ->  { l1.addAll(l2); return l1; } )));
	
		System.out.println("---------------- Produtos por cliente - solução 2 --------------");
		
		productsbyCustomer3.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getName()))
		.forEach(System.out::println);
		
	}

	private static void vendasPorProduto(List<Payment> payments) {

		Map<Product, BigDecimal> collect = payments.stream().flatMap(p -> p.getProducts().stream())
			.collect(Collectors.groupingBy(Function.identity(), 
					 Collectors.reducing(BigDecimal.ZERO, p1 -> p1.getPrice(), (total, p2) -> total.add(p2) ))) // reducing escrito por extenso para melhor entendimento
			         //Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));				// O mesmo código reduzido seria conforme ao lado
		;
		
		System.out.println("---------------------");
		System.out.println("Lista de produtos do menos vendido ao mais vendido por valor: ");
		System.out.println("---------------------");
		
		collect.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getValue()))
		.forEach(System.out::println);
		
		
		
		
		
	}

	private static void produtosMaisVendidos(List<Payment> payments) {

		/**
		 * Primeiro gerar o stream de todos os produtos
		 * Depois realizar o count de produtos, para ver quantas vezes cada um aparece. 
		 * Isso pode ser feito com o groupingBy do Collectors. O próprio método groupingBy, ao agrupar já pode realizar uma operação de count, conforme abaixo.
		 * Essa operação de count nada mais é que um reduce que faz um count
		 */
		
		Map<Product, Long> topProducts = payments.stream()
			.flatMap(p -> p.getProducts().stream())
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
//		topProducts.entrySet().forEach(System.out::println);
		
		/**
		 * O mais vendido. Daria para encadear tudo numa única instrução junto com a primeira
		 */
		topProducts.entrySet().stream()
			.max(Comparator.comparing(Map.Entry::getValue))
			.ifPresent(System.out::println);
		
		/**
		 * Encadeando tudo ficaria assim
		 */
		
		Optional<Entry<Product, Long>> max = payments.stream()
			.flatMap(p -> p.getProducts().stream())
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
			.entrySet().stream().max( Comparator.comparing(entry -> entry.getValue() )); // Coloquei o comparing com a forma mais longa como didatica.
		
		max.ifPresent( entry -> System.out.println("Produto mais vendido: "+entry.getKey().getName() + " - Quantidade: "+entry.getValue()));
		
	}

	private static void calcularValorDeTodosPagamentos(List<Payment> payments) {

		/**
		 * Isso retorna um stream com os totais de cada produto, ainda não é a soma total 
		 */
		Stream<BigDecimal> precos = payments.stream()
			.map(prs -> prs.getProducts().stream().map(p -> p.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));

		/**
		 * Agora sim tem o total geral
		 */
		BigDecimal totalGeral = payments.stream()
			.map(p -> p.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		System.out.println("Total utilizando map: "+totalGeral);
		
		/**
		 * o código está um pouco repetivo, da pra resolver isso de forma mais simples com o flatMap.
		 * Assim, o retorno será um Stream<BigDecimal> com o valor de todos os produtos de cada payment e não um Stream com a soma de todos os payments, como no exemplo anterior.
		 * Desta forma, basta aplicar o reduce uma única vez.
		 */
		
		Stream<BigDecimal> priceOfEachProduct = payments.stream().flatMap(p -> p.getProducts().stream().map(Product::getPrice));
		
		/**
		 * Se está difícil ler este código, leia-o passo a passo. O importante é enxergar essa função:
		 */
				
		Function<Payment, Stream<BigDecimal>> mapper =
				p -> p.getProducts().stream().map(Product::getPrice);
				
		/**
		 * Essa função mapeia um Payment para o Stream que passeia por todos os seus
		 * produtos. E é por esse exato motivo que precisamos invocar depois o flatMap e
		 * não o map, caso contrário obteríamos um Stream<Stream<BigDecimal>>. Para
		 * somar todo esse Stream<BigDecimal>, basta realizarmos a operação de reduce
		 * que conhecemos:
		 */
				
		BigDecimal totalComFlatMap = priceOfEachProduct.reduce(BigDecimal.ZERO, (total, valor) -> total.add(valor));
		
		System.out.println("Total utilizando flatMap: "+totalComFlatMap);
		
		
		/******* Sobre o flatMap ******
		 * 
		 *    ********     *********
		 * 
		 * 	O flatMap é sempre útil quando o retorno de um map vai ser Stream<Stream<T>>. Trocando de map para flatMap
		 * esse retorno passará a ser um único Stream<T> com todos os valores. É como se "achatasse" ou concatenesse todos os streams<T> internos para um único.
		 * 
		 * Escrevi isso mais para fixar mesmo esse conceito.
		 * 
		 */
		
		BigDecimal total = payments.stream().map(p -> p.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		System.out.println("Total geral a partir do método getTotal criado na classe Payment: "+total);
		
		
	}

	/**
	 * Há um problema. Se preço fosse um int, poderíamos usar o mapToDouble e
	 * invocar o sum do DoubleStream resultante. Não é o caso. Teremos um
	 * Stream<BigDecimal> e ele não possui um sum. Nesse caso precisaremos fazer a
	 * redução na mão, realizando a soma de BigDecimal. Podemos usar o (total,
	 * price) -> total.add(price), mas fica ainda mais fácil usando um method
	 * reference:
	 * 
	 * @param payment1
	 */
	private static void calcularValorTotalDoPayment1(Payment payment1) {

		BigDecimal valorTotal = payment1.getProducts().stream()
			.map( p -> p.getPrice() )
			.reduce(BigDecimal.ZERO, (total, valor) -> total.add(valor)); // Fiz assim para ficar mais claro, daria para reduzir conforme abaixo
//			.reduce(BigDecimal::add).ifPresent(v -> System.out.println("Valor total dos produtos de payment1: "+v));
			
		System.out.println("Valor total dos produtos de payment1: "+valorTotal);
		
	}

	private static void ordenandoOsPagamentosPorDataEImprimir(List<Payment> payments) {

		payments.stream().sorted(Comparator.comparing(p -> p.getDate() )).forEach(System.out::println);
			
		
	}

}
