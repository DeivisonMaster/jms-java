package br.com.loja.mensageria.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


public class JmsFactoryLocal {
	private static final Logger LOGGER = Logger.getLogger(JmsFactoryLocal.class.getName());
	private static Connection conexao;
	
	private JmsFactoryLocal() {
	}
	
	public static Connection conecta(InitialContext contextJndi) {
		try {
			contextJndi = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) contextJndi.lookup("ConnectionFactory");
			conexao = factory.createConnection();
		} catch (JMSException | NamingException e) {
			LOGGER.error("Erro ao conectar no serviço JMS ", e);
		} finally {
			try {
				contextJndi.close();
			} catch (NamingException e) {
				LOGGER.error("Erro ao encerrar serviço JNDI ", e);
			}
		}
		return conexao;
	}
}
