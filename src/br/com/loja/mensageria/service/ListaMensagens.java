package br.com.loja.mensageria.service;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import br.com.loja.mensageria.model.Pedido;


public class ListaMensagens implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(ListaMensagens.class.getName());

	@Override
	public void onMessage(Message mensagem) {
		ObjectMessage objMensagem = (ObjectMessage) mensagem;
		try {
			Pedido pedido = (Pedido) objMensagem.getObject();
			System.out.println(pedido.getCodigo());
		} catch (JMSException e) {
			LOGGER.error("Erro ao obter mensagem ", e);
		}
	}
	
}
