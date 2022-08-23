package br.com.loja.mensageria.topico;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;

import org.apache.log4j.Logger;

import br.com.loja.mensageria.model.Pedido;
import br.com.loja.mensageria.model.PedidoFactory;
import br.com.loja.mensageria.service.ListaMensagens;
import br.com.loja.mensageria.util.JmsFactoryLocal;

public class TopicoProdutor {
	private static final Logger LOGGER = Logger.getLogger(TopicoProdutor.class.getName());
	private static InitialContext contextJndi;
	private static Session sessao;
	
	public static void main(String[] args) throws Exception {
		contextJndi = obtemInstanciaJndi();
		Connection conexao = JmsFactoryLocal.conecta(contextJndi);
		conexao.start();
		
		MessageProducer produtor = criaProdutorDeMensagens(conexao);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
//		String xml = formataPedidoParaXML(pedido);
		//Message mensagem = sessao.createTextMessage(xml);
		
		Message mensagem = sessao.createObjectMessage(pedido);
		produtor.send(mensagem);
		
		conexao.close();
	}

	private static String formataPedidoParaXML(Pedido pedido) {
		StringWriter conversor = new StringWriter();
		JAXB.marshal(pedido, conversor);
		return conversor.toString();
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
