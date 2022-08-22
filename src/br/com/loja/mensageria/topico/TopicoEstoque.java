package br.com.loja.mensageria.topico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.loja.mensageria.service.ListaMensagens;
import br.com.loja.mensageria.util.JmsFactoryLocal;

public class TopicoEstoque {
	private static InitialContext contextJndi;
	
	public static void main(String[] args) throws Exception {
		contextJndi = obtemInstanciaJndi();
		Connection conexao = JmsFactoryLocal.conecta(contextJndi);
		conexao.setClientID("loja-estoque");
		conexao.start();
		
		obtemVariasMensagemDaFila(conexao);
		
		new Scanner(System.in).nextLine();
		
		conexao.close();
	}

	private static void obtemVariasMensagemDaFila(Connection conexao) throws JMSException, NamingException {
		MessageConsumer consumidor = criaConsumidorDeMensagens(conexao);
		consumidor.setMessageListener(new ListaMensagens());
	}
	
	private static MessageConsumer criaConsumidorDeMensagens(Connection conexao) throws JMSException, NamingException {
		Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		Topic topico = (Topic) contextJndi.lookup("loja");
		sessao.createDurableSubscriber(topico, "loja-assinatura");
		return sessao.createConsumer(topico);
	}
	
	private static String obtemDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	private static InitialContext obtemInstanciaJndi() throws NamingException {
		return new InitialContext();
	}
	
}
