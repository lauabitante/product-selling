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
public class Produto {

    private int id, codigo;
    private String nome;
    private double preco;

      /**
     * Construtor da classe Produto
     * @param codigo código do produto
     * @param nome nome do produto
     * @param preco preço do produto
     */
    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
    
    /**
     *
     * @param id
     * @param codigo
     * @param nome
     * @param preco
     */
    public Produto(int id, int codigo, String nome, double preco) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
    
    /**
     * ID do produto
     * @return Retorna o id de um produto
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Código do produto
     * @return Retorna o código de um produto
     */
    public int getCodigo() {
        return codigo;
    }
    
    /**
     *
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Nome do produto
     * @return Retorna o nome de um produto
     */
    public String getNome() {
        return nome;
    }
    
    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Preço do produto
     * @return Retorna o preço de um produto
     */
    public double getPreco() {
        return preco;
    }
    
    /**
     *
     * @param preco
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    /**
     * Compara produtos
     * @param p produto a ser comparado
     * @return Retorna true caso os códigos dos produtos sejam iguais
     */
    
    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        final Produto other = (Produto) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "|\tNOME: " + nome + "\t|\tPREÇO: R$" + preco;
    }

}
