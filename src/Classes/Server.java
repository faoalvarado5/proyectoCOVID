package Classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ArrayList<String> lista_de_ips;
    ArrayList<Integer> lista_de_puertos;
    ArrayList<Double> probabilidad_de_visita;
    int tiempo_para_lanzar_probabilidad;
    int tiempo_de_agente_en_la_computadora;
    ServerSocket serverSocket;   // Recibe
                                 // Input


    public Server(){
        this.lista_de_ips = new ArrayList<>();
        this.lista_de_puertos = new ArrayList<>();
        this.probabilidad_de_visita = new ArrayList<>();
        this.tiempo_de_agente_en_la_computadora = 0;
        this.tiempo_para_lanzar_probabilidad = 0;
        this.serverSocket = null;

    }

    public void addElementToListOfProb(Double a){
        probabilidad_de_visita.add(a);
    }
    public void addElementToListOfIps(String a){
        lista_de_ips.add(a);
    }
    public void addElementToListOfPorts(Integer a){
        lista_de_puertos.add(a);
    }
    public boolean available(String ip, int port) {

        if(serverSocket == null) {
            try {
                Socket ignored = new Socket(ip, port);
                return false;
            } catch (IOException ignored) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getLista_de_ips() {
        return lista_de_ips;
    }

    public void setLista_de_ips(ArrayList<String> lista_de_ips) {
        this.lista_de_ips = lista_de_ips;
    }

    public ArrayList<Integer> getLista_de_puertos() {
        return lista_de_puertos;
    }

    public void setLista_de_puertos(ArrayList<Integer> lista_de_puertos) {
        this.lista_de_puertos = lista_de_puertos;
    }

    public ArrayList<Double> getProbabilidad_de_visita() {
        return probabilidad_de_visita;
    }

    public void setProbabilidad_de_visita(ArrayList<Double> probabilidad_de_visita) {
        this.probabilidad_de_visita = probabilidad_de_visita;
    }

    public int getTiempo_para_lanzar_probabilidad() {
        return tiempo_para_lanzar_probabilidad;
    }

    public void setTiempo_para_lanzar_probabilidad(int tiempo_para_lanzar_probabilidad) {
        this.tiempo_para_lanzar_probabilidad = tiempo_para_lanzar_probabilidad;
    }

    public int getTiempo_de_agente_en_la_computadora() {
        return tiempo_de_agente_en_la_computadora;
    }

    public void setTiempo_de_agente_en_la_computadora(int tiempo_de_agente_en_la_computadora) {
        this.tiempo_de_agente_en_la_computadora = tiempo_de_agente_en_la_computadora;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public String toString() {
        return "Server{" +
                "lista_de_ips=" + lista_de_ips +
                ", lista_de_puertos=" + lista_de_puertos +
                ", probabilidad_de_visita=" + probabilidad_de_visita +
                ", tiempo_para_lanzar_probabilidad=" + tiempo_para_lanzar_probabilidad +
                ", tiempo_de_agente_en_la_computadora=" + tiempo_de_agente_en_la_computadora +
                ", serverSocket=" + serverSocket +
                '}';
    }
}
