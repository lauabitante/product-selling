/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author lauraabitante
 */
import java.util.List;
import model.Produto;

public interface ProdutoDAO {
    public void salvar(Produto produto);
    public void deletar(Produto produto);
    public void deletarPorCodigo(Produto produto);
    public void atualizar(Produto produto);
    public List<Produto> listar();
    public Produto buscarPorCodigo(int codProduto);
    public List<Produto> listarProdutoPorNome(String produto);
}
