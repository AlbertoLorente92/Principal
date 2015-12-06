package Principal;

import java.util.ArrayList;
import java.util.Hashtable;

public class Reparte {
	private ArrayList<Solicitud> fechasP1;
	private ArrayList<Solicitud> fechasP2;
	private ArrayList<Solicitud> fechasP3;
	private ArrayList<Solicitud> solicitudesRepetidas,solicitudesSinAsignar,solicitudesAsignadas;
	private Solicitud[][] tablaSalidaExcel;
	private int numEncuestasAsignadas;
	private Hashtable<String, Boolean> asignada;

	private double[] castHorasDouble;
	private ArrayList<Pair<Integer,Integer>> castSemanasSplit;

	
	public Reparte(ArrayList<Solicitud> fP1, ArrayList<Solicitud> fP2, ArrayList<Solicitud> fP3,Hashtable<String,Boolean> t,
			double[] castHorasDouble,ArrayList<Pair<Integer,Integer>> castSemanasSplit){
		this.castSemanasSplit = castSemanasSplit;
		this.castHorasDouble = castHorasDouble;
		this.fechasP1 = fP1;
		this.fechasP2 = fP2;
		this.fechasP3 = fP3;
		this.asignada = t;
		this.solicitudesRepetidas = new ArrayList<Solicitud>();
		this.solicitudesSinAsignar = new ArrayList<Solicitud>();
		this.solicitudesAsignadas = new ArrayList<Solicitud>();
		this.numEncuestasAsignadas = 0;
		this.tablaSalidaExcel = new Solicitud[castHorasDouble.length][castSemanasSplit.size()];				
	}

	public int start(){
		try{
			recorrerLista(fechasP1);			
			recorrerLista(fechasP2);
			recorrerLista(fechasP3);

			return 0;
		} catch(Exception e){
			return -3;
		}
	}
	
	public Hashtable<String, Boolean> getAsignada() {
		return asignada;
	}

	private void recorrerLista(ArrayList<Solicitud> fechas){
		for(int i = 0; i < fechas.size(); i++){
			Solicitud aux =  fechas.get(i);
			if(asignada.get(aux.clave())){
				Pair<Integer,Integer> hash = hashFranjaHorariaDoble(aux);
				if(tablaSalidaExcel[hash.getLeft()][hash.getRight()] == null){
					tablaSalidaExcel[hash.getLeft()][hash.getRight()] = aux;
					numEncuestasAsignadas++;
					solicitudesAsignadas.add(aux);
					asignada.replace(aux.clave(),false);
				}else{
					if(aux.esIgualClave(tablaSalidaExcel[hash.getLeft()][hash.getRight()])){
						solicitudesRepetidas.add(aux);
					} else {
						solicitudesSinAsignar.add(aux);
					}
				}
			}
		}
	}

	private Pair<Integer,Integer> hashFranjaHorariaDoble(Solicitud s){
		int dia = s.getDia();
		double hora = s.getHora();
		int posDia = 0,posHora = 0;
		Boolean fin = false;
		for(int i = 0; i < castHorasDouble.length && !fin; i++){
			if(hora == castHorasDouble[i]){
				fin = true;
				posHora = i;
			}
		}
		
		fin = false;
		for(int i = 0; i < castSemanasSplit.size() && !fin; i++){
			if(dia == castSemanasSplit.get(i).getLeft()){
				fin = true;
				posDia = i;
			}
		}
			
		return new Pair<Integer,Integer>(posHora,posDia);
	}
	
	public ArrayList<Solicitud> getSolicitudesRepetidas() {
		return solicitudesRepetidas;
	}
	
	public ArrayList<Solicitud> getSolicitudesAsignadas() {
		return new ArrayList<Solicitud>(solicitudesAsignadas);
	}
	
	public ArrayList<Solicitud> getSolicitudesSinAsignar() {
		return new ArrayList<Solicitud>(solicitudesSinAsignar);
	}

	public Solicitud[][] getTablaSalidaExcel() {
		return tablaSalidaExcel;
	}
		
	public int getNumEncuestasAsignadas() {
		return numEncuestasAsignadas;
	}
}
