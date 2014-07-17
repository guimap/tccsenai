package View;

import DAO.DAOcliente;
import Model.Cliente;
import Model.Cnh;
import Model.Endereco;
import Model.ModeloTableCliente;
import Model.ModeloTablePacote;
import Model.Pacote;
import Model.Repository.ConnectionFactoryRepository;
import Model.Repository.Repository;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.MaskFormatter;
import javax.swing.text.TabExpander;

import principal.CadastroCliente;
import principal.VerificadorDeCpf;

public class FormCadastroCliente extends JInternalFrame {

    private JLabel lbNome, lbEmail, lbEscolaridade, lbProfissao, lbNascimento,
            lbTelefone, lbPrimeiraHabilitacao, lbValidadeCnh, lbRegistroCnh,
            lbLogradouro, lbBairro, lbNumero, lbCep, lbRg, lbCpf, lbSexo,
            lbObserva, lbQ1, lbQ2, lbQ3, lbQ4, lbData, lbCelular, lbAluno, lbPacote, lbTipoPagamento,
            lbParcelas, lbPagamentoInicial;
    private JTextField tfNome, tfEmail, tfProfissao, tfRegistroCnh,
            tfLogradouro, tfBairro, tfRg, tfQuestao1, tfNumero, tfBuscaCliente,
            tfParcelas;
    private JFormattedTextField tfData, tfNascimento, tfCep, tfCpf, tfCelular,
            tfTelefone, tfValidadeCnh, tfPrimeiraHabilitacao, tfPagamentoInicial;
    
    private JButton btSalvar, btExcluir, btBuscar;
    
    private JTabbedPane abas;
    
    
    
    private JTextArea observa;
    private JScrollPane scroll, scTable, scrollPacote;
    private ButtonGroup gQ2, gQ3;
    private JRadioButton jrQ2Yes, jrQ2No, jrQ3Yes, jrQ3No;
    private String[] sexo = {"M", "F"};
    private String[] escolaridade = {"Superior", "Tecnico", "Médio",
        "Fundamental"};
    
    private String[] pagamento = {"Dinheiro", "D�bito", "Cr�dito", "Cheque"};
    
    private String[] pesquisa = {"Internet", "Indica��o", "Outros"};
    private JComboBox<String> jcSexo, jcEscolaridade, jcPesquisa, jcPagamento;
    private JTable table, tablePacote;
    private String data;
    private ArrayList<Cliente> listCliente = new ArrayList<Cliente>();
    private ArrayList<Pacote> listPacote = new ArrayList<Pacote>();
    private MaskFormatter dataMask, dataMaskNascimento, maskCep, maskNumero,
            maskCpf, maskTelefone, maskCelular, maskValidadeCnh,
            maskPrimeiraHabilitacao;
    private DAOcliente daoCliente;
    Cliente cliente = new Cliente();
    Endereco endereco = new Endereco();
    Cnh cnh = new Cnh();
    CadastroCliente cadastro = new CadastroCliente();

    private JPanel panelCliente, painelGeral, painelTodos, painelBusca;
    
    private boolean aberto = false;

