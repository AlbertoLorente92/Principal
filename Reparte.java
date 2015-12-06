package Principal;

import java.util.ArrayList;
import java.util.Hashtable;

public class Reparte {
	private ArrayList<Solicitud> fechasP1;
	private ArrayList<Solicitud> fechasP2;
	private ArrayList<Solicitud> fechasP3;
	private Hashtable<String,Boolean> tabla;
	
	private Boolean[] franjasOcupadas;
	private double[] castHorasDouble;
	private ArrayList<Pair<Integer,Integer>> castSemanasSplit;
	
	private Hashtable<String,Solicitud> tablaSalida = new Hashtable<String,Solicitud>();
	
	public Reparte(ArrayList<Solicitud> fP1, ArrayList<Solicitud> fP2, ArrayList<Solicitud> fP3,Hashtable<String,Boolean> t,
			double[] castHorasDouble,ArrayList<Pair<Integer,Integer>> castSemanasSplit){
		this.castSemanasSplit = castSemanasSplit;
		this.castHorasDouble = castHorasDouble;
		fechasP1 = fP1;
		fechasP2 = fP2;
		fechasP3 = fP3;
		tabla = t;

		franjasOcupadas = new Boolean[castHorasDouble.length*castSemanasSplit.size()];
		
		for(int i = 0; i < franjasOcupadas.length; i++){
			franjasOcupadas[i] = false;
		}
		
	}
	
	public int start(){
		try{
			recorrerLista(fechasP1);			
			recorrerLista(fechasP2);
			recorrerLista(fechasP3);
			/*for (String key : tabla.keySet()) {
			    System.out.println(key + ":" + tabla.get(key));
			}
			
			System.out.println("__________________________________________________________");*/
			return 0;
		} catch(Exception e){
			return -3;
		}
	}
	
	private void recorrerLista(ArrayList<Solicitud> fechas){
		int modificaciones = 0;
		Solicitud actual, elegida;
		ArrayList<Solicitud> aux;
		Boolean stop;
		
		while(!fechas.isEmpty() && modificaciones==0){
			modificaciones = 1;
			for(int i = 0; i < fechas.size(); i++){			
				actual = fechas.get(i);
				if(!tabla.get(actual.clave()) || franjasOcupadas[hashFranjaHoraria(actual)]){
					fechas.remove(i);
					modificaciones = 0;
					i--;
				}else{	
					
					aux = new ArrayList<Solicitud>();
					aux.add(actual);
					stop = false;
					for(int j = i+1; j < fechas.size() && !stop;j++){
						if(actual.esIgualQue(fechas.get(j))){
							aux.add(fechas.get(j));
						}else{
							stop = true;
						}
					}
								
					
					elegida = new Solicitud();
					if(aux.size()==1){
						elegida = aux.get(0);
					}else{
						int ordenDeRespuesta = Integer.MAX_VALUE;
						for(int j = 0; j < aux.size();j++){
							if(aux.get(j).getNumLinea() < ordenDeRespuesta){
								elegida = aux.get(j);
								ordenDeRespuesta = elegida.getNumLinea();
							}
						}		
					}

					tabla.put(elegida.clave(), false);
					tablaSalida.put(elegida.clave(),elegida);
					franjasOcupadas[hashFranjaHoraria(elegida)] = true;
					
					//System.out.println("SOLICITUD " + elegida.getNumOpcion() + " LINEA " + elegida.getNumLinea());
					
					for(int x = i; x < aux.size(); x++){
						fechas.remove(i);
					}
					
					modificaciones = 0;
					i--;
					
				}
			}
		}
	}
	
	private int hashFranjaHoraria(Solicitud s){
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
		
		if(posHora==0)
			return posDia*castHorasDouble.length;
		if(posDia==0)
			return posHora;
		
		return (posDia*castHorasDouble.length)+posHora;
	}

	public ArrayList<Solicitud> getFechasP1() {
		return new ArrayList<Solicitud>(fechasP1);
	}
	
	public ArrayList<Solicitud> getFechasP2() {
		return new ArrayList<Solicitud>(fechasP2);
	}
	
	public ArrayList<Solicitud> getFechasP3() {
		return new ArrayList<Solicitud>(fechasP3);
	}

	public Hashtable<String,Solicitud> salida(){
		return new Hashtable<String,Solicitud>(tablaSalida);
	}
}
