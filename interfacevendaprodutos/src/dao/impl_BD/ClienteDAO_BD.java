/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl_BD;

import dao.ClienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Conta;

/**
 *
 * @author lauraabitante
 */
public class ClienteDAO_BD implements ClienteDAO {
    
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
    public void salvar(Cliente cliente) {
        int id;
        try {
            String sql = "INSERT INTO cliente (nome, cpf, email, conta, saldo, ativo)"
                    + "VALUES (?,?,?,?,?,true)";

            conectarObtendoId(sql);
            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getCpf());
            comando.setString(3, cliente.getEmail());
            comando.setInt(4, cliente.getConta().getNumeroConta());
            comando.setDouble(5, cliente.getConta().getSaldoConta());
            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                id = resultado.getInt(1);
                cliente.setId(id);
            }
            else {
                System.err.println("Erro de Sistema - Não gerou o id conforme esperado.");
                throw new BDException("Não gerou o id conforme esperado.");
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar o cliente no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Cliente cliente) {
         try {
            //Realizando update para que o cliente não seja excluído do banco.
            String sql = "UPDATE cliente set ativo=false WHERE id=?";
            conectar(sql);
            comando.setInt(1, cliente.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar o cliente no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
    @Override
    public void deletarDadosDeTeste(String cpf) {
         try {
            String sql = "DELETE from cliente WHERE cpf=?";
            conectar(sql);
            comando.setString(1, cpf);
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar o cliente no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
         try {
            String sql = "UPDATE cliente SET nome=?, cpf=?, email=?, conta=?, saldo=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getCpf());
            comando.setString(3, cliente.getEmail());
            comando.setInt(4, cliente.getConta().getNumeroConta());
            comando.setDouble(5, cliente.getConta().getSaldoConta());
            comando.setInt(6, cliente.getId());
            comando.executeUpdate();
            System.out.println("\nCliente atualizado com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar cliente no Banco de Dados.");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Cliente> listar(boolean ativos) {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY id ASC";
        if(ativos) {
            sql = "SELECT * FROM cliente WHERE ativo=true ORDER BY id ASC";
        }
        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                int numeroConta = resultado.getInt("conta");
                double saldo = resultado.getDouble("saldo");
                Conta conta = new Conta(numeroConta, saldo);
                Cliente c = new Cliente(id, nome, email, cpf, conta);
                listaClientes.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os clientes no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaClientes);
    }

    @Override
    public Cliente buscarPorID(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                int numeroConta = resultado.getInt("conta");
                double saldo = resultado.getDouble("saldo");
                Conta conta = new Conta(numeroConta, saldo);
                Cliente c = new Cliente(id, nome, email, cpf, conta);
                return c;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o cliente pelo id no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (null);
    }

    @Override
    public Cliente buscaPorCPF(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf like ?";

        try {
            conectar(sql);
            comando.setString(1, cpf);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String email = resultado.getString("email");
                int numeroConta = resultado.getInt("conta");
                double saldo = resultado.getDouble("saldo");
                Conta conta = new Conta(numeroConta, saldo);
                Cliente c = new Cliente(id, nome, email, cpf, conta);
                return c;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o cliente pelo cpf no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (null);
    }

}
