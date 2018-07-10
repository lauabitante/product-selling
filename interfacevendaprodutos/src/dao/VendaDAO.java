/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Venda;

/**
 *
 * @author lauraabitante
 */
public interface VendaDAO {
    public void salvar(Venda venda);
    public List<Venda> listar();
    public Venda buscarPorID(int id);
    public List<Venda> buscarPorIdCliente(int idCliente);
}
