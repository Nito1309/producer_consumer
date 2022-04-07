import java.util.List;

public class Sedador implements Runnable {
    public boolean  dormido;

    public Sedador(Almacen almacen,int productoresDespiertos,
                   int consumidoresDespiertos, List<Productor> productores,
                   List<Consumidor> consumidores) {

        this.almacen = almacen;
        this.productoresDespiertos = productoresDespiertos;
        this.consumidoresDespiertos = consumidoresDespiertos;
        this.productores = productores;
        this.consumidores = consumidores;
    }

    int productoresDespiertos, consumidoresDespiertos;
    List<Productor> productores ;
    List<Consumidor> consumidores;
    Almacen almacen;


    @Override
    public synchronized void run() {
        while (!dormido){
            if (productoresDespiertos>consumidoresDespiertos){
                for(int i = productoresDespiertos-1; !(consumidoresDespiertos == productoresDespiertos); i-- ){
                        productores.get(i).aMimir();
                        productoresDespiertos--;
                    }

            }
            if(consumidoresDespiertos>productoresDespiertos){
                for(int j = consumidoresDespiertos-1; !(productoresDespiertos == consumidoresDespiertos ); j-- ){
                    consumidoresDespiertos--;
                    consumidores.get(j).aMimir();
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
