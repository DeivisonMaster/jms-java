package br.com.loja.mensageria.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;


public class ListaMensagens implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(ListaMensagens.class.getName());

	@Override
	public void onMessage(Message mensagem) {
		TextMessage textMensagem = (TextMessage) mensagem;
		try {
			System.out.println(textMensagem.getText());
		} catch (JMSException e) {
			LOGGER.error("Erro ao obter mensagem ", e);
		}
	}
	
}
