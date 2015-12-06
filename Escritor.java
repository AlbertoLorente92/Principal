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

	private ArrayList<Solicitud> solicitudesIncompletas = new ArrayList<Solicitud>();
	
	private ArrayList<Solicitud> solicitudesIncorrectasP1 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesIncorrectasP2 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesIncorrectasP3 = new ArrayList<Solicitud>();

	private String[] ficheros = new String[2];
	public Escritor() {
		for (int i = 0; i < 2; i++) {
			ficheros[i] = "";
		}
	}

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
			
			return 0;
		}catch(Exception e){
			return -5;
		}
		
	}
	
	public void EscritorExcel(Solicitud[][] salidaExcel,double[] castHorasDouble,ArrayList<Pair<Integer,Integer>> castSemanasSplit,String fichero) {
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
		
		for(int i = 1; i < castHorasDouble.length+1; i++){
			for(int j = 1; j < castSemanasSplit.size()+1; j++){
				if(salidaExcel[i-1][j-1] != null){
					if(!salidaExcel[i-1][j-1].isEmpty() && salidaExcel[i-1][j-1].getDia() == castSemanasSplit.get(j-1).getLeft() &&
							salidaExcel[i-1][j-1].getHora() == castHorasDouble[i-1]){
						datos[i][j] = salidaExcel[i-1][j-1].claveProfesor();
					}else{
						datos[i][j] = "";
					}
				}else{
					datos[i][j] = "";
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
