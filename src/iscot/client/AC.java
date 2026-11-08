package iscot.client;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import iscot.client.gui.ACIcon;
import iscot.enums.AirConditioningMode;
import iscot.enums.Areas;
import iscot.events.AreaTemperatureOrderEvent;

public class AC {

    private final Areas area;
    private final ACIcon arCondicionado = new ACIcon();
    private final JLabel estado = new JLabel("Modo: Desligado", SwingConstants.CENTER);

    public AC(Areas area) {
        this.area = area;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Invalid arguments!");
            return;
        }

        // A ligação e o registo no servidor serão implementados nas fases seguintes.
        Areas area = Areas.valueOf(args[2]);
        SwingUtilities.invokeLater(() -> new AC(area).openWindow());
    }

    private void openWindow() {
        JFrame janela = new JFrame("Ar condicionado em " + area);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setContentPane(createPanel());
        janela.pack();
        janela.setMinimumSize(janela.getSize());
        janela.setLocationByPlatform(true);
        janela.setVisible(true);
    }

    JPanel createPanel() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(estado, BorderLayout.NORTH);
        painel.add(arCondicionado, BorderLayout.CENTER);

        JComboBox<Areas> areaDaOrdem = new JComboBox<>(Areas.values());
        areaDaOrdem.setSelectedItem(area);
        JComboBox<AirConditioningMode> modos = new JComboBox<>(AirConditioningMode.values());
        modos.setSelectedItem(AirConditioningMode.Desligado);

        JButton aplicar = new JButton("Aplicar modo");
        aplicar.addActionListener(e -> processOrder(new AreaTemperatureOrderEvent(
                (Areas) areaDaOrdem.getSelectedItem(),
                (AirConditioningMode) modos.getSelectedItem())));

        JPanel controlos = new JPanel();
        controlos.add(new JLabel("Área da ordem:"));
        controlos.add(areaDaOrdem);
        controlos.add(new JLabel("Modo:"));
        controlos.add(modos);
        controlos.add(aplicar);
        painel.add(controlos, BorderLayout.SOUTH);
        return painel;
    }

    private void processOrder(AreaTemperatureOrderEvent ordem) {
        if (ordem.getArea() != area) {
            System.out.println("Ordem " + ordem.getId() + " ignorada: destina-se a " + ordem.getArea() + ".");
            return;
        }

        arCondicionado.setModo(ordem.getMode());
        estado.setText("Modo: " + ordem.getMode());
        System.out.println("Ar condicionado : " + area + " - " + ordem.getMode()
                + " (evento " + ordem.getId() + ")");
    }
}