    public FormCadastroCliente() {

        try {
            Cliente c = new Cliente();
            try {
                daoCliente = new DAOcliente();
                listCliente = daoCliente.buscarTodos();
            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, e.getMessage());
            }

            inicializaComponentes();

            definirEventos();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }

    }
    public void limparCampos() {
        tfNome.setText("");
        tfData.setValue(dataMask);
        tfLogradouro.setText("");
        tfNumero.setText("");
        tfBairro.setText("");
        jcSexo.setSelectedIndex(-1);
        tfRg.setText("");
        tfCpf.setValue(maskCpf);
        tfCep.setValue(maskCep);
        tfNascimento.setValue(dataMaskNascimento);
        tfTelefone.setValue(maskTelefone);
        tfCelular.setValue(maskCelular);
        tfEmail.setText("");
        jcEscolaridade.setSelectedIndex(-1);
        tfProfissao.setText("");
        tfPrimeiraHabilitacao.setValue(maskPrimeiraHabilitacao);
        tfValidadeCnh.setValue(maskValidadeCnh);
        tfRegistroCnh.setText("");
        tfQuestao1.setText("");
        jcPesquisa.setSelectedIndex(-1);
        observa.setText("");
        cliente = null;
    }

    public void populaList() {
        try {
            for (Cliente c : listCliente) {
                ArrayList<Cliente> l = new ArrayList<Cliente>();
                l = daoCliente.buscarTodos();

                listCliente = l;
                table.setModel(new ModeloTableCliente(listCliente));
            }
        } catch (SQLException e1) {

        }
    }

    public void inicializaComponentes() throws ParseException {
    	painelTodos = new JPanel();
    	painelTodos.setLayout(null);
    	painelBusca = new JPanel();
    	painelBusca.setLayout(null);
    	
    	

        panelCliente = new JPanel();
        Border border = BorderFactory.createTitledBorder("Cliente");
        panelCliente.setBorder(border);
        panelCliente.setLayout(new GridLayout(7, 2));
        panelCliente.setBounds(10, 20, 390, 220);

        // Nome
        lbNome = new JLabel("  Nome");
        lbNome.setLocation(40, 40);
        lbNome.setSize(35, 10);
        panelCliente.add(lbNome);

        tfNome = new JTextField();
        tfNome.setLocation(80, 37);
        tfNome.setSize(300, 30);
        panelCliente.add(tfNome);

        lbData = new JLabel("  Data");
        lbData.setLocation(390, 40);
        lbData.setSize(35, 10);
        panelCliente.add(lbData);

        dataMask = new MaskFormatter("##/##/####");
        dataMask.setPlaceholderCharacter('_');
        tfData = new JFormattedTextField(dataMask);
        tfData.setLocation(420, 37);
        tfData.setSize(68, 30);
        panelCliente.add(tfData);

        lbLogradouro = new JLabel("  Rua");
        lbLogradouro.setLocation(40, 70);
        lbLogradouro.setSize(30, 10);
        panelCliente.add(lbLogradouro);

        tfLogradouro = new JTextField();
        tfLogradouro.setLocation(80, 67);
        tfLogradouro.setSize(250, 30);
        panelCliente.add(tfLogradouro);

        lbNumero = new JLabel("  Numero");
        lbNumero.setLocation(350, 70);
        lbNumero.setSize(50, 10);
        panelCliente.add(lbNumero);

        tfNumero = new JTextField();
        tfNumero.setLocation(410, 67);
        tfNumero.setSize(60, 30);
        panelCliente.add(tfNumero);

        lbBairro = new JLabel("  Bairro");
        lbBairro.setLocation(40, 100);
        lbBairro.setSize(50, 10);
        panelCliente.add(lbBairro);

        tfBairro = new JTextField();
        tfBairro.setLocation(90, 97);
        tfBairro.setSize(180, 30);
        panelCliente.add(tfBairro);

        lbCep = new JLabel("  Cep");
        lbCep.setLocation(300, 100);
        lbCep.setSize(30, 15);
        panelCliente.add(lbCep);

        maskCep = new MaskFormatter("#####-###");
        maskCep.setPlaceholderCharacter('_');
        tfCep = new JFormattedTextField(maskCep);
        tfCep.setLocation(340, 97);
        tfCep.setSize(67, 30);
        panelCliente.add(tfCep);

        lbNascimento = new JLabel("  Data Nasc.");
        lbNascimento.setLocation(40, 130);
        lbNascimento.setSize(65, 15);
        panelCliente.add(lbNascimento);

        dataMaskNascimento = new MaskFormatter("##/##/####");
        dataMaskNascimento.setPlaceholderCharacter('_');
        tfNascimento = new JFormattedTextField(dataMaskNascimento);
        tfNascimento.setLocation(105, 127);
        tfNascimento.setSize(68, 30);
        panelCliente.add(tfNascimento);

        lbSexo = new JLabel("  Sexo");
        lbSexo.setLocation(180, 130);
        lbSexo.setSize(30, 15);
        panelCliente.add(lbSexo);

        // Combo box
        jcSexo = new JComboBox<String>(sexo);
        jcSexo.setLocation(220, 127);
        jcSexo.setSize(50, 30);
        jcSexo.setSelectedIndex(-1);
        panelCliente.add(jcSexo);

        lbCpf = new JLabel("  Cpf");
        lbCpf.setLocation(40, 160);
        lbCpf.setSize(30, 15);
        panelCliente.add(lbCpf);

        maskCpf = new MaskFormatter("###.###.###-##");
        maskCpf.setPlaceholderCharacter('_');
        tfCpf = new JFormattedTextField(maskCpf);
        tfCpf.setLocation(80, 157);
        tfCpf.setSize(94, 30);
        panelCliente.add(tfCpf);

        lbRg = new JLabel("  Rg");
        lbRg.setLocation(190, 160);
        lbRg.setSize(50, 15);
        panelCliente.add(lbRg);

        tfRg = new JTextField();
        tfRg.setLocation(220, 157);
        tfRg.setSize(100, 30);
        panelCliente.add(tfRg);

        lbTelefone = new JLabel("  Tel");
        lbTelefone.setLocation(330, 160);
        lbTelefone.setSize(50, 15);
        panelCliente.add(lbTelefone);

        maskTelefone = new MaskFormatter("(##)####-####");
        maskTelefone.setPlaceholderCharacter('_');
        tfTelefone = new JFormattedTextField(maskTelefone);
        tfTelefone.setLocation(385, 157);
        tfTelefone.setSize(88, 20);
        panelCliente.add(tfTelefone);

        lbCelular = new JLabel("  Cel");
        lbCelular.setLocation(480, 160);
        lbCelular.setSize(30, 20);
        panelCliente.add(lbCelular);

        maskCelular = new MaskFormatter("(##)#-####-####");
        maskCelular.setPlaceholderCharacter('_');
        tfCelular = new JFormattedTextField(maskCelular);
        tfCelular.setLocation(505, 157);
        tfCelular.setSize(98, 30);
        panelCliente.add(tfCelular);

        lbEmail = new JLabel("  E-mail");
        lbEmail.setLocation(40, 190);
        lbEmail.setSize(50, 15);
        panelCliente.add(lbEmail);

        tfEmail = new JTextField();
        tfEmail.setLocation(80, 187);
        tfEmail.setSize(200, 30);
        panelCliente.add(tfEmail);

        lbEscolaridade = new JLabel("  Escolaridade");
        lbEscolaridade.setLocation(40, 220);
        lbEscolaridade.setSize(100, 15);
        panelCliente.add(lbEscolaridade);

        jcEscolaridade = new JComboBox<String>(escolaridade);
        jcEscolaridade.setLocation(120, 217);
        jcEscolaridade.setSize(105, 30);
        jcEscolaridade.setSelectedIndex(-1);
        panelCliente.add(jcEscolaridade);

		//-----
        painelTodos.add(panelCliente);

		//y = 240 ~ 250
        painelGeral = new JPanel();
        Border border2 = BorderFactory.createTitledBorder("Dados Gerais");
        painelGeral.setLayout(new GridLayout(9, 2));
        painelGeral.setBorder(border2);
        painelGeral.setBounds(10, 250, 390, 310);//TODO GERENCIANDO O LAYOUT

        lbProfissao = new JLabel("Profissão");
        lbProfissao.setLocation(240, 220);
        lbProfissao.setSize(100, 15);
        painelGeral.add(lbProfissao);

        tfProfissao = new JTextField();
        tfProfissao.setLocation(300, 217);
        tfProfissao.setSize(100, 20);
        painelGeral.add(tfProfissao);

        lbRegistroCnh = new JLabel("Nº Cnh");
        lbRegistroCnh.setLocation(40, 250);
        lbRegistroCnh.setSize(70, 15);
        painelGeral.add(lbRegistroCnh);

        tfRegistroCnh = new JTextField();
        tfRegistroCnh.setLocation(80, 247);
        tfRegistroCnh.setSize(100, 20);
        painelGeral.add(tfRegistroCnh);

        lbValidadeCnh = new JLabel("Validade");
        lbValidadeCnh.setLocation(190, 250);
        lbValidadeCnh.setSize(80, 15);
        painelGeral.add(lbValidadeCnh);

        maskValidadeCnh = new MaskFormatter("##/##/####");
        maskValidadeCnh.setPlaceholderCharacter('_');
        tfValidadeCnh = new JFormattedTextField(maskValidadeCnh);
        tfValidadeCnh.setLocation(245, 247);
        tfValidadeCnh.setSize(70, 20);
        painelGeral.add(tfValidadeCnh);

        lbPrimeiraHabilitacao = new JLabel("Dt Permissão");
        lbPrimeiraHabilitacao.setLocation(325, 250);
        lbPrimeiraHabilitacao.setSize(100, 15);
        painelGeral.add(lbPrimeiraHabilitacao);

        maskPrimeiraHabilitacao = new MaskFormatter("##/##/####");
        maskPrimeiraHabilitacao.setPlaceholderCharacter('_');
        tfPrimeiraHabilitacao = new JFormattedTextField(maskPrimeiraHabilitacao);
        tfPrimeiraHabilitacao.setLocation(410, 247);
        tfPrimeiraHabilitacao.setSize(70, 20);
        painelGeral.add(tfPrimeiraHabilitacao);

        // Op��es e Grupos de quest�es.
        lbQ1 = new JLabel("A quanto tempo nao dirige?");
        lbQ1.setLocation(40, 280);
        lbQ1.setSize(200, 20);
        painelGeral.add(lbQ1);

        tfQuestao1 = new JTextField();
        tfQuestao1.setLocation(200, 280);
        tfQuestao1.setSize(70, 20);
        painelGeral.add(tfQuestao1);

        lbQ2 = new JLabel("Tem veiculo proprio?");
        lbQ2.setLocation(40, 310);
        lbQ2.setSize(200, 20);
        painelGeral.add(lbQ2);

        JPanel painelQ2 = new JPanel();

        jrQ2Yes = new JRadioButton("Sim", true);
        jrQ2Yes.setLocation(170, 310);
        jrQ2Yes.setSize(60, 20);

        jrQ2No = new JRadioButton("Não", false);
        jrQ2No.setLocation(240, 310);
        jrQ2No.setSize(60, 20);

        gQ2 = new ButtonGroup();
        gQ2.add(jrQ2Yes);
        gQ2.add(jrQ2No);
        painelQ2.add(jrQ2Yes);
        painelQ2.add(jrQ2No);

        painelGeral.add(painelQ2);

        JPanel painelQ3 = new JPanel(); //Painel para colocar os radios parar alinhar os radios

        lbQ3 = new JLabel("É possivel treinar nele?");
        lbQ3.setLocation(40, 340);
        lbQ3.setSize(150, 20);
        painelGeral.add(lbQ3);

        jrQ3Yes = new JRadioButton("Sim", true);
        jrQ3Yes.setLocation(190, 340);
        jrQ3Yes.setSize(60, 20);

        jrQ3No = new JRadioButton("Não", false);
        jrQ3No.setLocation(260, 340);
        jrQ3No.setSize(60, 20);

        gQ3 = new ButtonGroup();
        gQ3.add(jrQ3Yes);
        gQ3.add(jrQ3No);
        painelQ3.add(jrQ3Yes);
        painelQ3.add(jrQ3No);

        painelGeral.add(painelQ3);

        lbQ4 = new JLabel("Como voc� soube da Karol Treinamentos?");
        lbQ4.setLocation(40, 370);
        lbQ4.setSize(250, 20);
        painelGeral.add(lbQ4);

        jcPesquisa = new JComboBox<String>(pesquisa);
        jcPesquisa.setLocation(290, 370);
        jcPesquisa.setSize(105, 20);
        jcPesquisa.setSelectedIndex(-1);
        painelGeral.add(jcPesquisa);

        lbObserva = new JLabel("Observações");
        lbObserva.setLocation(60, 400);
        lbObserva.setSize(120, 20);
        painelGeral.add(lbObserva);

        // Obs
        observa = new JTextArea();
        observa.setLineWrap(true);
        scroll = new JScrollPane(observa);
        scroll.setLocation(60, 430);
        scroll.setSize(300, 80);
        painelGeral.add(scroll);

        painelTodos.add(painelGeral);

        // Bot�o
        btSalvar = new JButton("Salvar");
        btSalvar.setLocation(10, 570);
        btSalvar.setSize(180, 35);
        painelTodos.add(btSalvar);

        btExcluir = new JButton("Excluir");
        btExcluir.setLocation(215, 570);
        btExcluir.setSize(180, 35);
        painelTodos.add(btExcluir);

        tfBuscaCliente = new JTextField();
        tfBuscaCliente.setBounds(430, 50, 250, 25);
        painelTodos.add(tfBuscaCliente);

        btBuscar = new JButton("Buscar");
        btBuscar.setLocation(690, 50);
        btBuscar.setSize(80, 25);
        painelTodos.add(btBuscar);
       
		// Text

        // Table
        lbAluno = new JLabel("Aluno");
        lbAluno.setSize(100, 20);
        lbAluno.setLocation(430, 30);
        painelTodos.add(lbAluno);
        
        lbPacote = new JLabel("Pacote");
        lbPacote.setSize(100, 20);
        lbPacote.setLocation(430, 300);
        painelTodos.add(lbPacote);
        
        table = new JTable(new ModeloTableCliente(listCliente));
        scTable = new JScrollPane(table);
        scTable.setSize(350, 200);
        scTable.setLocation(430, 80);
        painelTodos.add(scTable);
        
        tablePacote = new JTable(new ModeloTablePacote(listPacote));
        scrollPacote = new JScrollPane(tablePacote);
        scrollPacote.setSize(350, 150);
        scrollPacote.setLocation(430, 320);
        painelTodos.add(scrollPacote);
        
        //Dados do pagamento
        lbTipoPagamento = new JLabel("Tipo de pagamento");
        lbTipoPagamento.setSize(200, 20);
        lbTipoPagamento.setLocation(430, 480);
        painelTodos.add(lbTipoPagamento);
        
        jcPagamento = new JComboBox<String>(pagamento);
        jcPagamento.setLocation(650, 480);
        jcPagamento.setSize(120, 30);
        jcPagamento.setSelectedIndex(-1);
        painelTodos.add(jcPagamento);
        
        lbParcelas = new JLabel("Parcelas");
        lbParcelas.setSize(100,20);
        lbParcelas.setLocation(430, 510);
        painelTodos.add(lbParcelas);
        
        tfParcelas = new JTextField();
        tfParcelas.setSize(120, 30);
        tfParcelas.setLocation(650, 510);
        painelTodos.add(tfParcelas);
        
        lbPagamentoInicial = new JLabel("Valor Pago");
        lbPagamentoInicial.setSize(200, 20);
        lbPagamentoInicial.setLocation(430, 540);
        painelTodos.add(lbPagamentoInicial);
        
        tfPagamentoInicial = new JFormattedTextField();
        tfPagamentoInicial.setSize(120, 30);
        tfPagamentoInicial.setLocation(650, 540);
        painelTodos.add(tfPagamentoInicial);
        
        
        abas = new JTabbedPane();
		abas.setBounds(1, 1, 795, 635);
		abas.addTab("Cadastro", painelTodos);
		abas.addTab("Cliente/Pacote", painelBusca);
		add(abas);
        

        // PAINEL//
        getContentPane().setLayout(null);
        setSize(800, 670);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setClosable(true);
        setTitle("Cadastro Cliente");
        setResizable(false);
        setIconifiable(true);
        //getContentPane().setBackground(Color.lightGray);

    }

    public void definirEventos() {

        btSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // valida��es
                tfNome.setText(tfNome.getText().trim());
                tfData.setText(tfData.getText().trim());
                tfLogradouro.setText(tfLogradouro.getText().trim());
                tfNumero.setText(tfNumero.getText().trim());
                tfBairro.setText(tfBairro.getText().trim());
                tfCep.setText(tfCep.getText().trim());
                tfNascimento.setText(tfNascimento.getText().trim());
                tfBairro.setText(tfBairro.getText().trim());
                tfRg.setText(tfRg.getText().trim());
                tfEmail.setText(tfEmail.getText().trim());
                tfProfissao.setText(tfProfissao.getText().trim());
                tfQuestao1.setText(tfQuestao1.getText().trim());

                VerificadorDeCpf verificador = new VerificadorDeCpf();

                if (tfNome.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informar o nome");
                    lbNome.setForeground(Color.red);
                    tfNome.requestFocus(true);
                } else if (tfData.getValue() == null) {
                    JOptionPane.showMessageDialog(null, "Informar a data");
                    lbData.setForeground(Color.red);
                    tfData.requestFocus(true);
                } else if (tfLogradouro.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informar a rua");
                    lbLogradouro.setForeground(Color.red);
                    tfLogradouro.requestFocus(true);
                } else if (tfNumero.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informar o numero");
                    lbNumero.setForeground(Color.red);
                    tfNumero.requestFocus(true);
                } else if (tfCep.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informar o cep");
                    lbCep.setForeground(Color.red);
                    tfCep.requestFocus(true);
                } else if (tfNascimento.getValue() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Informar a data de nascimento");
                    lbNascimento.setForeground(Color.red);
                    tfNascimento.requestFocus(true);
                } else if (tfCep.getValue() == null) {
                    JOptionPane.showMessageDialog(null, "Informar o cep");
                    lbCep.setForeground(Color.red);
                    tfCep.requestFocus(true);
                } else if (jcSexo.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Informar o sexo");
                    jcSexo.requestFocus(true);
                } else if (tfCpf.getValue() == null
                        || !verificador.verificarCpf(tfCpf.getValue().toString())) {
                    JOptionPane.showMessageDialog(null, "Cpf invalido");
                    lbCpf.setForeground(Color.red);
                    tfCpf.requestFocus(true);
                } else if (tfRegistroCnh.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Informar o numero de registro da CNH");
                    lbRegistroCnh.setForeground(Color.red);
                    tfRegistroCnh.requestFocus(true);
                } else if (tfValidadeCnh.getValue() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Informar a data de validade da CNH");
                    lbValidadeCnh.setForeground(Color.red);
                    tfValidadeCnh.requestFocus(true);
                } else if (tfPrimeiraHabilitacao.getValue() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Informar a data da permi��o");
                    lbPrimeiraHabilitacao.setForeground(Color.red);
                    tfPrimeiraHabilitacao.requestFocus(true);
                } else if (jcPesquisa.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Selecionar uma das opções");
                    jcPesquisa.requestFocus(true);
                } else {

                    // populando os objetos
                    cliente.setNome(tfNome.getText());
                    cadastro.setData(tfData.getText());
                    endereco.setLogradouro(tfLogradouro.getText());

                    endereco.setNumero(Long.parseLong(tfNumero.getText()));
                    endereco.setBairro(tfBairro.getText());
                    endereco.setCep(tfCep.getText());
                    Date dt = new Date(tfNascimento.getText());
                    cliente.setNascimento(dt);
                    cliente.setSexo(jcSexo.getSelectedItem().toString());
                    cliente.setCpf(tfCpf.getText());
                    cliente.setRg(tfRg.getText());
                    cliente.setTelefone(tfTelefone.getText());
                    cliente.setCelular(tfCelular.getText());
                    cliente.setEmail(tfEmail.getText());
                    if (jcEscolaridade.getSelectedIndex() == -1) {
                        cliente.setEscolaridade("");
                    } else {
                        cliente.setEscolaridade(jcEscolaridade
                                .getSelectedItem().toString());
                    }
                    cliente.setProfissao(tfProfissao.getText());
                    cnh.setRegistroCnh(tfRegistroCnh.getText());
                    cnh.setDtValidade(tfValidadeCnh.getText());
                    cnh.setPrimeiraHabilitacao(tfPrimeiraHabilitacao.getText());
                    cadastro.setPesquisa1(tfQuestao1.getText());
                    if (jrQ2Yes.isSelected()) {
                        cadastro.setPesquisa2("sim");
                    } else {
                        cadastro.setPesquisa2("não");
                    }
                    if (jrQ3Yes.isSelected()) {
                        cadastro.setPesquisa3("sim");
                    } else {
                        cadastro.setPesquisa3("não");
                    }
                    cadastro.setPesquisa4(jcPesquisa.getSelectedItem()
                            .toString());
                    cadastro.setObservacao(observa.getText());

                    Repository<Cliente> repo = new Repository<Cliente>();
                    repo.adicionar(cliente);

                    //populaList();
                    limparCampos();

					// TODO n�o esquecer de aplicar o ternario para os
                    // FORMATEDTEXTFIELD
                    // limpar os campos ap�s o cadastro.
                }

            }
        });

        tfNome.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int caracteres = 50;
                if (tfNome.getText().length() >= caracteres
                        && e.getKeyCode() != 8) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                tfNome.setText(tfNome.getText().toUpperCase());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        tfLogradouro.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int caracteres = 50;
                if (tfLogradouro.getText().length() >= caracteres
                        && e.getKeyCode() != 8) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                tfLogradouro.setText(tfLogradouro.getText().toUpperCase());

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        tfNumero.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int caracteres = 8;
                if (tfNumero.getText().length() >= caracteres
                        && e.getKeyCode() != 8) {
                    e.consume();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        tfBairro.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                int caracteres = 30;
                if (tfBairro.getText().length() >= caracteres
                        && e.getKeyCode() != 8) {
                    e.consume();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                tfBairro.setText(tfBairro.getText().toUpperCase());

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        tfEmail.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                int caracter = 40;
                if (tfEmail.getText().length() >= caracter
                        && e.getKeyCode() != 8) {
                    e.consume();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                tfEmail.setText(tfEmail.getText().toLowerCase());

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        tfProfissao.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                int caracter = 25;
                if (tfProfissao.getText().length() >= caracter
                        && e.getKeyCode() != 8) {
                    e.consume();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                tfProfissao.setText(tfProfissao.getText().toUpperCase());

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent arg0) {
                Principal.isFrameInstrutorOpen = false;
            }
        });
        
       
    }
}
