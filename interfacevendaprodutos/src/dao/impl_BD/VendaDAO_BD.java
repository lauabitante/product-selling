/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl_BD;

import dao.VendaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Cliente;
import model.Conta;
import model.Produto;
import model.Venda;

/**
 *
 * @author lauraabitante
 */
public class VendaDAO_BD implements VendaDAO {
    
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
    public void salvar(Venda venda) {
        int id;
        try {
            String sql = "INSERT INTO venda (datahora, idcliente)" + "VALUES (?,?)";            
            conectarObtendoId(sql);
            
            java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
            
            comando.setDate(1, sqlDate);
            comando.setInt(2, venda.getCliente().getId());
            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                id = resultado.getInt(1);
                venda.setId(id);
                
                for(int i = 0; i< venda.getProdutos().size() ; i++) {
                    Produto p = venda.getProdutos().get(i);
                    String query = "INSERT INTO itemvenda (idvenda, idproduto)"
                    + "VALUES (?,?)";

                    conectarObtendoId(query);
                    comando.setInt(1, venda.getId());
                    comando.setInt(2, p.getId());
                    comando.executeUpdate();
                }
                
            }
            else {
                System.err.println("Erro de Sistema - Não gerou o id conforme esperado.");
                throw new BDException("Não gerou o id conforme esperado.");
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar a venda no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Venda> listar() {
        List<Venda> listaVendas = new ArrayList<>();
        
        String sql = "SELECT venda.id, venda.datahora, venda.idcliente, "
                + "cliente.nome, cliente.cpf, cliente.email, cliente.conta, cliente.saldo "
                + "FROM venda INNER JOIN cliente "
                + "ON venda.idCliente = cliente.id";

        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                int idCliente = resultado.getInt("idcliente");
                Date data = resultado.getDate("datahora");
                LocalDate dataHora = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(data));
                
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                int numeroConta = resultado.getInt("conta");
                double saldo = resultado.getDouble("saldo");
                Conta conta = new Conta(numeroConta, saldo);
                Cliente c = new Cliente(idCliente, nome, email, cpf, conta);
                
                Venda venda = new Venda(id, dataHora, c);
                
                String produtosSQL = "SELECT produto.id, produto.codigo, produto.nome, produto.preco FROM produto INNER JOIN itemvenda ON produto.id = itemvenda.idproduto WHERE itemvenda.idvenda = " + id;
                conectar(produtosSQL);
                ResultSet produtosResult = comando.executeQuery();
                
                while (produtosResult.next()) {
                    int idProduto = produtosResult.getInt("id");
                    int codigo = produtosResult.getInt("codigo");
                    String nomeProduto = produtosResult.getString("nome");
                    double preco = produtosResult.getDouble("preco");
                    Produto p = new Produto(idProduto, codigo, nomeProduto, preco);
                    venda.getProdutos().add(p);
                }
                listaVendas.add(venda);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVendas);
    }

    @Override
    public Venda buscarPorID(int id) {
        Venda venda = null;
        String sql = "SELECT venda.id, venda.datahora, venda.idcliente, "
                + "cliente.nome, cliente.cpf, cliente.email, cliente.conta, cliente.saldo "
                + "FROM venda INNER JOIN cliente "
                + "ON venda.idcliente = cliente.id WHERE venda.id = " + id;

        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int idCliente = resultado.getInt("idcliente");
                Date data = resultado.getDate("datahora");
                LocalDate dataHora = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(data));
                
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                int numeroConta = resultado.getInt("conta");
                double saldo = resultado.getDouble("saldo");
                Conta conta = new Conta(numeroConta, saldo);
                Cliente c = new Cliente(idCliente, nome, email, cpf, conta);
                
                Venda v = new Venda(id, dataHora, c);
                
                String produtosSQL = "SELECT produto.id, produto.codigo, produto.nome, produto.preco FROM produto INNER JOIN itemvenda ON produto.id = itemvenda.idproduto WHERE itemvenda.idvenda = " + id;
                conectar(produtosSQL);
                ResultSet produtosResult = comando.executeQuery();
                
                while (produtosResult.next()) {
                    int idProduto = produtosResult.getInt("id");
                    int codigo = produtosResult.getInt("codigo");
                    String nomeProduto = produtosResult.getString("nome");
                    double preco = produtosResult.getDouble("preco");
                    Produto p = new Produto(idProduto, codigo, nomeProduto, preco);
                    v.getProdutos().add(p);
                }
                
                venda = v;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return venda;
    }

    @Override
    public List<Venda> buscarPorIdCliente(int idCliente) {
        List<Venda> listaVendas = new ArrayList<>();
        
        String sql = "SELECT venda.id, venda.datahora, venda.idcliente, "
                + "cliente.nome, cliente.cpf, cliente.email, cliente.conta, cliente.saldo "
                + "FROM venda INNER JOIN cliente "
                + "ON venda.idcliente = cliente.id "
                + "WHERE venda.idcliente = " + idCliente;

        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                Date data = resultado.getDate("datahora");
                LocalDate dataHora = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(data));
                
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                int numeroConta = resultado.getInt("conta");
                double saldo = resultado.getDouble("saldo");
                Conta conta = new Conta(numeroConta, saldo);
                Cliente c = new Cliente(idCliente, nome, email, cpf, conta);
                
                Venda venda = new Venda(id, dataHora, c);
                
                String produtosSQL = "SELECT produto.id, produto.codigo, produto.nome, produto.preco FROM produto INNER JOIN itemvenda ON produto.id = itemvenda.idproduto WHERE itemvenda.idvenda = " + id;
                conectar(produtosSQL);
                ResultSet produtosResult = comando.executeQuery();
                
                while (produtosResult.next()) {
                    int idProduto = produtosResult.getInt("id");
                    int codigo = produtosResult.getInt("codigo");
                    String nomeProduto = produtosResult.getString("nome");
                    double preco = produtosResult.getDouble("preco");
                    Produto p = new Produto(idProduto, codigo, nomeProduto, preco);
                    venda.getProdutos().add(p);
                }
                listaVendas.add(venda);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVendas);
    }
    
}
