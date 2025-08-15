import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;

public class Welcome {
    App app;
    JLabel parag;
    JTextField product;
    JTextField preco;
    JTextField quant;
    JButton adicionar;
    JButton terminar;
    JFrame welcome;

    public Welcome(App app) {
        this.app = app;
    }

    public void setUp() {
        this.welcome = new JFrame("Bem vindo!");
        this.welcome.setSize(480, 360);
        this.welcome.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.welcome.setLocationRelativeTo(null);
        this.welcome.setResizable(false);
        this.welcome.setLayout(null);
        this.welcome.setVisible(true);

        JLabel title = new JLabel("<html>Bem vindo ao Rancho Maker!<br>O programa que facilita o seu rancho</html>");
        title.setBounds(10, 10, welcome.getWidth() - 10, 40);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.BLACK);
        welcome.add(title);

        JLabel banner = new JLabel();
        banner.setBounds(0, 0, welcome.getWidth(), 60);
        banner.setOpaque(true);
        banner.setBackground(new Color(102, 255, 102));
        welcome.add(banner);

        this.parag = new JLabel("<html>O Rancho Maker tem como objectivo facilitar o teu rancho, gerindo a <br>sua lista de compras, preços, e a data do rancho. Mas antes precisa fornecer algumas informações para configurar o programa, como os <br>itens que compra no rancho, os preços dos mesmos e a data do <br>rancho.<br><br><strong>Item da lista                   Preço (cada)   Quantos?</strong></html>");
        this.parag.setBounds(10, 70, welcome.getWidth() - 10, 120);
        this.parag.setFont(new Font("Arial", Font.PLAIN, 14));
        this.parag.setForeground(Color.BLACK);
        welcome.add(this.parag);

        this.product = new JTextField();
        this.product.setBounds(10, 200, 150, 30);
        this.product.setFont(new Font("Arial", Font.PLAIN, 14));
        this.product.setForeground(Color.BLACK);
        this.product.setBackground(Color.WHITE);
        welcome.add(this.product);

        this.preco = new JTextField();
        this.preco.setBounds(170, 200, 50, 30);
        this.preco.setFont(new Font("Arial", Font.PLAIN, 14));
        this.preco.setForeground(Color.BLACK);
        this.preco.setBackground(Color.WHITE);
        welcome.add(this.preco);

        this.quant = new JTextField();
        this.quant.setBounds(270, 200, 30, 30);
        this.quant.setFont(new Font("Arial", Font.PLAIN, 14));
        this.quant.setForeground(Color.BLACK);
        this.quant.setBackground(Color.WHITE);
        welcome.add(this.quant);

        this.adicionar = new JButton("Adicionar");
        this.adicionar.setBounds(310, 200, 100, 30);
        this.adicionar.setFont(new Font("Arial", Font.PLAIN, 14));
        this.adicionar.setForeground(Color.BLACK);
        this.adicionar.setBackground(Color.WHITE);
        this.adicionar.addActionListener(_ -> addItem());
        welcome.add(this.adicionar);

        this.terminar = new JButton("Terminar");
        this.terminar.setBounds(310, 270, 100, 30);
        this.terminar.setFont(new Font("Arial", Font.PLAIN, 14));
        this.terminar.setForeground(Color.BLACK);
        this.terminar.setBackground(Color.WHITE);
        this.terminar.addActionListener(_ -> finish());
        welcome.add(this.terminar);

        welcome.repaint();
        welcome.revalidate();

        File dataFolder = new File("data");
        try {
            dataFolder.mkdir();
        } catch (Exception a) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado!\nCódigo: " + a, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addItem() {
        if (!isValidInt(this.preco.getText())) {
            JOptionPane.showMessageDialog(null, "O preço inserido não é válido!", null, JOptionPane.WARNING_MESSAGE);
        } else if (!isValidInt(this.quant.getText())) {
            JOptionPane.showMessageDialog(null, "A quantidade inserida não é válida!", null, JOptionPane.WARNING_MESSAGE); 
        } else if (!isString(this.product.getText())) {
            JOptionPane.showMessageDialog(null, "O nome do item inserido não é válido!", null, JOptionPane.WARNING_MESSAGE); 
        } else {
            for (int i = 0; true; i ++) {
                File itemFile = new File("data/" + i + "item");
                File precoFile = new File("data/" + i + "preco");
                File quantFile = new File("data/" + i + "quant");

                if (!itemFile.exists() || !precoFile.exists() || !quantFile.exists()) {
                    try {
                        itemFile.createNewFile();
                        FileWriter itemWriter = new FileWriter("data/" + i + "item");
                        itemWriter.write(this.product.getText());
                        itemWriter.close();

                        precoFile.createNewFile();
                        FileWriter precoWriter = new FileWriter("data/" + i + "preco");
                        precoWriter.write(this.preco.getText());
                        precoWriter.close();

                        quantFile.createNewFile();
                        FileWriter quantWriter = new FileWriter("data/" + i + "quant");
                        quantWriter.write(this.quant.getText());
                        quantWriter.close();
                        break;
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao guardar as informações!\nCódigo: " + a, "ERRO", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
            }
        }
    }

    public boolean isValidInt(String a) {
        try {
            int b = Integer.parseInt(a);
            if (b > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception _) {
            return false;
        }
    }

    public boolean isString(String a) {
        if (a.length() < 1) {
            return false;
        } else {
            try {
                int _ = Integer.parseInt(a);
                return false;
            } catch (Exception _) {
                return true;
            }
        }
    }

    public void finish() {
        File minItem = new File("data/0item");
        File minQuant = new File("data/0quant");
        File minPreco = new File("data/0preco");
        File allSet = new File("data/allSet");
        File diaFile = new File("data/dia");
        
        if (minItem.exists() && minPreco.exists() && minQuant.exists()) {
            String diaDoRancho = JOptionPane.showInputDialog(null, "Defina o dia do rancho (1 - 28)");

            if (isValidDate(diaDoRancho)) {
                try {
                    FileWriter diaWriter = new FileWriter("data/dia");
                    diaWriter.write(diaDoRancho);
                    diaWriter.close();
                    diaFile.createNewFile();
                    allSet.createNewFile();
                    this.welcome.setVisible(false);
                    app.main(null);
                } catch (Exception a) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro desconhecido!\nCódigo: " + a, "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor digite uma data dentro do intervalo indicado", null, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor adicione pelo menos um item na lista", null, JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isValidDate(String a) {
        try {
            int b = Integer.parseInt(a);

            if (b < 29 && b > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception _) {
            return false;
        }
    }
}