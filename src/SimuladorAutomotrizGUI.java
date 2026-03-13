import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

interface ModoConduccion {
    String activar();
}

class ModoEco implements ModoConduccion {
    public String activar() {
        return "Activando modo economico: ajustando inyeccion y reduciendo consumo de combustible.\n";
    }
}

class ModoDeportivo implements ModoConduccion {
    public String activar() {
        return "Activando modo deportivo: maximizando respuesta del motor y endureciendo direccion.\n";
    }
}

class SistemaTraccionAntiguo {
    public String aplicarLogicaAntigua() {
        return "Activando logica heredada de control de traccion mecanico de modelos anteriores.\n";
    }
}

class AdaptadorTraccion implements ModoConduccion {
    private SistemaTraccionAntiguo sistemaAntiguo;

    public AdaptadorTraccion(SistemaTraccionAntiguo sistemaAntiguo) {
        this.sistemaAntiguo = sistemaAntiguo;
    }

    public String activar() {
        return "Adaptando modulo antiguo al nuevo estandar de modos...\n" + this.sistemaAntiguo.aplicarLogicaAntigua();
    }
}

abstract class Vehiculo {
    protected ModoConduccion modo;

    public Vehiculo(ModoConduccion modo) {
        this.modo = modo;
    }

    public abstract String configurarModo();
}

class Renault extends Vehiculo {
    public Renault(ModoConduccion modo) {
        super(modo);
    }

    public String configurarModo() {
        return "Configurando perfil aerodinamico y motor del Renault Arkana.\n" + modo.activar();
    }
}

class Bmw extends Vehiculo {
    public Bmw(ModoConduccion modo) {
        super(modo);
    }

    public String configurarModo() {
        return "Configurando perfil aerodinamico y motor del BMW Serie M.\n" + modo.activar();
    }
}

class Mercedes extends Vehiculo {
    public Mercedes(ModoConduccion modo) {
        super(modo);
    }

    public String configurarModo() {
        return "Configurando perfil aerodinamico y motor del Mercedes-Benz Clase AMG.\n" + modo.activar();
    }
}

public class SimuladorAutomotrizGUI extends JFrame {
    private JComboBox<String> selectorVehiculo;
    private JComboBox<String> selectorModo;
    private JTextArea areaSalida;

    public SimuladorAutomotrizGUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        setTitle("Centro de Control Automotriz - Sede Pasto");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 244, 248));

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 15, 15));
        panelSuperior.setOpaque(false);

        JLabel etiquetaVehiculo = new JLabel("Seleccione el Vehículo:");
        etiquetaVehiculo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String[] vehiculos = {"Renault Arkana", "BMW Serie M", "Mercedes-Benz Clase AMG"};
        selectorVehiculo = new JComboBox<>(vehiculos);
        selectorVehiculo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel etiquetaModo = new JLabel("Seleccione el Modo de Conducción:");
        etiquetaModo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String[] modos = {"Modo Ecológico", "Modo Deportivo", "Tracción Clásica (Adaptada)"};
        selectorModo = new JComboBox<>(modos);
        selectorModo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelSuperior.add(etiquetaVehiculo);
        panelSuperior.add(selectorVehiculo);
        panelSuperior.add(etiquetaModo);
        panelSuperior.add(selectorModo);

        JButton botonAplicar = new JButton("Aplicar Configuración");
        botonAplicar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonAplicar.setBackground(new Color(41, 128, 185));
        botonAplicar.setForeground(Color.WHITE);
        botonAplicar.setFocusPainted(false);
        botonAplicar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(botonAplicar);

        JPanel panelControl = new JPanel(new BorderLayout());
        panelControl.setOpaque(false);
        panelControl.add(panelSuperior, BorderLayout.CENTER);
        panelControl.add(panelBoton, BorderLayout.SOUTH);

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaSalida.setBackground(new Color(30, 30, 30));
        areaSalida.setForeground(new Color(0, 255, 0));
        areaSalida.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane panelScroll = new JScrollPane(areaSalida);
        panelScroll.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));

        panelPrincipal.add(panelControl, BorderLayout.NORTH);
        panelPrincipal.add(panelScroll, BorderLayout.CENTER);

        add(panelPrincipal);

        botonAplicar.addActionListener(e -> procesarConfiguracion());
    }

    private void procesarConfiguracion() {
        String modoSeleccionado = (String) selectorModo.getSelectedItem();
        String vehiculoSeleccionado = (String) selectorVehiculo.getSelectedItem();

        ModoConduccion modo = null;
        if (modoSeleccionado.equals("Modo Ecológico")) {
            modo = new ModoEco();
        } else if (modoSeleccionado.equals("Modo Deportivo")) {
            modo = new ModoDeportivo();
        } else if (modoSeleccionado.equals("Tracción Clásica (Adaptada)")) {
            modo = new AdaptadorTraccion(new SistemaTraccionAntiguo());
        }

        Vehiculo vehiculo = null;
        if (vehiculoSeleccionado.equals("Renault Arkana")) {
            vehiculo = new Renault(modo);
        } else if (vehiculoSeleccionado.equals("BMW Serie M")) {
            vehiculo = new Bmw(modo);
        } else if (vehiculoSeleccionado.equals("Mercedes-Benz Clase AMG")) {
            vehiculo = new Mercedes(modo);
        }

        if (vehiculo != null) {
            areaSalida.append(vehiculo.configurarModo() + "\n--------------------------------------------------\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimuladorAutomotrizGUI gui = new SimuladorAutomotrizGUI();
            gui.setVisible(true);
        });
    }
}