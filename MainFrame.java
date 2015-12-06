package Principal;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Negocio negocio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		negocio = new Negocio();
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));

		this.setResizable(false);
		this.setTitle(Constantes.TEXTO_VENTANA);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 723, 605);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);

		JLabel lblDias = new JLabel(Constantes.TEXTO_LBL_DIAS);
		lblDias.setBounds(36, 98, 217, 14);
		this.add(lblDias);

		// Poniendo todo el texto en negrita
		Font font = lblDias.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		lblDias.setFont(boldFont);

		JLabel lblHoras = new JLabel(Constantes.TEXTO_LBL_HORAS);
		lblHoras.setBounds(480, 98, 186, 14);
		lblHoras.setFont(boldFont);
		this.add(lblHoras);

		JLabel lblNombreDelFichero = new JLabel(Constantes.TEXTO_LBL_NOMBRE_FICHERO);
		lblNombreDelFichero.setBounds(272, 160, 160, 14);
		this.add(lblNombreDelFichero);

		JLabel lblSalidaDeErrores = new JLabel(Constantes.TEXTO_LBL_SALIDA_ERROR);
		lblSalidaDeErrores.setBounds(215, 478, 217, 14);
		lblSalidaDeErrores.setFont(boldFont);
		this.add(lblSalidaDeErrores);

		JLabel lblNewLabel = new JLabel(Constantes.TEXTO_LBL_AUTOR);
		lblNewLabel.setBounds(500, 552, 160, 25);
		lblNewLabel.setFont(boldFont);
		this.add(lblNewLabel);

		JLabel lblImg = new JLabel();
		lblImg.setBounds(0, 0, 717, 87);
		this.add(lblImg);

		ImageIcon imgThisImg = new ImageIcon("encabezado.jpg");
		lblImg.setIcon(imgThisImg);
		//System.out.println(this.getClass().getResource("encabezado.jpg"));
		//Image img = t.getImage("encabezado.jpg");
		//System.out.println("imagen expl " + img);
		//System.out.println(this.getGraphics());
		//this.getGraphics().drawImage(img,0, 0, 717, 87, this);

		JTextArea txtDias = new JTextArea();
		txtDias.setText(Constantes.TEXTO_JTXT_DIAS);
		JScrollPane spaneDias = new JScrollPane(txtDias);
		spaneDias.setBounds(36, 123, 200, 186);
		spaneDias.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(spaneDias);

		JTextArea txtHoras = new JTextArea();
		txtHoras.setText(Constantes.TEXTO_JTXT_HORAS);
		JScrollPane spaneHoras = new JScrollPane(txtHoras);
		spaneHoras.setBounds(480, 123, 200, 186);
		spaneHoras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(spaneHoras);

		JTextArea txtFichero = new JTextArea();
		txtFichero.setText(Constantes.TEXTO_JTXT_FICHERO);
		txtFichero.setBounds(272, 185, 160, 23);
		this.add(txtFichero);

		JTextArea txtSalida = new JTextArea();
		txtSalida.setEditable(false);
		txtSalida.setColumns(10);
		JScrollPane spaneSalida = new JScrollPane(txtSalida);
		spaneSalida.setBounds(36, 370, 630, 97);
		spaneSalida.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(spaneSalida);

		JTextArea textSalidaError = new JTextArea();
		textSalidaError.setEditable(false);
		textSalidaError.setColumns(10);
		JScrollPane spaneSalidaError = new JScrollPane(textSalidaError);
		spaneSalidaError.setBounds(36, 494, 630, 49);
		spaneSalidaError.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(spaneSalidaError);

		JButton btnCargarFechas = new JButton(Constantes.TEXTO_BTN_DIAS);
		btnCargarFechas.setBounds(36, 336, 160, 23);
		btnCargarFechas.setFont(boldFont);
		this.add(btnCargarFechas);

		JButton btnCargarHoras = new JButton(Constantes.TEXTO_BTN_HORAS);
		btnCargarHoras.setBounds(480, 336, 160, 23);
		btnCargarHoras.setFont(boldFont);
		this.add(btnCargarHoras);

		JButton btnCargarFichero = new JButton(Constantes.TEXTO_BTN_CARGAR_Y_ASIGNAR);
		btnCargarFichero.setBounds(272, 225, 160, 80);
		btnCargarFichero.setFont(boldFont);
		btnCargarFichero.setEnabled(false);
		this.add(btnCargarFichero);

		btnCargarHoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pair<Boolean, Boolean> pair = negocio.cargarHorasEncuestas(txtHoras.getText());
				if (pair.getLeft()) {
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_JOPTIONPANE_HORAS_CORRECTAS);
					if (pair.getRight()) {
						btnCargarFichero.setEnabled(true);
					}
				} else {
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_JOPTIONPANE_HORAS_INCORRECTAS);
				}
			}
		});

		btnCargarFechas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pair<Boolean, Boolean> pair = negocio.cargarDiasEncuestas(txtDias.getText());
				if (pair.getLeft()) {
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_JOPTIONPANE_DIAS_CORRECTAS);
					if (pair.getRight()) {
						btnCargarFichero.setEnabled(true);
					}
				} else {
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_JOPTIONPANE_DIAS_INCORRECTAS);
					;
				}
			}
		});

		btnCargarFichero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer> triplet = negocio.asignaEncuestas(txtFichero.getText());

				if(triplet.getRight()==Constantes.ERR_1){
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_SALIDA_ERROR_1);
				}else if(triplet.getRight()==Constantes.ERR_2){
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_SALIDA_ERROR_2);
				}else if(triplet.getRight()==Constantes.ERR_3){
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_SALIDA_ERROR_3);
				}else if(triplet.getRight()==Constantes.ERR_4){
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_SALIDA_ERROR_4);
				}else if(triplet.getRight()==Constantes.ERR_10){
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_SALIDA_ERROR_10);
				}else if (triplet.getLeft().getLeft()) {
					txtSalida.setText(triplet.getLeft().getRight());
					if (triplet.getMidle().getLeft()) {
						textSalidaError.setText(triplet.getMidle().getRight());
					}

					JOptionPane.showMessageDialog(getContentPane(),
							"Encuestas asignadas." + "\nNúmero solicitudes: " + negocio.getNumSolicitudes()
									+ "\nNúmero encuestas asignadas: " + negocio.getNumEncuestasAsignadas()
									+ "\nNúmero encuestas sin asignar: " + negocio.getNumEncuestasSinAsignar());
				} else {
					JOptionPane.showMessageDialog(getContentPane(), Constantes.TEXTO_SALIDA_ERROR_10);
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics g)
	{
		// TODO Auto-generated method stub
		super.paint(g);
	}
}
