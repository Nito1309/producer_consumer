import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame{
    private JPanel mainPanel;
    private JTextArea txtArea;
    private JButton btnBorrarProductor;
    private JButton btnAgregarProductor;
    private JButton btnAgregarConsumidor;
    private JButton btnBorrarConsumidor;
    private JButton btnDetenerPrograma;
    private JButton bntIniciarPrograma;
    private final Almacen almacen;
    Controlador controlador;
    public App (String title){
        //initComponents();
        super(title);
        DefaultCaret caret = (DefaultCaret)txtArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        almacen = new Almacen(0);
        btnDetenerPrograma.setEnabled(false);
        btnAgregarConsumidor.setEnabled(false);
        btnAgregarProductor.setEnabled(false);
        btnBorrarConsumidor.setEnabled(false);
        btnBorrarProductor.setEnabled(false);

        btnAgregarProductor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.agregarPersona(true);
                if(!btnBorrarProductor.isEnabled())
                    btnBorrarProductor.setEnabled(true);
                if (controlador.limiteAlcanzado(true))
                    btnAgregarProductor.setEnabled(false);
            }
        });
        btnBorrarProductor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.eliminarPersona(true);
                if(!btnAgregarProductor.isEnabled())
                    btnAgregarProductor.setEnabled(true);
                if(controlador.limiteAlcanzado(true))
                    btnBorrarProductor.setEnabled(false);
            }
        });
        btnAgregarConsumidor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.agregarPersona(false);
                if(!btnBorrarConsumidor.isEnabled())
                    btnBorrarConsumidor.setEnabled(true);
                if (controlador.limiteAlcanzado(false))
                    btnAgregarConsumidor.setEnabled(false);
            }
        });
        btnBorrarConsumidor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.eliminarPersona(false);
                if(!btnAgregarConsumidor.isEnabled())
                    btnAgregarConsumidor.setEnabled(true);
                if (controlador.limiteAlcanzado(false))
                    btnBorrarConsumidor.setEnabled(false);
            }
        });
        bntIniciarPrograma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDetenerPrograma.setEnabled(true);
                bntIniciarPrograma.setEnabled(false);
                btnAgregarConsumidor.setEnabled(true);
                btnAgregarProductor.setEnabled(true);
                btnBorrarConsumidor.setEnabled(true);
                btnBorrarProductor.setEnabled(true);
                controlador = new Controlador(almacen, txtArea);

                Thread hiloControlador = new Thread(controlador);
                hiloControlador.setPriority(Thread.MAX_PRIORITY);
                hiloControlador.start();
            }
        });
        btnDetenerPrograma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDetenerPrograma.setEnabled(false);
                bntIniciarPrograma.setEnabled(true);
                btnAgregarConsumidor.setEnabled(false);
                btnAgregarProductor.setEnabled(false);
                btnBorrarConsumidor.setEnabled(false);
                btnBorrarProductor.setEnabled(false);
                try {
                    controlador.detenerTodo();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new App(" Productor - Consumidor ");
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

