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
			
			//System.out.println(lector.getClaves().size());
			
			Escritor escritor = new Escritor();
			ArrayList<Solicitud> listaPrioridad1 = lector.getSolicitudesPrioridad1();
			ArrayList<Solicitud> listaPrioridad2 = lector.getSolicitudesPrioridad2();
			ArrayList<Solicitud> listaPrioridad3 = lector.getSolicitudesPrioridad3();
			
			escritor.setSolicitudesIncompletas(lector.getSolicitudesIncompletas(), Constantes.ficheros[0]);
			
			cambios = new Hashtable<String, Integer>();
			
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
			
			FechasOrdenadas fechasOrdP1 = new FechasOrdenadas(sCorrectasP1, castSemanasSplit);
			FechasOrdenadas fechasOrdP2 = new FechasOrdenadas(sCorrectasP2, castSemanasSplit);
			FechasOrdenadas fechasOrdP3 = new FechasOrdenadas(sCorrectasP3, castSemanasSplit);
	
			solicitudesCorrectasPrioridad1 = eliminarSolicitudesRepetidas(fechasOrdP1.list());
			solicitudesCorrectasPrioridad2 = eliminarSolicitudesRepetidas(fechasOrdP2.list());
			solicitudesCorrectasPrioridad3 = eliminarSolicitudesRepetidas(fechasOrdP3.list());

			escritor.setSolicitudesRepetidas(solicitudesCorrectasPrioridad1.getRight(), solicitudesCorrectasPrioridad2.getRight(),solicitudesCorrectasPrioridad3.getRight(),Constantes.ficheros[2]);
			
			int escritorCodigotxt = escritor.escribir();
			if(escritorCodigotxt==Constantes.ERR_5){
				return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false,""), new Pair<Boolean, String>(false,""),Constantes.ERR_5);
			}
			
			numSolicitudes = solicitudesCorrectasPrioridad1.getLeft().size() + solicitudesCorrectasPrioridad2.getLeft().size() +solicitudesCorrectasPrioridad3.getLeft().size();
			Reparte p = new Reparte(solicitudesCorrectasPrioridad1.getLeft(),solicitudesCorrectasPrioridad2.getLeft(),solicitudesCorrectasPrioridad3.getLeft(), t, castHorasDouble, castSemanasSplit);
			
			int reparteCodigo = p.start();

			if(reparteCodigo==Constantes.ERR_3){
				return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false,""), new Pair<Boolean, String>(false,""),Constantes.ERR_3);
			}
			
			Hashtable<String, Solicitud> tablaSalida = p.salida();
			
			
			//System.out.println(tablaSalida);
			
			
			numEncuestasSinAsignar = 0;
			
			Hashtable<String,Triplet<Solicitud, Solicitud, Solicitud>> todasEncuestas = lector.getEncuestas();
			//Hashtable<String, Boolean> t = lector.getClaves();
			String salidaError = "";
			for (String key : t.keySet()) {
				if(t.get(key)){
					Solicitud aux = todasEncuestas.get(key).getLeft();
					numEncuestasSinAsignar++;
					salidaError = salidaError + "Linea = " + aux.getNumLinea() + " Profesor = " + aux.getNombre() + " Sin asignar\n";
				}	    
			}
			
			textSalidaError = salidaError;
			
			numEncuestasAsignadas = tablaSalida.size();
			
			FechasOrdenadas f2 = new FechasOrdenadas(tablaSalida, castSemanasSplit);
			
			Pair<ArrayList<Solicitud>,Integer> prueba = crearCalendarioPorHoras(f2.list());
			
			if(prueba.getRight()==Constantes.ERR_4){
				return new Triplet<Pair<Boolean, String>, Pair<Boolean, String>,Integer>(new Pair<Boolean, String>(false,""), new Pair<Boolean, String>(false,""),Constantes.ERR_4);
			}
			
			//EscritorExcel excel = new EscritorExcel(prueba, castHorasDouble, castSemanasSplit,ficheroSalida);
			escritor.EscritorExcel(prueba.getLeft(), castHorasDouble, castSemanasSplit,ficheroSalida);
			String salida = "";
			for (Solicitud s : f2.list()) {
				salida = salida + s.toString() + "\n";
			}

			//System.out.println(salida);				//Muestra las encuestas asignadas

			txtSalida = salida;
			
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
	
	private Pair<ArrayList<Solicitud>,ArrayList<Solicitud>> eliminarSolicitudesRepetidas(ArrayList<Solicitud> fechas){
		ArrayList<Solicitud> salida = new ArrayList<Solicitud>();
		ArrayList<Solicitud> salidaRep = new ArrayList<Solicitud>();
		Solicitud der;
		
		for(int i = 0; i < fechas.size(); i++){
			try{
				der = fechas.get(i+1);
			}catch(Exception e){
				der = null;
			}
			if(!solicitudesIguales(fechas.get(i),der)){
				salida.add(fechas.get(i));
			}else{
				salidaRep.add(fechas.get(i));
			}
		}
		
		return new Pair<ArrayList<Solicitud>,ArrayList<Solicitud>>(salida,salidaRep);
	}

	private Boolean solicitudesIguales(Solicitud s1, Solicitud s2){
		if(s2 == null){
			return false;
		}else{
			return 	s1.getNombre().equalsIgnoreCase(s2.getNombre()) &&
					s1.getAsignatura().equalsIgnoreCase(s2.getAsignatura()) &&
					s1.getGrupo().equalsIgnoreCase(s2.getGrupo()) &&
					s1.getCarrera().equalsIgnoreCase(s2.getCarrera()) &&
					s1.getAula().equalsIgnoreCase(s2.getAula()) &&
					s1.getDia() == s2.getDia() &&
					s1.getMes() == s2.getMes() &&
					s1.getHora() == s2.getHora();
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
	
	@SuppressWarnings("unused")
	private ArrayList<ArrayList<Solicitud>> crearCalendario(ArrayList<Solicitud> f){
		ArrayList<ArrayList<Solicitud>> salida = new ArrayList<ArrayList<Solicitud>>();
		if(!f.isEmpty()){
			ArrayList<Solicitud> fechas = f;
			ArrayList<Solicitud> aux = new ArrayList<Solicitud>();
			aux.add(fechas.get(0));
			fechas.remove(0);
			while(!fechas.isEmpty()){
				if(fechas.get(0).getDia() == aux.get(0).getDia()){
					aux.add(fechas.get(0));
					fechas.remove(0);
				}else{
					salida.add(aux);
					aux = new ArrayList<Solicitud>();
					aux.add(fechas.get(0));
					fechas.remove(0);
				}
			}
			salida.add(aux);	
		}
		return salida;
	}
	
	private Pair<ArrayList<Solicitud>,Integer> crearCalendarioPorHoras(ArrayList<Solicitud> f){
		ArrayList<ArrayList<Solicitud>> salida = crearCalendarioPorHorasAux(f);
		ArrayList<Solicitud> salidaCorrecta = new ArrayList<Solicitud>();
		
		try{
			for(int i = 0; i < salida.size(); i++){
				if(!salida.get(i).isEmpty()){
					//System.out.println("HORA = " + salida.get(i).get(0).getHora());
					for(int j = 0; j < salida.get(i).size(); j++){
						salidaCorrecta.add(salida.get(i).get(j));
						//System.out.println(salida.get(i).get(j).getDia());
					}	
				}
			}
			
			return new Pair<ArrayList<Solicitud>,Integer>(salidaCorrecta,0);
		}catch(Exception e){
			return new Pair<ArrayList<Solicitud>,Integer>(salidaCorrecta,-4);
		}
		
	}
	
	private ArrayList<ArrayList<Solicitud>> crearCalendarioPorHorasAux(ArrayList<Solicitud> f){
		ArrayList<ArrayList<Solicitud>> salida = new ArrayList<ArrayList<Solicitud>>();
		for(int i = 0; i < castHorasDouble.length; i++){
			salida.add(i, new ArrayList<Solicitud>());
		}
		
		Boolean ok;
		while(!f.isEmpty()){
			ok = false;
			for(int i = 0; i < salida.size() && !ok; i++){
				if(f.get(0).getHora() == castHorasDouble[i]){
					salida.get(i).add(f.get(0));
					f.remove(0);
					ok = true;
					//System.out.println(f.size());
				}
			}
		}	
		
		
		return salida;
	}
}
