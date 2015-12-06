package Principal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

public class FechasOrdenadas {
	private ArrayList<Solicitud> solicitudesOrdenadas = new ArrayList<Solicitud>();
	private ArrayList<Pair<Integer,Integer>> listadias;

	public FechasOrdenadas(ArrayList<Solicitud> s, ArrayList<Pair<Integer,Integer>> list) {
		if (list.size() != 0) {
			listadias = list;

			solicitudesOrdenadas.add(s.get(0));
			boolean ok;
			for (int i = 1; i < s.size(); i++) {
				ok = false;
				for (int j = 0; j < solicitudesOrdenadas.size() && !ok; j++) {
					if (!esAnterior(s.get(i), solicitudesOrdenadas.get(j))) {
						solicitudesOrdenadas.add(j, s.get(i));
						ok = true;
					}

					if (j == solicitudesOrdenadas.size() - 1) {
						solicitudesOrdenadas.add(solicitudesOrdenadas.size(), s.get(i));
						ok = true;
					}
				}
			}
		}
	}

	public FechasOrdenadas(Hashtable<String, Solicitud> h, ArrayList<Pair<Integer,Integer>> list) {
		if (list.size() != 0) {
			listadias = list;
			ArrayList<Solicitud> s = new ArrayList<Solicitud>();

			Set<Entry<String, Solicitud>> entrySet = h.entrySet();
			java.util.Iterator<Entry<String, Solicitud>> it = entrySet.iterator();

			while (it.hasNext()) {
				s.add(it.next().getValue());
			}

			solicitudesOrdenadas.add(s.get(0));
			boolean ok;
			for (int i = 1; i < s.size(); i++) {
				ok = false;
				for (int j = 0; j < solicitudesOrdenadas.size() && !ok; j++) {
					if (!esAnterior(s.get(i), solicitudesOrdenadas.get(j))) {
						solicitudesOrdenadas.add(j, s.get(i));
						ok = true;
					}

					if (j == solicitudesOrdenadas.size() - 1) {
						solicitudesOrdenadas.add(solicitudesOrdenadas.size(), s.get(i));
						ok = true;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return 0 = false, 1 = true, 3 =  
	 */
	public boolean esAnterior(Solicitud s1, Solicitud s2) {
		int mes1 =  s1.getMes();
		int mes2 = s2.getMes();
		int dia1 = s1.getDia();
		double hora1 = s1.getHora();
		int dia2 = s2.getDia();
		double hora2 = s2.getHora();
		int pos1 = 0, pos2 = 0;
		for (int i = 0; i < listadias.size(); i++) {
			if (listadias.get(i).getLeft() == dia1 && listadias.get(i).getRight() == mes1) {
				pos1 = i;
			}
			if (listadias.get(i).getLeft() == dia2 && listadias.get(i).getRight() == mes2) {
				pos2 = i;
			}
		}

		if (pos1 == pos2) {
			if(hora1 == hora2){
				return true;
			}else if (hora1 < hora2) {
				return false;
			} else {
				return true;
			}
		} else if (pos1 < pos2) {
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<Solicitud> list() {
		return new ArrayList<Solicitud>(solicitudesOrdenadas);
	}
}
