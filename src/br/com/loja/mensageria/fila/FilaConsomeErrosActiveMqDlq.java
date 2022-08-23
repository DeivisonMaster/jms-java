package br.com.loja.mensageria.fila;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.loja.mensageria.util.JmsFactoryLocal;

public class FilaConsomeErrosActiveMqDlq {
	private static InitialContext contextJndi;
	
	public static void main(String[] args) throws Exception {
		contextJndi = obtemInstanciaJndi();
		Connection conexao = JmsFactoryLocal.conecta(contextJndi);
		conexao.start();
		
		obtemVariasMensagemDaFila(conexao);
		
		new Scanner(System.in).nextLine();
		conexao.close();
	}

	private static void obtemVariasMensagemDaFila(Connection conexao) throws JMSException, NamingException {
		MessageConsumer consumidor = criaConsumidorDeMensagens(conexao);
		consumidor.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message mensagem) {
				System.out.println(mensagem);
			}
		});
	}
	
	private static MessageConsumer criaConsumidorDeMensagens(Connection conexao) throws JMSException, NamingException {
		Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		Destination destino = (Destination) contextJndi.lookup("DLQ");
		MessageConsumer consumidor = sessao.createConsumer(destino);
		return consumidor;
	}
	
	private static String obtemDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	private static InitialContext obtemInstanciaJndi() throws NamingException {
		return new InitialContext();
	}
}
