package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Model.Carro;
import Model.Repository.Repository;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormCadastroCarro extends JInternalFrame {

	private JLabel lbAno, lbMarca, lbModelo, lbPlaca;
	private JTextField tfAno, tfMarca, tfModelo;
	private JFormattedTextField tfPlaca;
	private MaskFormatter maskPalca;
	private JButton btSalvar;
	private JPanel pnGeral, pnBusca;
	private JTable tabela;
	private JScrollPane scroll;
	private JTabbedPane abas;
	
	
	public FormCadastroCarro() {

		try {
			inicializaComponentes();
			definirEventos();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
	}

	public void inicializaComponentes() throws ParseException {
		//Paineis 
		pnGeral = new JPanel();
		pnGeral.setLayout(null);
		pnBusca = new JPanel();
		pnBusca.setLayout(null);
		
		
		// label
		lbAno = new JLabel("Ano");
		lbAno.setLocation(40, 40);
		lbAno.setSize(30, 20);
		pnGeral.add(lbAno);

		lbMarca = new JLabel("Marca");
		lbMarca.setLocation(155, 40);
		lbMarca.setSize(100, 20);
		pnGeral.add(lbMarca);

		lbModelo = new JLabel("Modelo");
		lbModelo.setLocation(40, 80);
		lbModelo.setSize(100, 20);
		pnGeral.add(lbModelo);

		lbPlaca = new JLabel("Placa");
		lbPlaca.setLocation(200, 80);
		lbPlaca.setSize(100, 20);
		pnGeral.add(lbPlaca);
		// text
		tfAno = new JTextField();
		tfAno.setLocation(70, 40);
		tfAno.setSize(70, 20);
		pnGeral.add(tfAno);

		tfMarca = new JTextField();
		tfMarca.setLocation(200, 40);
		tfMarca.setSize(70, 20);
		pnGeral.add(tfMarca);

		tfModelo = new JTextField();
		tfModelo.setLocation(95, 80);
		tfModelo.setSize(70, 20);
		pnGeral.add(tfModelo);

		maskPalca = new MaskFormatter("***-####");
		maskPalca.setPlaceholderCharacter('_');
		tfPlaca = new JFormattedTextField(maskPalca);
		tfPlaca.setLocation(250, 80);
		tfPlaca.setSize(60, 20);
		pnGeral.add(tfPlaca);
		// Button
		btSalvar = new JButton("Salvar");
		btSalvar.setLocation(120, 140);
		btSalvar.setSize(100, 30);
		pnGeral.add(btSalvar);
		
		
		abas = new JTabbedPane();
		abas.setBounds(1, 1, 345, 225);
		abas.addTab("Cadastro", pnGeral);
		abas.addTab("Busca", pnBusca);
		add(abas);

		 // PAINEL//
        getContentPane().setLayout(null);
        setSize(350, 230);
        setLocation(40, 10);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setClosable(true);
        setTitle("Cadastro Carro");
        setResizable(false);
        setIconifiable(true);
	}

	public void definirEventos() {
		btSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfAno.setText(tfAno.getText().trim());
				tfMarca.setText(tfMarca.getText().trim());
				tfModelo.setText(tfModelo.getText().trim());
				tfPlaca.setText(tfPlaca.getText().trim());
				if (tfAno.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informar o ano");
					lbAno.setForeground(Color.RED);
					tfAno.requestFocus(true);
				} else if (tfMarca.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informar a marca");
					lbMarca.setForeground(Color.RED);
					tfMarca.requestFocus(true);
				} else {
                                    Carro carro = new Carro();
                                    carro.setAno(Long.parseLong(tfAno.getText()));
                                    carro.setMarca(tfMarca.getText());
                                    carro.setModelo(tfModelo.getText());
                                    carro.setPlaca(tfPlaca.getValue().toString());
                                    Repository<Model.Carro> repo = new Repository<Carro>();
                                    repo.Adicionar(carro);
				}   
			}
		});
		tfAno.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				int caracteres = 10;
				if(tfAno.getText().length() > caracteres){
					e.consume();
		}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		tfMarca.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				int caracteres = 20;
				if (tfMarca.getText().length() >= caracteres
						&& e.getKeyCode() != 8) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				tfMarca.setText(tfMarca.getText().toUpperCase());

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

}
