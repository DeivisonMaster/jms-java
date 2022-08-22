package br.com.loja.mensageria.fila;

import java.util.Scanner;

import javax.jms.Connection;

import br.com.loja.mensageria.util.JmsFactoryLocal;

public class FilaConsome {
	public static void main(String[] args) throws Exception {
		Connection conexao = JmsFactoryLocal.conecta();
		conexao.start();
		
		new Scanner(System.in).nextLine();
		conexao.close();
	}
}
