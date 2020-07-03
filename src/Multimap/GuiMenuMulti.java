package Multimap;

import Classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.net.Socket;

// The import from the object

public class GuiMenuMulti {

    JFrame f;
    ArrayList<agente> arreglo_de_agentes;
    mapa configuracion_de_mapa;
    enfermedad configuracion_de_enfermedad = new enfermedad();
    Server servidores;
    JButton boton_agentes = new JButton("Cargar agentes");//creating instance of JButton
    JButton boton_mapa = new JButton("Cargar mapa");//creating instance of JButton
    JButton boton_enferdad = new JButton("Cargar enfermedad");//creating instance of JButton
    JButton boton_comenzar = new JButton("Comenzar");//creating instance of JButton
    JButton boton_enlinea = new JButton("En linea");//creating instance of JButton

    public GuiMenuMulti() {

        f = new JFrame("Simulacion de propagacion de COVID-19");//creating instance of JFrame
        JLabel label1 = new JLabel("Ingrese los archivos de configuracion.");

        boton_agentes.setEnabled(false);
        boton_enferdad.setEnabled(false);
        boton_comenzar.setEnabled(false);
        boton_enlinea.setEnabled(true);

        label1.setBounds(30, 0, 250, 40);
        boton_enferdad.setBounds(70, 200, 150, 40);
        boton_agentes.setBounds(70, 150, 150, 40);
        boton_mapa.setBounds(70, 100, 150, 40);
        boton_enlinea.setBounds(70, 50, 150, 40);
        boton_comenzar.setBounds(70, 250, 150, 40);

        boton_enlinea.addActionListener(new configuracion_red());
        boton_mapa.addActionListener(new configuracion_mapa());
        boton_agentes.addActionListener(new configuracion_agente());
        boton_enferdad.addActionListener(new configuracion_enfermedad());
        boton_comenzar.addActionListener(new comenzar_prueba());

        f.add(label1);
        f.add(boton_mapa);//adding button in JFrame
        f.add(boton_agentes);//adding button in JFrame
        f.add(boton_enferdad);//adding button in JFrame
        f.add(boton_comenzar);
        f.add(boton_enlinea);

        f.setSize(300, 400);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setLocationRelativeTo(null);
    }

