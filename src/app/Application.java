package app;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import service.Cotacao;
import service.Produto;
import service.Usuario;
import swing.ui.DefaultList;

/**
 *
 * @author itakenami
 */
public class Application {

	private static Application instance;
	public DefaultList listUsuario;
	public DefaultList listProdutos;
	public DefaultList listCotacoes;
	public Main main;

	private Application() {
		this.main = new Main();
		this.listUsuario = new DefaultList(this.main, new Usuario());
		this.listProdutos = new DefaultList(this.main, new Produto());
		this.listCotacoes = new DefaultList(this.main, new Cotacao());

	}

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}

}
