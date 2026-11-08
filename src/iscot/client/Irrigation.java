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

import iscot.client.gui.IrrigationIcon;
import iscot.enums.Areas;
import iscot.enums.OnOffMode;
import iscot.events.AreaIrrigationOrderEvent;

public class Irrigation {

    private final Areas area;
    private final IrrigationIcon rega = new IrrigationIcon();
    private final JLabel estado = new JLabel("Estado: Inativa", SwingConstants.CENTER);

    public Irrigation(Areas area) {
        this.area = area;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Invalid arguments!");
            return;
        }

        // A ligação e o registo no servidor serão implementados nas fases seguintes.
        Areas area = Areas.valueOf(args[2]);
        SwingUtilities.invokeLater(() -> new Irrigation(area).openWindow());
    }

    private void openWindow() {
        JFrame janela = new JFrame("Rega em " + area);
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
        painel.add(rega, BorderLayout.CENTER);

        JComboBox<Areas> areaDaOrdem = new JComboBox<>(Areas.values());
        areaDaOrdem.setSelectedItem(area);

        JButton ligar = new JButton("Ligar");
        ligar.addActionListener(e -> processOrder(
                new AreaIrrigationOrderEvent((Areas) areaDaOrdem.getSelectedItem(), OnOffMode.ON)));

        JButton desligar = new JButton("Desligar");
        desligar.addActionListener(e -> processOrder(
                new AreaIrrigationOrderEvent((Areas) areaDaOrdem.getSelectedItem(), OnOffMode.OFF)));

        JPanel controlos = new JPanel();
        controlos.add(new JLabel("Área da ordem:"));
        controlos.add(areaDaOrdem);
        controlos.add(ligar);
        controlos.add(desligar);
        painel.add(controlos, BorderLayout.SOUTH);
        return painel;
    }

    private void processOrder(AreaIrrigationOrderEvent ordem) {
        if (ordem.getArea() != area) {
            System.out.println("Ordem " + ordem.getId() + " ignorada: destina-se a " + ordem.getArea() + ".");
            return;
        }

        boolean ligado = ordem.getState() == OnOffMode.ON;
        rega.setAtiva(ligado);
        String descricao = ligado ? "Ativa" : "Inativa";
        estado.setText("Estado: " + descricao);
        System.out.println("Rega : " + area + " - " + descricao + " (evento " + ordem.getId() + ")");
    }
}
