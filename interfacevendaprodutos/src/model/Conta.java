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
public class Conta {

    private int numeroConta;
    private double saldoConta;

    /**
     * Construtor classe Conta
     * @param numeroConta numero da conta do cliente
     * @param saldoConta saldo da conta do cliente
     */
    public Conta(int numeroConta, double saldoConta) {
        this.numeroConta = numeroConta;
        this.saldoConta = saldoConta;
    }

    /**
     * Número da conta do cliente
     * @return Retorna o número da conta do cliente
     */
    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    /**
     * Saldo da conta do cliente
     * @return Retorna o saldo da conta do cliente
     */
    public double getSaldoConta() {
        return saldoConta;
    }
    
    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    /**
     * Realiza depósito na conta do cliente
     * @param valor valor que será depositado na conta do cliente
     */
    public void depositar(double valor) {
        this.saldoConta += valor;
    }
    
    /**
     * Realiza saque da conta do cliente
     * @param valor valor que será retirado da conta do cliente
     */
    public void retirar(double valor){
        this.saldoConta -= valor;
    }

    @Override
    public String toString() {
        return ""+numeroConta;
    }

}
