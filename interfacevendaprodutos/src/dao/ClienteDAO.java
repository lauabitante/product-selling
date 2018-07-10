/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Cliente;

/**
 *
 * @author lauraabitante
 */
public interface ClienteDAO {
    public void salvar(Cliente cliente);
    public void deletar(Cliente cliente);
    public void deletarDadosDeTeste(String cpf);
    public void atualizar(Cliente cliente);
    public List<Cliente> listar(boolean clienteAtivo);
    public Cliente buscarPorID(int id);
    public Cliente buscaPorCPF(String cpf);
}
