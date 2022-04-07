import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controlador implements Runnable {
    Almacen almacen;
    List <Consumidor> consumidores;
    List <Productor> productores;
    List <Sedador> sedadores;
    List <Despertador> despertadores;
    int productoresDespiertos, consumidoresDespiertos, sedadoresTrabajando,
    despertadoresTrabajando;
    boolean dormir ;
    JTextArea jTextArea;


    public Controlador(Almacen almacen, JTextArea jTextArea) {
        productoresDespiertos=0;
        consumidoresDespiertos=0;
        sedadoresTrabajando=0;
        despertadoresTrabajando=0;
        this.jTextArea = jTextArea;
        consumidores = new ArrayList<>();
        productores = new ArrayList<>();
        sedadores = new ArrayList<>();
        despertadores = new ArrayList<>();
        this.almacen = almacen;
        dormir = false;
        agregarPersona(true);
        agregarPersona(false);
        nuevoSedador();
        nuevoDespertador();
    }

    public boolean limiteAlcanzado(boolean deProductores){
        if (deProductores){
            return productores.size() == 10 || productores.isEmpty();
        }else{
            return consumidores.size() == 10 || consumidores.isEmpty();
        }
    }

    public void agregarPersona(boolean productor){
        Thread hiloNuevaPersona;
        if(productor){
            Productor nuevaPersona = new Productor(productores.size()+1,jTextArea,almacen);
            hiloNuevaPersona = new Thread(nuevaPersona);
            productores.add(nuevaPersona);
            productoresDespiertos++;
        }else {
            Consumidor nuevaPersona = new Consumidor(consumidores.size()+1,jTextArea,almacen);
            hiloNuevaPersona = new Thread(nuevaPersona);
            consumidores.add(nuevaPersona);
            consumidoresDespiertos++;
        }
        hiloNuevaPersona.setPriority(Thread.MIN_PRIORITY);
        hiloNuevaPersona.start();
    }

    public void eliminarPersona(boolean esProductor){
        if(esProductor){
            Productor ultimoProductor = productores.get(productores.size()-1);
            ultimoProductor.aMimir();
            productores.remove(ultimoProductor);
            ultimoProductor.imprimir(jTextArea,"El productor "+ (ultimoProductor.num) + " ha sido despedido :(");
            productoresDespiertos--;

        }else{
            Consumidor ultimoConsumidor = consumidores.get(consumidores.size()-1);
            ultimoConsumidor.aMimir();
            consumidores.remove(ultimoConsumidor);
            consumidoresDespiertos--;
            ultimoConsumidor.imprimir(jTextArea, "El consumidor "+ultimoConsumidor.num + " se ha ido");
        }

    }

    public void detenerTodo() throws InterruptedException {
        for (Sedador sedador: sedadores){
            sedador.dormido=true;
        }
        for (Despertador despertador: despertadores
        ) {
            despertador.dormido = true;
        }
        for (Consumidor consumidor: consumidores
             ) {
            consumidor.aMimir();
        }

        for (Productor productor: productores
             ) {
            productor.aMimir();
        }



        this.dormir = true;
    }

    public void nuevoSedador(){
        Sedador sedador = new Sedador(almacen,productoresDespiertos, consumidoresDespiertos,
                                        productores, consumidores);
        Thread hiloSedador = new Thread(sedador);
        hiloSedador.setPriority(Thread.NORM_PRIORITY);
        hiloSedador.start();
        sedadores.add(sedador);
        sedadoresTrabajando++;
    }

    public void nuevoDespertador(){
        Despertador despertador = new Despertador(almacen, productoresDespiertos,
                consumidoresDespiertos, productores, consumidores);
        Thread hiloDespertador = new Thread(despertador);
        hiloDespertador.setPriority(Thread.NORM_PRIORITY);
        hiloDespertador.start();
        despertadores.add(despertador);
        despertadoresTrabajando++;
    }

    @Override
    public synchronized void run() {
        while(!dormir){

            if(almacen.productos<=0 && consumidoresDespiertos>productoresDespiertos){
                if(consumidoresDespiertos>sedadoresTrabajando){
                    nuevoDespertador();
                    nuevoSedador();
                }
            }
            if (almacen.productos >= 10 && productoresDespiertos>consumidoresDespiertos){
                    if(productoresDespiertos>sedadoresTrabajando){
                        nuevoDespertador();
                        nuevoSedador();
                    }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