    class configuracion_red implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            servidores = new Server();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File archivo = fileChooser.getSelectedFile();
            if (archivo != null) {

                try {
                    ArrayList<String> lista_de_ips;
                    ArrayList<Integer> lista_de_puertos;
                    ArrayList<Socket> lista_de_computadoras;
                    Scanner myReader = new Scanner(archivo);

                    //Aqui leo la primera linea donde vienen la cantidad de agentes.
                    String line1 = myReader.nextLine();
                    int x = Integer.parseInt(line1);

                    //Aqui recorro la cantidad de agentes que vienen en el documento.
                    for (int i=0; i<x; i++) {
                        String line2 = myReader.nextLine();
                        String[] datos = line2.split(" ");
                        servidores.addElementToListOfPorts(Integer.parseInt(datos[5]));
                        servidores.addElementToListOfIps(datos[0]);

                        if(servidores.available(datos[0],Integer.parseInt(datos[5]))){
                            configuracion_de_enfermedad.setDias_totales(Integer.parseInt(datos[1]));
                            servidores.setProbabilidad_de_visita((double) Float.parseFloat(datos[2]));
                            servidores.setTiempo_para_lanzar_probabilidad(Integer.parseInt(datos[3]));
                            servidores.setTiempo_de_agente_en_la_computadora(Integer.parseInt(datos[4]));
                            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(datos[5]));
                            servidores.setServerSocket(serverSocket);

                        }

                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err);
                }
            }
            System.out.println(servidores.toString());
        }
    }

    class configuracion_agente implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File archivo = fileChooser.getSelectedFile();
            if(archivo != null){
                ArrayList<agente> agentes = new ArrayList<agente>();
                try {

                    Scanner myReader = new Scanner(archivo);

                    //Aqui leo la primera linea donde vienen la cantidad de agentes.
                    String line1 = myReader.nextLine();
                    int x = Integer.parseInt(line1);

                    //Aqui recorro la cantidad de agentes que vienen en el documento.
                    for (int i=0; i<x; i++) {

                        int cantidad = Integer.parseInt(myReader.nextLine());

                        int tipo = Integer.parseInt(myReader.nextLine());
                        String velocidades = myReader.nextLine();
                        String[] velocidades2 = velocidades.split(" ");
                        String estado = myReader.nextLine();


                        //Aqui hago la lista de cada uno de los agentes segun su tipo especificado en el documento.
                        Random rand = new Random();

                        for (int y=0; y<cantidad; y++) {
                            agente agente = new agente();
                            agente.setVelocidad_maxima(Integer.parseInt(velocidades2[1]));
                            agente.setVelocidad_minima(Integer.parseInt(velocidades2[0]));
                            agente.setTipo(tipo);
                            agente.setEstado(estado);
                            agente.setTiempo_enfermo(0);
                            int velocidad_maxima = Integer.parseInt(velocidades2[1]);
                            int velocidad_minima = Integer.parseInt(velocidades2[0]);


                            if(velocidad_maxima == 0) agente.setVelocidad_x(0);
                            else agente.setVelocidad_x(velocidad_maxima+rand.nextInt(velocidad_maxima));
                            if(velocidad_minima == 0) agente.setVelocidad_y(0);
                            else agente.setVelocidad_y(velocidad_minima+rand.nextInt(velocidad_maxima));
                            agente.setPosicion_en_eje_x(rand.nextInt(configuracion_de_mapa.getAncho()));
                            agente.setPosicion_en_eje_y(rand.nextInt(configuracion_de_mapa.getLargo()));

                            agentes.add(agente);
                            boton_enferdad.setEnabled(true);
                            boton_agentes.setEnabled(false);
                        }
                    }
                    arreglo_de_agentes = agentes;
                } catch(Exception err) {
                    JOptionPane.showMessageDialog(null, err);
                }
            }
        }
    }

    class configuracion_mapa implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File archivo = fileChooser.getSelectedFile();
            if(archivo != null){
                mapa mapa = new mapa();
                try {

                    Scanner myReader = new Scanner(archivo);

                    //Aqui leo la primera linea donde vienen las dimenciones del mapa
                    String line1 = myReader.nextLine();
                    String[] dimenciones = line1.split(" ");
                    mapa.setAncho(Integer.parseInt(dimenciones[0]));
                    mapa.setLargo(Integer.parseInt(dimenciones[1]));

                    if(myReader.hasNextLine()) {

                        //Aqui leo la cantidad de paredes que tiene el mapa.
                        String line2 = myReader.nextLine();
                        int paredes = Integer.parseInt(line2);

                        while (paredes != 0) {

                            //Aqui leo y asigno las dimenciones de las paredes hasta que ya no hayan mas.
                            String linex = myReader.nextLine();
                            String[] dimenciones2 = linex.split(" ");
                            pared pared = new pared(Integer.parseInt(dimenciones2[0]), Integer.parseInt(dimenciones2[1]), Integer.parseInt(dimenciones2[2]), Integer.parseInt(dimenciones2[3]));
                            mapa.addPared(pared);
                            paredes -= 1;
                        }
                        configuracion_de_mapa = mapa;
                        boton_agentes.setEnabled(true);
                        boton_mapa.setEnabled(false);
                    }

                 } catch(Exception err) {
                     JOptionPane.showMessageDialog(null, err);
                 }
            }
        }
    }

    class configuracion_enfermedad implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File archivo = fileChooser.getSelectedFile();
            if(archivo != null){

                try{

                    Scanner myReader = new Scanner(archivo);

                    //Aqui leo la primera linea donde viene la probabilidad de muerte.
                    String line1 = myReader.nextLine();
                    configuracion_de_enfermedad.setProbabilidad_muerte(Float.parseFloat(line1));

                    //Aqui leo la segunda linea donde viene la cantidad de segundos para morir.
                    String line2 = myReader.nextLine();
                    configuracion_de_enfermedad.setDias_de_muerte(Integer.parseInt(line2));

                    //Aqui leo la tercera linea donde viene la cantidad de segundos para curarse.
                    String line3 = myReader.nextLine();
                    configuracion_de_enfermedad.setDias_de_recuperacion(Integer.parseInt(line3));

                    ArrayList<ArrayList<Float>> matriz = new ArrayList<ArrayList<Float>>();

                    for (int x=0; x<4; x++) {

                        //Aqui leo y asigno las probabilidades de contagio segun cada individuo.
                        String linex = myReader.nextLine();
                        String[] probabilidadesx = linex.split(" ");
                        ArrayList<Float> arrayList = new ArrayList<Float>();

                        arrayList.add(Float.parseFloat(probabilidadesx[0]));
                        arrayList.add(Float.parseFloat(probabilidadesx[1]));
                        arrayList.add(Float.parseFloat(probabilidadesx[2]));
                        arrayList.add(Float.parseFloat(probabilidadesx[3]));
                        matriz.add(arrayList);
                    }
                    configuracion_de_enfermedad.setMatriz_de_cotagio(matriz);

                    //Aqui leo la ultima linea donde indica si hay posibilidad de reinfeccion.
                    String lastLine = myReader.nextLine();
                    configuracion_de_enfermedad.setReinfeccion(Integer.parseInt(lastLine));

                    //Esta linea lee la cantidad de dias que el programa va a simular.
                    String lastLine2 = myReader.nextLine();

                    configuracion_de_enfermedad.setDias_corriendo(0);
                    configuracion_de_enfermedad.setCantidad_enfermos_actuales(0);
                    configuracion_de_enfermedad.setCantidad_recuperados_actuales(0);
                    configuracion_de_enfermedad.setCantidad_sanos_actuales(0);

                    boton_enferdad.setEnabled(false);
                    boton_comenzar.setEnabled(true);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err);
                }
            }
        }
    }

    class comenzar_prueba implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                new MainGuiFrameMulti(arreglo_de_agentes, configuracion_de_mapa, configuracion_de_enfermedad, arreglo_de_agentes.size(), servidores);
        }
    }
}