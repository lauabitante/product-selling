/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl_BD;

import dao.ProdutoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

/**
 *
 * @author lauraabitante
 */
public class ProdutoDAO_BD implements ProdutoDAO {
    private Connection conexao;
    private PreparedStatement comando;

    public Connection conectar(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
        return conexao;
    }

    public void conectarObtendoId(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public void fecharConexao() {
        try {
            if (comando != null) {
                comando.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Erro ao encerrar a conexão.");
            throw new BDException(ex);
        }
    }  

    @Override
    public void salvar(Produto produto) {
        int id;
        try {
            String sql = "INSERT INTO produto (codigo, nome, preco)"
                    + "VALUES (?,?,?)";

            conectarObtendoId(sql);
            comando.setInt(1, produto.getCodigo());
            comando.setString(2, produto.getNome());
            comando.setDouble(3, produto.getPreco());
            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                id = resultado.getInt(1);
                produto.setId(id);
            }
            else {
                System.err.println("Erro de Sistema - Não gerou o id conforme esperado.");
                throw new BDException("Não gerou o id conforme esperado.");
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar o produto no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Produto produto) {
        try {
            String sql = "DELETE FROM produto WHERE id = ?";
            conectar(sql);
            comando.setInt(1, produto.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar o produto no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
     @Override
    public void deletarPorCodigo(Produto produto) {
        try {
            String sql = "DELETE FROM produto WHERE codigo = ?";
            conectar(sql);
            comando.setInt(1, produto.getCodigo());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar o produto no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Produto produto) {
        try {
            String sql = "UPDATE produto SET codigo=?, nome=?, preco=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setInt(1, produto.getCodigo());
            comando.setString(2, produto.getNome());
            comando.setDouble(3, produto.getPreco());
            comando.setInt(4, produto.getId());
            comando.executeUpdate();
            System.out.println("\nProduto atualizado com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar produto no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Produto> listar() {
        List<Produto> listaProdutos = new ArrayList<>();
        
        String sql = "SELECT * FROM produto";

        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                int codigo = resultado.getInt("codigo");
                String nome = resultado.getString("nome");
                double preco = resultado.getDouble("preco");
                Produto p = new Produto(id, codigo, nome, preco);
                listaProdutos.add(p);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaProdutos);
    }

//    @Override
//    public Produto buscarPorID(int id) {
//        String sql = "SELECT * FROM produto WHERE id = ?";
//
//        try {
//            conectar(sql);
//            comando.setInt(1, id);
//            ResultSet resultado = comando.executeQuery();
//
//            if (resultado.next()) {
//                int codigo = resultado.getInt("codigo");
//                String nome = resultado.getString("nome");
//                double preco = resultado.getDouble("preco");
//                Produto p = new Produto(id, codigo, nome, preco);
//                return p;
//            }
//
//        } catch (SQLException ex) {
//            System.err.println("Erro de Sistema - Problema ao buscar o produto pelo id do Banco de Dados!");
//            throw new BDException(ex);
//        } finally {
//            fecharConexao();
//        }
//
//        return (null);
//    }

    @Override
    public Produto buscarPorCodigo(int codProduto) {
        String sql = "SELECT * FROM produto WHERE codigo = ?";

        try {
            conectar(sql);
            comando.setInt(1, codProduto);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                double preco = resultado.getDouble("preco");
                Produto p = new Produto(id, codProduto, nome, preco);
                return p;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o produto pelo código no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (null);
    }

    @Override
    public List<Produto> listarProdutoPorNome(String produto) {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE nome LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + produto + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                int codigo = resultado.getInt("codigo");
                String nome = resultado.getString("nome");
                double preco = resultado.getDouble("preco");
                Produto p = new Produto(id, codigo, nome, preco);
                listaProdutos.add(p);
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos pelo nome no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaProdutos);
    }

}


