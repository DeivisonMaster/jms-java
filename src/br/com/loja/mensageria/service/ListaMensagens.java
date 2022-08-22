package br.com.loja.mensageria.service;

import javax.jms.Message;
import javax.jms.MessageListener;

public class ListaMensagens implements MessageListener {

	@Override
	public void onMessage(Message mensagem) {
		System.out.println(mensagem);
	}

}
