import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import javax.swing.*;

public class App {
    JFrame tela;
    JLabel list;
    String listString = "";
    int valorTotal = 0;
    JButton config;
    JButton edit;
    JFrame editFrame;
    JTextField novoItem;
    JTextField preco;
    JTextField quant;
    JTextField product;
    JButton adicionar;
    JButton remover;
    JFrame setting;
    JTextField novoDia;
    JButton guardar;
    JButton checkUp;

    public static void main(String[] args) throws Exception {
        App app = new App();
        File firstTime = new File("data/allSet");
        
        if (firstTime.exists()) {
            app.tela = new JFrame("Rancho Maker");
            app.tela.setSize(720, 480);
            app.tela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            app.tela.setLocationRelativeTo(null);
            app.tela.setLayout(null);
            app.tela.setResizable(false);
            app.tela.setVisible(true);

            app.config = new JButton("Configurações");
            app.config.setBounds(app.tela.getWidth() - 180, 10, 150, 30);
            app.config.setFont(new Font("Arial", Font.PLAIN, 14));
            app.config.setForeground(Color.BLACK);
            app.config.setBackground(Color.WHITE);
            app.config.addActionListener(_ -> settings(app));
            app.tela.add(app.config);

            app.edit = new JButton("Editar lista");
            app.edit.setBounds(app.tela.getWidth() - 180, 50, 150, 30);
            app.edit.setFont(new Font("Arial", Font.PLAIN, 14));
            app.edit.setForeground(Color.BLACK);
            app.edit.setBackground(Color.WHITE);
            app.edit.addActionListener(_ -> editList(app));
            app.tela.add(app.edit);

            String dia = new String(Files.readAllBytes(Paths.get("data/dia")));
            Date data = new Date();
            int hoje = data.getDate();
            int mesInt = data.getMonth();
            String mes = "";
            if (Integer.parseInt(dia) > hoje) {
                mes = app.queMes(mesInt);
            } else {
                mes = app.queMes(mesInt + 1);
            }

            JLabel title = new JLabel("<html><strong>Rancho Maker<br>Seu programa para gerir o rancho com facilidade!</strong><br><br>Proximo rancho: " + dia + " de " + mes + "</html>");
            title.setBounds(10, 0, app.tela.getWidth() - 10, 100);
            title.setFont(new Font("Arial", Font.PLAIN, 14));
            title.setForeground(Color.BLACK);
            app.tela.add(title);

            JLabel banner = new JLabel();
            banner.setBounds(0, 0, app.tela.getWidth(), 100);
            banner.setOpaque(true);
            banner.setBackground(new Color(102, 255, 102));
            app.tela.add(banner);

            app.list = new JLabel();
            app.list.setBounds(10, 110, app.tela.getWidth() - 50, 130 + app.altura());
            app.list.setFont(new Font("Arial", Font.PLAIN, 14));
            app.list.setForeground(Color.BLACK);
            app.tela.add(app.list);
            app.setList();

            app.tela.repaint();
            app.tela.revalidate();
        } else {
            Welcome welcomeClass = new Welcome(app);
            welcomeClass.setUp();
        }
    }

    public int altura() {
        for (int i = 0; true; i ++) {
            File item = new File("data/" + i + "item");

            if (!item.exists()) {
                return i * 20;
            }
        }
    }

    public void setList() {
        for (int i = 0; true; i ++) {
            try {
                String itemString = new String(Files.readAllBytes(Paths.get("data/" + i + "item")));
                String precoString = new String(Files.readAllBytes(Paths.get("data/" + i + "preco")));
                String quantString = new String(Files.readAllBytes(Paths.get("data/" + i + "quant")));

                int quantos = Integer.parseInt(quantString);
                int precoCada = Integer.parseInt(precoString);
                int precoDoItem = precoCada * quantos;
                this.valorTotal += precoDoItem;
                this.listString += itemString + "     (" + quantos + ")  -  " + precoCada + " MZN  -  <strong>" + precoDoItem + " MZN</strong><br>";
            } catch (Exception a) {
                break;
            }
        }

        String lista = "<html><strong>O seu rancho...<br>Item     (Quantidade)  -  Preço cada  -   Total</strong><br><br>" + this.listString + "<br><strong>Total: " + this.valorTotal + " MZN</strong></html>";
        this.list.setText(lista);
    }

    public String queMes(int mes) {        
        switch (mes) {
            case 0: return "Janeiro";

            case 1: return "Fevereiro";

            case 2: return "Março";

            case 3: return "Abril";

            case 4: return "Maio";

            case 5: return "Junho";

            case 6: return "Julho";

            case 7: return "Agosto";

            case 8: return "Setembro";

            case 9: return "Outubro";

            case 10: return "Novembro";

            case 11: return "Dezembro";

            default: return null;
        }
    }

