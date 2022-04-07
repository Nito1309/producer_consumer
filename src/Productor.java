import javax.swing.*;

public class Productor extends Personaje implements Runnable{
    Productor(int num, JTextArea textArea, Almacen almacen){
        dormido=false;
        this.num = num;
        this.textArea = textArea;
        this.almacen = almacen;
    }

    @Override
    public synchronized void run() {
        while (!dormido) {

            if (almacen.productos != 10){
                almacen.productos++;
                imprimir(textArea, "El productor " + (num) + " ha creado el producto: " +
                        almacen.productos);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void aMimir(){
        if (!dormido){
            this.dormido=true;
            imprimir(textArea,"El productor: "+ (num) + " se fue a mimir");
        }
    }

    void aChambear(){
        if(dormido){
            this.dormido=false;
            imprimir(textArea,"El productor: "+ (num) + " ha despertado");
            this.run();
        }

    }

    @Override
    void run(Almacen almacen) throws InterruptedException {

    }
}
