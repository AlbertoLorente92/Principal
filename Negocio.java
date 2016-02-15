package Principal;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class Negocio {
	private double[] castHorasDouble;
	private ArrayList<Pair<Integer,Integer>> castSemanasSplit;
	private String fichero;
	private String ficheroSalida;
	private Boolean boolHoras, boolDias;
	private int numEncuestasAsignadas,numSolicitudes,numEncuestasSinAsignar;
	private Hashtable<String, Integer> cambios;

	public Negocio() {
		ficheroSalida = "HorarioEncuestas.xlsx";
		boolHoras = boolDias = false;
		cambios = new Hashtable<String, Integer>();
	}
	
	/**
	 * Carga las horas para asignar las encuestas
	 * @param horas
	 * @return true,true => horas cargadas con éxito y se han completado los pasos necesarios para asignar encuestas
	 * @return true,false => horas cargadas con éxito pero aun NO se pueden asignar encuestas
	 * @return false,false => formato de horas INCORRECTO y NO pueden asignarse encuestas
	 */
	public Pair<Boolean, Boolean> cargarHorasEncuestas(String horas) {
		try {
			Constantes.TEXTO_JTXT_HORAS = horas;
			horas = horas.replace("\n", "").replace("\r", "");
			String[] horasSplit = horas.split(",");
			int numHoras = horasSplit.length;
			castHorasDouble = new double[numHoras];
			for (int j = 0; j < numHoras; j++) {
				horasSplit[j] = horasSplit[j].replaceAll(":", ".").replaceAll("\t", "").replaceAll("\n", "")
						.replaceAll(" ", ",");
				castHorasDouble[j] = Double.parseDouble(horasSplit[j]);
			}
			boolHoras = true;
			if (check()) {
				return new Pair<Boolean, Boolean>(true, true);
			}

			return new Pair<Boolean, Boolean>(true, false);
		} catch (Exception e) {
			boolHoras = false;
			this.fichero = null;
			return new Pair<Boolean, Boolean>(false, false);
		}
	}
	
	/**
	 * Carga los días para asignar las encuestas
	 * @param dias
	 * @return true,true => días cargados con éxito y se han completado los pasos necesarios para asignar encuestas
	 * @return true,false => días cargados con éxito pero aun NO se pueden asignar encuestas
	 * @return false,false => formato de días INCORRECTO y NO pueden asignarse encuestas
	 */
	public Pair<Boolean, Boolean> cargarDiasEncuestas(String dias) {
		try {
			Constantes.TEXTO_LBL_DIAS = dias;
			dias = dias.replace("\n", "").replace("\r", "");
			String[] semanasSplit1 = dias.split(";");
			int numSemanas = semanasSplit1.length;

			String[][] semanasSplit2 = new String[numSemanas][5];

			for (int i = 0; i < numSemanas; i++) {
				semanasSplit2[i] = semanasSplit1[i].split(",");
			}
			String[] aux;
			castSemanasSplit = new ArrayList<Pair<Integer,Integer>>();
			for (int i = 0; i < semanasSplit2.length; i++) {
				for (int j = 0; j < semanasSplit2[i].length; j++) {
					aux = semanasSplit2[i][j].split("/");			
					aux[0] = aux[0].replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n","");
					aux[1] = aux[1].replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n","");
					castSemanasSplit.add(new Pair<Integer,Integer>(Integer.parseInt(aux[0]),Integer.parseInt(aux[1])));
				}
			}
			boolDias = true;
			if (check()) {
				return new Pair<Boolean, Boolean>(true, true);
			}

			return new Pair<Boolean, Boolean>(true, false);
		} catch (Exception e) {
			boolDias = false;
			this.fichero = null;
			return new Pair<Boolean, Boolean>(false, false);
		}
	}

	/**
	 * Una vez cargadas las horas y los días pueden asignarse las encuestas
	 * 0 => Salida correcta sin fallos
	 * -1 => Fichero NO encontrado
	 * -2 => Error en la lectura del fichero
	 * -3 => Error en la reparticion
	 * -4 => Error al crear el calendario
	 * -10 => error sin especificar
	 */
	public Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer> asignaEncuestas(String fich) {
		try {
			try {
				this.fichero = fich;
				FileInputStream file = new FileInputStream(new File(this.fichero));
				file.close();		
			} catch (Exception e) {
				return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false,""), new Pair<Boolean, String>(false,""),Constantes.ERR_1);
			}
			
			String textSalidaError = "";
			String txtSalida = "";
			Lector lector = new Lector(this.fichero);
			
			int lectorCodigo = lector.lee();
			if(lectorCodigo==Constantes.ERR_2){
				return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false,""), new Pair<Boolean, String>(false,""),Constantes.ERR_2);
			}
						
			Escritor escritor = new Escritor();
			ArrayList<Solicitud> listaPrioridad1 = lector.getSolicitudesPrioridad1();
			ArrayList<Solicitud> listaPrioridad2 = lector.getSolicitudesPrioridad2();
			ArrayList<Solicitud> listaPrioridad3 = lector.getSolicitudesPrioridad3();
			
			escritor.setSolicitudesIncompletas(lector.getSolicitudesIncompletas(), Constantes.ficheros[0]);
			
			
			
			Pair<ArrayList<Solicitud>,ArrayList<Solicitud>> solicitudesCorrectasPrioridad1 = eliminarSolicitudesIncorrectas(listaPrioridad1);
			Pair<ArrayList<Solicitud>,ArrayList<Solicitud>> solicitudesCorrectasPrioridad2 = eliminarSolicitudesIncorrectas(listaPrioridad2);	
			Pair<ArrayList<Solicitud>,ArrayList<Solicitud>> solicitudesCorrectasPrioridad3 = eliminarSolicitudesIncorrectas(listaPrioridad3);	
			
			Hashtable<String, Boolean> t = lector.getClaves();	
			for (String key : cambios.keySet()) {
				if(cambios.get(key)==3){
					t.remove(key);					
				}	    
			}

			escritor.setSolicitudesIncorrectas(solicitudesCorrectasPrioridad1.getRight(), solicitudesCorrectasPrioridad2.getRight(),solicitudesCorrectasPrioridad3.getRight(),Constantes.ficheros[1]);
					
			ArrayList<Solicitud> sCorrectasP1 = solicitudesCorrectasPrioridad1.getLeft();
			ArrayList<Solicitud> sCorrectasP2 = solicitudesCorrectasPrioridad2.getLeft();
			ArrayList<Solicitud> sCorrectasP3 = solicitudesCorrectasPrioridad3.getLeft();
			
			Reparte p = new Reparte(sCorrectasP1,sCorrectasP2,sCorrectasP3, t, castHorasDouble, castSemanasSplit);
			
			int reparteCodigo = p.start();
			if(reparteCodigo==Constantes.ERR_3){
				return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false,""), new Pair<Boolean, String>(false,""),Constantes.ERR_3);
			}
			
			this.numSolicitudes = sCorrectasP1.size() + sCorrectasP2.size() + sCorrectasP3.size() - p.getSolicitudesRepetidas().size();
			this.numEncuestasAsignadas = p.getNumEncuestasAsignadas();		
			this.numEncuestasSinAsignar = 0;
			String salidaError = "";			
			Hashtable<String, Boolean> sinAsignar = p.getAsignada();
			Hashtable<String,Triplet<Solicitud, Solicitud, Solicitud>> todasEncuestas = lector.getEncuestas();
			for (String key : sinAsignar.keySet()) {
				if(sinAsignar.get(key)){
					this.numEncuestasSinAsignar++;
					Solicitud aux = todasEncuestas.get(key).getLeft();
					salidaError = salidaError + "Linea = " + aux.getNumLinea() + " Profesor = " + aux.getNombre() + " Asignatura = " + aux.getAsignatura() + " Grupo = " + aux.getGrupo() + " Sin asignar\n";
				}
			}
			textSalidaError = salidaError;
			
			String salida = "";
			for(Solicitud sol: p.getSolicitudesAsignadas()){
				salida = salida + sol.toString() + "\n";
			}
			txtSalida = salida;
						
			escritor.escribir();
			escritor.EscritorExcel(p.getTablaSalidaExcel(), castHorasDouble, castSemanasSplit,ficheroSalida);			
			
			return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(true, txtSalida),
					new Pair<Boolean, String>(this.numEncuestasSinAsignar!=0, textSalidaError),0);

		} catch (Exception e) {
			return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false, ""),
					new Pair<Boolean, String>(false, ""),Constantes.ERR_10);
		}
	}


	public int getNumEncuestasAsignadas() {
		return numEncuestasAsignadas;
	}

	public int getNumSolicitudes() {
		return numSolicitudes;
	}

	public int getNumEncuestasSinAsignar() {
		return numEncuestasSinAsignar;
	}

	private Boolean check() {
		if (boolHoras && boolDias) {
			return true;
		} else {
			return false;
		}
	}
	
	private Pair<ArrayList<Solicitud>,ArrayList<Solicitud>> eliminarSolicitudesIncorrectas(ArrayList<Solicitud> fechas){
		ArrayList<Solicitud> salida = new ArrayList<Solicitud>();
		ArrayList<Solicitud> salidaInc = new ArrayList<Solicitud>();
		
		for(int i = 0; i < fechas.size(); i++){
			if(esCorrecta(fechas.get(i))){
				salida.add(fechas.get(i));
			}else{
				salidaInc.add(fechas.get(i));

				if(cambios.containsKey(fechas.get(i).clave())){
					int aux = cambios.get(fechas.get(i).clave());
					aux++;
					cambios.put(fechas.get(i).clave(),aux);
				}else{
					cambios.put(fechas.get(i).clave(),1);
				}
			}
		}
		
	
		return new Pair<ArrayList<Solicitud>,ArrayList<Solicitud>>(salida,salidaInc);
	}
	
	private Boolean esCorrecta(Solicitud s){
		int dia = s.getDia();
		double hora = s.getHora();
		int mes = s.getMes();
		Boolean existeHora = false, existeDia = false;
		Pair<Integer,Integer> diaYMes = new Pair<Integer,Integer>(dia,mes);
		
		for(int i = 0; i < castSemanasSplit.size() && !existeDia; i++){
			if(castSemanasSplit.get(i).getLeft() == diaYMes.getLeft() && castSemanasSplit.get(i).getRight() == diaYMes.getRight()){
				existeDia = true;
			}
		}
		
		if(existeDia){
			for(int i = 0; i < castHorasDouble.length && !existeHora; i++){
				if(castHorasDouble[i]==hora){
					existeHora = true;
				}
			}
			
			return existeHora;
		}else{
			return false;
		}
	}
	
}
