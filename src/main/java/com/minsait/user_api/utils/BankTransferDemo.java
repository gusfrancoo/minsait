package com.minsait.user_api.utils;

import java.util.Random;

public class BankTransferDemo {
    volatile static boolean running = true;

    public static class Conta {
        private final int id;
        private double saldo;

        public Conta(int id, double saldoInicial) {
            this.id = id;
            this.saldo = saldoInicial;
        }

        public int getId() {
            return id;
        }

        public synchronized void depositar(double valor) {
            saldo += valor;
        }

        public synchronized void sacar(double valor) {
            saldo -= valor;
        }

        public synchronized double getSaldo() {
            return saldo;
        }
    }

    public static class Banco {
        private final Conta[] contas;

        public Banco(int numContas, double saldoInicial) {
            contas = new Conta[numContas];
            for (int i = 0; i < numContas; i++) {
                contas[i] = new Conta(i, saldoInicial);
            }
        }

        public void transferir(int de, int para, double valor) {

            Conta c1 = contas[de];
            Conta c2 = contas[para];

            Conta primeiro = c1.getId() < c2.getId() ? c1 : c2;
            Conta segundo = c1.getId() < c2.getId() ? c2 : c1;

            synchronized (primeiro) {
                synchronized (segundo) {
                    if (c1.getSaldo() >= valor) {
                        c1.sacar(valor);
                        c2.depositar(valor);
                        System.out.println("Transferido R$" + valor + " da Conta " + de + " para Conta " + para);
                    }
                }
            }
        }

        public double saldoTotal() {
            double total = 0;
            for (Conta c : contas) {
                total += c.getSaldo();
            }
            return total;
        }

        public int getNumeroDeContas() {
            return contas.length;
        }
    }

    public static void main(String[] args) {
        Banco banco = new Banco(5, 1000.0);
        Random rand = new Random();


        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                while (running) {
                    int de = rand.nextInt(banco.getNumeroDeContas());
                    int para = rand.nextInt(banco.getNumeroDeContas());
                    if (de == para) {
                        continue;
                    }
                    double valor = rand.nextInt(200);

                    banco.transferir(de, para, valor);

                    try {
                        Thread.sleep(rand.nextInt(100));
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            t.start();
        }
        System.out.println("Aquiuiiiii");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }

        running = false;
        System.out.println("Encerrando simulação. Saldo final total: " + banco.saldoTotal());
    }
}
