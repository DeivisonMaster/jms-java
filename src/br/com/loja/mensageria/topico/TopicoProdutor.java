package br.com.loja.mensageria.topico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.loja.mensageria.util.JmsFactoryLocal;

public class TopicoProdutor {
	private static InitialContext contextJndi;
	private static Session sessao;
	
	public static void main(String[] args) throws Exception {
		contextJndi = obtemInstanciaJndi();
		Connection conexao = JmsFactoryLocal.conecta(contextJndi);
		conexao.start();
		
		MessageProducer produtor = criaProdutorDeMensagens(conexao);
		Message mensagem = sessao.createTextMessage("<pedido><id> 300 </id><e-book>true</e-ebook></pedido>");
		mensagem.setBooleanProperty("ebook", true);
		produtor.send(mensagem);
		
		new Scanner(System.in).nextLine();
		
		conexao.close();
	}
	
	private static MessageProducer criaProdutorDeMensagens(Connection conexao) throws JMSException, NamingException {
		sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		Destination topico = (Destination) contextJndi.lookup("loja");
		return sessao.createProducer(topico);
	}
	
	private static String obtemDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	private static InitialContext obtemInstanciaJndi() throws NamingException {
		return new InitialContext();
	}
	
}
