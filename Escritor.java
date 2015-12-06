package Principal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Escritor {
	/**
	 * solicitudes[0] => Incompletas solicitudes[1] => Erroneas solicitudes[2]
	 * => Repetidas
	 */
	//private ArrayList<ArrayList<Solicitud>> solicitudes = new ArrayList<ArrayList<Solicitud>>();
	private ArrayList<Solicitud> solicitudesIncompletas = new ArrayList<Solicitud>();
	
	private ArrayList<Solicitud> solicitudesIncorrectasP1 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesIncorrectasP2 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesIncorrectasP3 = new ArrayList<Solicitud>();
	
	private ArrayList<Solicitud> solicitudesRepetidasP1 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesRepetidasP2 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesRepetidasP3 = new ArrayList<Solicitud>();
	/**
	 * ficheros[0] => Incompletas ficheros[1] => Erroneas ficheros[2] =>
	 * Repetidas
	 */
	private String[] ficheros = new String[3];

	public Escritor() {
		for (int i = 0; i < 3; i++) {
			//solicitudes.add(i, new ArrayList<Solicitud>());
			ficheros[i] = "";
		}
	}

	/*public void setSolicitudes(ArrayList<ArrayList<Solicitud>> s, String[] f) {
		solicitudes = s;
		ficheros = f;
	}*/

	/**
	 * I = 0
	 * 
	 * @param s
	 * @param f
	 */
	public void setSolicitudesIncompletas(ArrayList<Solicitud> s, String f) {
		solicitudesIncompletas = s;
		ficheros[0] = f;
	}

	/**
	 * I = 1
	 * 
	 * @param s
	 * @param f
	 */
	public void setSolicitudesIncorrectas(ArrayList<Solicitud> sP1,ArrayList<Solicitud> sP2,ArrayList<Solicitud> sP3, String f) {
		solicitudesIncorrectasP1 = sP1;
		solicitudesIncorrectasP2 = sP2;
		solicitudesIncorrectasP3 = sP3;
		ficheros[1] = f;
	}

	/**
	 * I = 2
	 * 
	 * @param s
	 * @param f
	 */
	public void setSolicitudesRepetidas(ArrayList<Solicitud> sP1,ArrayList<Solicitud> sP2,ArrayList<Solicitud> sP3, String f) {
		solicitudesRepetidasP1 = sP1;
		solicitudesRepetidasP2 = sP2;
		solicitudesRepetidasP3 = sP3;
		ficheros[2] = f;
	}

	public int escribir() {
		try{  	
    		File file; 
    		for(int i = 0; i < 3; i++){
    			file = new File(ficheros[i]); 
    			file.delete();
    		}	   
    	}catch(Exception e){ 
    		//System.out.println("No existe el fichero");  		
    	}
		
		try{
			escribeAux(false,0,solicitudesIncompletas);	
			
			escribeAux(false,1,solicitudesIncorrectasP1);
			escribeAux(true,1,solicitudesIncorrectasP2);
			escribeAux(true,1,solicitudesIncorrectasP3);
			
			escribeAux(false,2,solicitudesRepetidasP1);
			escribeAux(false,2,solicitudesRepetidasP2);
			escribeAux(false,2,solicitudesRepetidasP3);
			return 0;
		}catch(Exception e){
			return -5;
		}
		
	}
	
	public void EscritorExcel(ArrayList<Solicitud> prueba,double[] castHorasDouble,ArrayList<Pair<Integer,Integer>> castSemanasSplit,String fichero) {
		try{    		
    		File file = new File(fichero); 	
    		file.delete();   
    	}catch(Exception e){ 
    		System.out.println("No existe el fichero");  		
    	}
		
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Encuestas");

		// This data needs to be written (Object[])
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		
		Object[][] datos = new Object[castHorasDouble.length+1][castSemanasSplit.size()+1];
		datos[0][0] = "Hora/Dia";
		for(int i = 1; i < castSemanasSplit.size()+1; i++){
			datos[0][i] = castSemanasSplit.get(i-1).getLeft();
		}
		String aux;
		for(int i = 1; i < castHorasDouble.length+1; i++){
			aux = String.valueOf(castHorasDouble[i-1]);
			aux = aux.replace(".",":").concat("0");
			datos[i][0] = aux;
		}
		
		for(int j = 1; j < castHorasDouble.length+1; j++){
			for(int i = 1; i < castSemanasSplit.size()+1; i++){	
				if(!prueba.isEmpty() && prueba.get(0).getDia() == castSemanasSplit.get(i-1).getLeft() &&
					prueba.get(0).getHora() == castHorasDouble[j-1]){
					datos[j][i] = prueba.get(0).claveProfesor();
					prueba.remove(0);
				}else{
					datos[j][i] = "";
				}
			}
		}

		for(int i = 0; i < castHorasDouble.length+1; i++){
			data.put(i, datos[i]);
		}

		// Iterate over data and write to sheet
		Set<Integer> keyset = data.keySet();
		int rownum = 0;
		for (Integer key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(fichero));
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void escribeAux(Boolean append,int nombrefichero,ArrayList<Solicitud> s){
		FileWriter fichero = null;
		PrintWriter pw = null;

	
		if(s.size()!=0){
			try {
				fichero = new FileWriter(ficheros[nombrefichero],append);
				pw = new PrintWriter(fichero);

				for (Solicitud sol : s) {
					pw.println(sol.completeToString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != fichero) {
						fichero.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		}
	}
}
