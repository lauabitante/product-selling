/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lauraabitante
 */
public class Cliente {

    private int id;

    private String nome;
    private String email;
    private String cpf;
    private Conta conta;

    /**
     * Construtor classe Cliente
     * @param nome nome do cliente
     * @param email email do cliente
     * @param cpf cpf do cliente
     * @param conta conta do cliente
     */
    public Cliente(String nome, String email, String cpf, Conta conta) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.conta = conta;
    }
    
     public Cliente(int id, String nome, String email, String cpf, Conta conta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.conta = conta;
    }
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Nome do cliente
     * @return Retorna o nome do cliente
     */
    public String getNome() {
        return nome;
    }
    
       public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Email do cliente
     * @return Retorna o email do cliente
     */
    public String getEmail() {
        return email;
    }
    
     public void setEmail(String email) {
        this.email = email;
    }

    /**
     * CPF do cliente
     * @return Retorna o CPF do cliente
     */
    public String getCpf() {
        return cpf;
    }
    
     public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Conta do cliente
     * @return Retorna a conta do cliente
     */
    public Conta getConta() {
        return conta;
    }
    
    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    /**
     * Compara clientes
     * @param c cliente a ser comparado
     * @return Retorna true caso os CPFs sejam iguais
     */
    public boolean equals(Cliente c){  
        return this.getCpf().equals(c.getCpf());
    }

    @Override
    public String toString() {
        return "|COD: " + id + "\t|\tNOME: " + nome + "\t|\tEMAIL: " + email + "\t|\tCPF: " + cpf + "\t|\tCONTA: " + conta;
    }

}
