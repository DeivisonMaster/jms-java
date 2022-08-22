package br.com.loja.mensageria.fila;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.loja.mensageria.service.ListaMensagens;
import br.com.loja.mensageria.util.JmsFactoryLocal;

public class FilaProdutor {
	private static InitialContext contextJndi;
	private static Session sessao;
	
	public static void main(String[] args) throws Exception {
		contextJndi = obtemInstanciaJndi();
		Connection conexao = JmsFactoryLocal.conecta(contextJndi);
		conexao.start();
		
		MessageProducer produtor = criaProdutorDeMensagens(conexao);
		
		for (int i = 0; i < 1000; i++) {
			Message mensagem = sessao.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			produtor.send(mensagem);
		}
		
		conexao.close();
	}
	
	private static MessageProducer criaProdutorDeMensagens(Connection conexao) throws JMSException, NamingException {
		sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		Destination destino = (Destination) contextJndi.lookup("financeiro");
		return sessao.createProducer(destino);
	}
	
	private static String obtemDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	private static InitialContext obtemInstanciaJndi() throws NamingException {
		return new InitialContext();
	}
	
}
