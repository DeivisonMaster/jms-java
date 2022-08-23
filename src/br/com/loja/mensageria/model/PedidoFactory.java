package br.com.loja.mensageria.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


public class PedidoFactory {
	private static final Logger LOGGER = Logger.getLogger(PedidoFactory.class.getName());
	
	public Pedido geraPedidoComValores() {
		Pedido pedido = new Pedido(2459, geraData("22/12/2016"), geraData("23/12/2016"), new BigDecimal("145.98"));
		
		Item motoG = geraItem(23, "Moto G");
		Item motoX = geraItem(51, "Moto X");
		
		pedido.adicionaItem(motoG);
		pedido.adicionaItem(motoX);
		
		return pedido;
	}

	private Item geraItem(int id, String nome) {
		Item item = new Item(id, nome);
		return item;
	}

	private Calendar geraData(String dataString) {
		Calendar calendar = Calendar.getInstance();
		try {
			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataString);
			calendar.setTime(data);
		} catch (ParseException e) {
			LOGGER.error("Erro ", e);
		}
		return calendar;
	}
}
