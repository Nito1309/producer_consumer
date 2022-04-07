import java.util.Iterator;
import java.util.List;

public class Despertador extends Sedador implements Runnable{

    public Despertador(Almacen almacen, int productoresDespiertos, int consumidoresDespiertos, List<Productor> productores, List<Consumidor> consumidores) {
        super(almacen, productoresDespiertos, consumidoresDespiertos, productores, consumidores);
    }

    @Override
    public synchronized void run() {
        while (!dormido){
            if (productoresDespiertos>consumidoresDespiertos){
                Iterator<Consumidor> itr = consumidores.iterator();
                while (itr.hasNext()){
                    Consumidor consumidor = itr.next();
                    consumidor.aChambear();
                    consumidoresDespiertos++;
                }
            }
            if(consumidoresDespiertos>productoresDespiertos){
                Iterator <Productor> itr = productores.iterator();
                while (itr.hasNext()){
                    Productor productor = itr.next();
                    if (productor.dormido){
                        productor.aChambear();
                        productoresDespiertos++;
                    }
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

