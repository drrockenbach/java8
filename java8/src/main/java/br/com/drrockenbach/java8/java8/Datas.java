package br.com.drrockenbach.java8.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Datas {

	public static void main(String[] args) {
		LocalDate hoje = LocalDate.now();
		System.out.println(hoje);
		
		LocalDate olipiadasRio = LocalDate.of(2016, Month.JUNE, 5);
		
		int anos = olipiadasRio.getYear() - hoje.getYear();
		System.out.println(anos);
				
		Period periodo = Period.between(olipiadasRio, hoje);
		System.out.println(periodo.getDays());
		
		LocalDate nextOlimpicGames = olipiadasRio.plusYears(4);
		
		System.out.println("Tokio " +nextOlimpicGames);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println(nextOlimpicGames.format(formatter));
		
		DateTimeFormatter soDataLocaleLocal = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.getDefault());
		
		DateTimeFormatter comHoraLocaleLocal = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
		System.out.println(nextOlimpicGames.format(soDataLocaleLocal));
		
		LocalDateTime dataComHora = LocalDateTime.now();
		System.out.println(dataComHora.format(comHoraLocaleLocal));
		
		YearMonth mes = YearMonth.of(2019, 3);
		System.out.println(mes);
		
		LocalTime intervalo = LocalTime.of(3, 30);
		System.out.println(intervalo);
	}
	
}