    public static void editList(App app) {
        app.editFrame = new JFrame("Editar lista");
        app.editFrame.setSize(480, 360);
        app.editFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        app.editFrame.setResizable(false);
        app.editFrame.setLocationRelativeTo(null);
        app.editFrame.setLayout(null);
        app.editFrame.setAlwaysOnTop(true);
        app.editFrame.addWindowListener(new WindowAdapter() {
            @Override
            
            public void windowClosing(WindowEvent a) {
                try {
                    app.tela.setVisible(false);
                    main(null);
                } catch (Exception b) {

                }
            }
        });
        app.editFrame.setVisible(true);

        app.config.setVisible(false);
        app.edit.setVisible(false);
        app.tela.repaint();
        app.tela.revalidate();

        JLabel title = new JLabel("<html><strong>Rancho Maker<br>Seu programa para gerir o rancho com facilidade!</strong><br><br>Aqui poderá editar o seu rancho, remover ou adicionar itens</html>");
        title.setBounds(10, 0, app.editFrame.getWidth() - 10, 90);
        title.setFont(new Font("Arial", Font.PLAIN, 14));
        title.setForeground(Color.BLACK);
        app.editFrame.add(title);

        JLabel banner = new JLabel();
        banner.setBounds(0, 0, app.editFrame.getWidth(), 90);
        banner.setOpaque(true);
        banner.setBackground(new Color(102, 255, 102));
        app.editFrame.add(banner);

        JLabel parag = new JLabel("<html>Para adicionar um novo item insira os valores como nome do item, preço, e quantidade. Para remover apenas insira o nome.<br><br><strong>Nome                               Preço     Quantidade</strong></html>");
        parag.setBounds(10, 100, app.editFrame.getWidth() - 20, 90);
        parag.setFont(new Font("Arial", Font.PLAIN, 14));
        parag.setForeground(Color.BLACK);
        app.editFrame.add(parag);

        app.product = new JTextField();
        app.product.setBounds(10,190, 150, 30);
        app.product.setFont(new Font("Arial", Font.PLAIN, 14));
        app.product.setForeground(Color.BLACK);
        app.product.setBackground(Color.WHITE);
        app.editFrame.add(app.product);

        app.preco = new JTextField();
        app.preco.setBounds(175, 190, 50, 30);
        app.preco.setFont(new Font("Arial", Font.PLAIN, 14));
        app.preco.setForeground(Color.BLACK);
        app.preco.setBackground(Color.WHITE);
        app.editFrame.add(app.preco);

        app.quant = new JTextField();
        app.quant.setBounds(240, 190, 30, 30);
        app.quant.setFont(new Font("Arial", Font.PLAIN, 14));
        app.quant.setForeground(Color.BLACK);
        app.quant.setBackground(Color.WHITE);
        app.editFrame.add(app.quant);

        app.adicionar = new JButton("Adicionar");
        app.adicionar.setBounds(10, 230, 100, 30);
        app.adicionar.setFont(new Font("Arial", Font.PLAIN, 14));
        app.adicionar.setForeground(Color.BLACK);
        app.adicionar.setBackground(Color.WHITE);
        app.adicionar.addActionListener(_ -> addItem(app));
        app.editFrame.add(app.adicionar);

        app.remover = new JButton("Remover");
        app.remover.setBounds(120, 230, 100, 30);
        app.remover.setFont(new Font("Arial", Font.PLAIN, 14));
        app.remover.setForeground(Color.BLACK);
        app.remover.setBackground(Color.WHITE);
        app.remover.addActionListener(_ -> removeItem(app));
        app.editFrame.add(app.remover);
    }

