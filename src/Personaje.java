import javax.swing.*;
import java.awt.*;

public abstract class Personaje {
    protected boolean dormido;
    int num;
    JTextArea textArea;
    Almacen almacen;
    public void revisarProductos(int cantidadProdutos){
        if (cantidadProdutos != 3){
            if (dormido)
                dormido=false;
        }else{
            if (!dormido)
                dormido=true;
        }
    }

    public void imprimir(JTextArea textArea, String mensaje){
        textArea.append(mensaje+"\n");
    }


    //hacerlo asincrono
    abstract void run(Almacen almacen) throws InterruptedException;
}
