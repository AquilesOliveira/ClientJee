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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
@XMLCast(thisClassFrom = "models.Usuario")
public class Usuario implements DefaultModel<Usuario> {

	@GridHeader(name = "ID", size = 10)
	public Long id;
	@GridHeader(name = "Nome", size = 200)
	public String nome;
	@GridHeader(name = "Email", size = 100)
	public String email;

	public static Service<Usuario> service = new Service<Usuario>(new ApacheRequest("http://localhost:8080/api/usuarios"), Usuario.class,
			new TypeToken<List<Usuario>>() {
			}.getType());

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public List<Usuario> findStart() {
		// return service.findAll();
		return new LinkedList<Usuario>();
	}

	@Override
	public List<Usuario> filterGrid(final String filter) {

		final List<Usuario> lista = service.findAll();

		if ("".equals(filter) || filter.equals("*")) {
			return lista;
		}

		final List<Usuario> filtro = new ArrayList<Usuario>();
		for (final Usuario user : lista) {
			if (user.nome.toUpperCase().startsWith(filter.toUpperCase())) {
				filtro.add(user);
			}
		}
		return filtro;

	}

	@Override
	public Usuario findById(final Long id) {
		return service.findById(id);
	}

	@Override
	public Usuario save(final Long id, final HashMap<String, Object> map) throws ValidationException {
		final HashMap<String, String> vo = new HashMap<String, String>();
		vo.put("usuario.nome", map.get("Nome").toString());
		vo.put("usuario.email", map.get("Email").toString());
		return service.save(id, vo);
	}

	@Override
	public boolean delete(final Long id) {
		return service.delete(id);
	}

	@Override
	public ModelField getGridFields() {
		final ModelField gf = new ModelField();
		gf.addField(this.id);
		gf.addField(this.nome);
		gf.addField(this.email);
		return gf;
	}

	@Override
	public ModelField getViewFields() {
		final ModelField ff = new ModelField();
		ff.addField("Nome", this.nome);
		ff.addField("Email", this.email);
		return ff;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public List<? extends DefaultModel> getObj(final String campo) {
		return null;
	}
}