    public static void addItem(App app) {
        Welcome welcomeClass = new Welcome(app);
        
        if (welcomeClass.isString(app.product.getText()) && welcomeClass.isValidInt(app.quant.getText()) && welcomeClass.isValidInt(app.preco.getText())) {
            for (int i = 0; true; i ++) {
                File nomeFile = new File("data/" + i + "item");
                File quantFile = new File("data/" + i + "quant");
                File precoFile = new File("data/" + i + "preco");

                if (!nomeFile.exists() && !quantFile.exists() && !precoFile.exists()) {
                    try {
                        nomeFile.createNewFile();
                        quantFile.createNewFile();
                        precoFile.createNewFile();

                        FileWriter nomeWriter = new FileWriter("data/" + i + "item");
                        nomeWriter.write(app.product.getText());
                        nomeWriter.close();

                        FileWriter quantWriter = new FileWriter("data/" + i + "quant");
                        quantWriter.write(app.quant.getText());
                        quantWriter.close();

                        FileWriter precoWriter = new FileWriter("data/" + i + "preco");
                        precoWriter.write(app.preco.getText());
                        precoWriter.close();

                        app.editFrame.setAlwaysOnTop(false);
                        JOptionPane.showMessageDialog(null, "Item adicinado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
                        app.editFrame.setAlwaysOnTop(true);
                        break;
                    } catch (Exception a) {
                        app.editFrame.setAlwaysOnTop(false);
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao guardar o item\nCódigo: " + a, null, JOptionPane.ERROR_MESSAGE);
                        app.editFrame.setAlwaysOnTop(true);
                        break;
                    }
                }
            }
        } else {
            app.editFrame.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Por favor digite valores válidos", null, JOptionPane.WARNING_MESSAGE);
            app.editFrame.setAlwaysOnTop(true);
        }
    }

    public static void removeItem(App app) {
        Welcome welcome = new Welcome(app);

        if (welcome.isString(app.product.getText())) {
            for (int i = 0; true; i ++) {
                try {
                    String nome = new String(Files.readAllBytes(Paths.get("data/" + i + "item")));

                    if (nome.equalsIgnoreCase(app.product.getText())) {
                        File item = new File("data/" + i + "item");
                        File preco = new File("data/" + i + "preco");
                        File quant = new File("data/" + i + "quant");
    
                        item.delete();
                        preco.delete();
                        quant.delete();

                        app.editFrame.setAlwaysOnTop(false);
                        JOptionPane.showMessageDialog(null, "Item removido com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
                        app.editFrame.setAlwaysOnTop(true);
                        break;
                    }
                } catch (Exception a) {
                    app.editFrame.setAlwaysOnTop(false);
                    JOptionPane.showMessageDialog(null, "Item não encontrado na sua lista, certifique-se de que o nome está certo", null, JOptionPane.WARNING_MESSAGE);
                    app.editFrame.setAlwaysOnTop(true);
                    break;
                }
            }
        } else {
            app.editFrame.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Por favor digite o nome do item para poder remové-lo da lista", null, JOptionPane.WARNING_MESSAGE);
            app.editFrame.setAlwaysOnTop(true);
        }
    }

    public static void settings(App app) {
        app.setting = new JFrame("Configurações");
        app.setting.setSize(480, 360);
        app.setting.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        app.setting.setResizable(false);
        app.setting.setLocationRelativeTo(null);
        app.setting.setLayout(null);
        app.setting.setAlwaysOnTop(true);
        app.setting.addWindowListener(new WindowAdapter() {
            @Override

            public void windowClosing(WindowEvent a) {
                try {
                    app.tela.setVisible(false);
                    main(null);
                } catch (Exception b) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado!\nCódigo: " + a, null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        app.setting.setVisible(true);

        app.config.setVisible(false);
        app.edit.setVisible(false);
        app.tela.repaint();
        app.tela.revalidate();

        JLabel title = new JLabel("<html><strong>Rancho Maker<br>Seu programa para gerir o rancho com facilidade!</strong><br><br>Aqui poderá configurar o programa ao seu gosto</html>");
        title.setBounds(10, 0, app.setting.getWidth() - 10, 90);
        title.setFont(new Font("Arial", Font.PLAIN, 14));
        title.setForeground(Color.BLACK);
        app.setting.add(title);

        JLabel banner = new JLabel();
        banner.setBounds(0, 0, app.setting.getWidth(), 90);
        banner.setOpaque(true);
        banner.setBackground(new Color(102, 255, 102));
        app.setting.add(banner);

        JLabel parag = new JLabel("<html><strong>Trocar dia do rancho:</strong><br><br><br><br><br><br><br><br><br><br>Criado por <br>Cledson Vilanculo</html>");
        parag.setBounds(10, 100, 150, 210);
        parag.setFont(new Font("Arial", Font.PLAIN, 14));
        parag.setForeground(Color.BLACK);
        app.setting.add(parag);

        String dia = "";
        try {
            dia = new String(Files.readAllBytes(Paths.get("data/dia")));
        } catch (Exception a) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado!\nCódigo: " + a, null, JOptionPane.ERROR_MESSAGE);
        }
        
        app.novoDia = new JTextField(dia);
        app.novoDia.setBounds(170, 100, 30, 30);
        app.novoDia.setFont(new Font("Arial", Font.PLAIN, 14));
        app.novoDia.setForeground(Color.BLACK);
        app.novoDia.setBackground(Color.WHITE);
        app.setting.add(app.novoDia);

        app.guardar = new JButton("Guardar");
        app.guardar.setBounds(210, 100, 100, 30);
        app.guardar.setFont(new Font("Arial", Font.PLAIN, 14));
        app.guardar.setForeground(Color.BLACK);
        app.guardar.setBackground(Color.WHITE);
        app.guardar.addActionListener(_ -> save(app));
        app.setting.add(app.guardar);
    }

    public static void save(App app) {
        Welcome welcome = new Welcome(app);
        if (welcome.isValidDate(app.novoDia.getText())) {
            try {
                FileWriter diaWriter = new FileWriter("data/dia");
                diaWriter.write(app.novoDia.getText());
                diaWriter.close();
                app.setting.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "Dia do rancho alterado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
                app.setting.setAlwaysOnTop(true);
            } catch (Exception a) {
                app.setting.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao guardar as informações!\nCódigo: " + a, null, JOptionPane.ERROR_MESSAGE);
                app.setting.setAlwaysOnTop(true);
            }
        } else {
            app.setting.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Por favor insira uma data válida!", null, JOptionPane.WARNING_MESSAGE);
            app.setting.setAlwaysOnTop(true);
        }
    }
}