/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author itakenami
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import swing.annotation.GridHeader;
import swing.model.DefaultModel;
import swing.ui.ModelField;
import api.wadl.annotation.XMLCast;
import client.crud.Service;
import client.exception.ValidationException;
import client.request.ApacheRequest;

import com.google.gson.reflect.TypeToken;

/**
 *
 * @author itakenami
 */
@XMLCast(thisClassFrom = "models.Projeto")
public class Cotacao implements DefaultModel<Cotacao> {

	@GridHeader(name = "ID", size = 10)
	public Long id;
	@GridHeader(name = "Cpf", size = 100)
	public String cpf;
	public String solicitante;
	@GridHeader(name = "Data Solicitação", size = 50)
	public Date data_solicitacao;
	public Set<Produto> produtos;

	public static Service<Cotacao> service = new Service<Cotacao>(new ApacheRequest("http://localhost:8080/restee/api/cotacoes"), Cotacao.class,
			new TypeToken<List<Cotacao>>() {
	}.getType());

	@Override
	public String toString() {
		return this.cpf;
	}

	@Override
	public List<Cotacao> findStart() {
		return service.findAll();
	}

	@Override
	public Cotacao findById(final Long id) {
		return service.findById(id);
	}

	@Override
	public Cotacao save(final Long id, final HashMap<String, Object> map) throws ValidationException {

		final HashMap<String, String> vo = new HashMap<String, String>();

		vo.put("cotacao.cpf", map.get("cpf").toString());
		vo.put("cotacao.solicitante", map.get("solicitante").toString());
		vo.put("cotacao.data_solicitacao", map.get("data_solicitacao").toString());

		final String[] sel = (String[]) map.get("Produtos");

		for (final String element : sel) {
			vo.put("Produtos[" + element + "].id", element);
		}

		return service.save(id, vo);
	}

	@Override
	public ModelField getGridFields() {
		final ModelField gf = new ModelField();
		gf.addField(this.id);
		gf.addField(this.cpf);

		final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

		System.out.println("Tah dando erro aqui: " + this.data_solicitacao);
		gf.addField(sdf.format(this.data_solicitacao));
		return gf;
	}

	@Override
	public ModelField getViewFields() {
		final ModelField ff = new ModelField();
		// ff.addField("ID", id);
		ff.addField("cpf", this.cpf);
		ff.addField("solicitante", this.solicitante);
		ff.addField("data_solicitacao", this.data_solicitacao, ModelField.DATE);
		ff.addField("Produtos", this.produtos, ModelField.LISTBOX);
		return ff;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public List<? extends DefaultModel> getObj(final String campo) {
		if (campo.equals("Produtos")) {
			return Produto.service.findAll();
		}
		return null;
	}

	@Override
	public boolean delete(final Long id) {
		return service.delete(id);
	}

	@Override
	public List<Cotacao> filterGrid(final String filter) {

		final List<Cotacao> lista = service.findAll();

		if ("".equals(filter) || filter.equals("*")) {
			return lista;
		}

		final List<Cotacao> filtro = new ArrayList<Cotacao>();

		for (final Cotacao projeto : lista) {
			if (projeto.cpf.toUpperCase().startsWith(filter.toUpperCase())) {
				filtro.add(projeto);
			}
		}
		return filtro;

	}

}