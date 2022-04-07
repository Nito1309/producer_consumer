import javax.swing.*;

public class Consumidor extends Personaje implements Runnable{
    Consumidor(int num, JTextArea textArea, Almacen almacen){
        dormido=false;
        this.num = num;
        this.textArea = textArea;
        this.almacen = almacen;
    }

    @Override
    public synchronized void run() {
        while (!dormido){
            if(almacen.productos != 0){
                almacen.productos--;
                imprimir(textArea,"El consumidor "+ (num) + " ha consumido el producto: "  +
                        (almacen.productos+1));

            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void aMimir(){
        if(!dormido){
            this.dormido=true;
            imprimir(textArea,"El consumidor: "+ (num) + " se fue a mimir");
        }
    }

    void aChambear(){
        if(dormido){
            this.dormido=false;
            imprimir(textArea,"El consumidor: "+ (num) + " ha despertado");
            this.run();
        }
    }

    @Override
    void run(Almacen almacen) throws InterruptedException {

    }
}
