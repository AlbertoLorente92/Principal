package Principal;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Lector {
	private Hashtable<String,Triplet<Solicitud,Solicitud,Solicitud>> encuestas = new Hashtable<String,Triplet<Solicitud,Solicitud,Solicitud>>();
	private ArrayList<Solicitud> solicitudesPrioridad1 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesPrioridad2 = new ArrayList<Solicitud>();
	private ArrayList<Solicitud> solicitudesPrioridad3 = new ArrayList<Solicitud>();
	private Hashtable<String, Boolean> claves = new Hashtable<String, Boolean>();
	private ArrayList<Solicitud> solicitudesIncompletas = new ArrayList<Solicitud>();
	private String fichero;
	
	public Lector(String fichero) {
		this.fichero = fichero;
		/*try {
			FileInputStream file = new FileInputStream(new File(fichero));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();

			Solicitud[] s = new Solicitud[9];

			while (rowIterator.hasNext()) {
				for (int j = 0; j < 9; j++) {
					s[j] = new Solicitud();
				}

				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_NUMERIC:

						if (cell.getColumnIndex() == 5) { // Aula - Opcion 1 2 3
							s[0].setAula((int) cell.getNumericCellValue());
							s[1].setAula((int) cell.getNumericCellValue());
							s[2].setAula((int) cell.getNumericCellValue());
						}

						else if (cell.getColumnIndex() == 12) { // Aula - Opcion
																// 4 5 6
							s[3].setAula((int) cell.getNumericCellValue());
							s[4].setAula((int) cell.getNumericCellValue());
							s[5].setAula((int) cell.getNumericCellValue());
						}

						else if (cell.getColumnIndex() == 19) { // Aula - Opcion
																// 4 5 6
							s[6].setAula((int) cell.getNumericCellValue());
							s[7].setAula((int) cell.getNumericCellValue());
							s[8].setAula((int) cell.getNumericCellValue());
						}

						break;
					case Cell.CELL_TYPE_STRING:

						if (cell.getColumnIndex() == 1) { // Nombre - Comun
							// System.out.println(" NOMBRE = " +
							// cell.getColumnIndex());
							for (int j = 0; j < s.length; j++) {
								s[j].setNombre(cell.getStringCellValue());
								s[j].setNumLinea(cell.getRowIndex());
							}
						} else if (cell.getColumnIndex() == 2) { // Carrera -
																	// Opcion 1
																	// 2 3
							s[0].setCarrera(cell.getStringCellValue());
							s[1].setCarrera(cell.getStringCellValue());
							s[2].setCarrera(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 3) { // Asignatura -
																	// Opcion 1
																	// 2 3
							s[0].setAsignatura(cell.getStringCellValue());
							s[1].setAsignatura(cell.getStringCellValue());
							s[2].setAsignatura(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 4) { // Grupo -
																	// Opcion 1
																	// 2 3
							s[0].setGrupo(cell.getStringCellValue());
							s[1].setGrupo(cell.getStringCellValue());
							s[2].setGrupo(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 6) { // Fecha -
																	// Opcion 1
							s[0].setFechaString(cell.getStringCellValue());
							s[0].setNumOpcion(1);
						} else if (cell.getColumnIndex() == 7) { // Fecha -
																	// Opcion 2
							s[1].setFechaString(cell.getStringCellValue());
							s[1].setNumOpcion(2);
						} else if (cell.getColumnIndex() == 8) { // Fecha -
																	// Opcion 3
							s[2].setFechaString(cell.getStringCellValue());
							s[2].setNumOpcion(3);
						}

						else if (cell.getColumnIndex() == 9) { // Carrera -
																// Opcion 4 5 6
							s[3].setCarrera(cell.getStringCellValue());
							s[4].setCarrera(cell.getStringCellValue());
							s[5].setCarrera(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 10) { // Asignatura
																	// - Opcion
																	// 4 5 6
							s[3].setAsignatura(cell.getStringCellValue());
							s[4].setAsignatura(cell.getStringCellValue());
							s[5].setAsignatura(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 11) { // Grupo -
																	// Opcion 4
																	// 5 6
							s[3].setGrupo(cell.getStringCellValue());
							s[4].setGrupo(cell.getStringCellValue());
							s[5].setGrupo(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 13) { // Fecha -
																	// Opcion 4
							s[3].setFechaString(cell.getStringCellValue());
							s[3].setNumOpcion(1);
						} else if (cell.getColumnIndex() == 14) { // Fecha -
																	// Opcion 5
							s[4].setFechaString(cell.getStringCellValue());
							s[4].setNumOpcion(2);
						} else if (cell.getColumnIndex() == 15) { // Fecha -
																	// Opcion 6
							s[5].setFechaString(cell.getStringCellValue());
							s[5].setNumOpcion(3);
						}

						else if (cell.getColumnIndex() == 16) { // Carrera -
																// Opcion 7 8 9
							s[6].setCarrera(cell.getStringCellValue());
							s[7].setCarrera(cell.getStringCellValue());
							s[8].setCarrera(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 17) { // Asignatura
																	// - Opcion
																	// 7 8 9
							s[6].setAsignatura(cell.getStringCellValue());
							s[7].setAsignatura(cell.getStringCellValue());
							s[8].setAsignatura(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 18) { // Grupo -
																	// Opcion 7
																	// 8 9
							s[6].setGrupo(cell.getStringCellValue());
							s[7].setGrupo(cell.getStringCellValue());
							s[8].setGrupo(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 20) { // Fecha -
																	// Opcion 7
							s[6].setFechaString(cell.getStringCellValue());
							s[6].setNumOpcion(1);
						} else if (cell.getColumnIndex() == 21) { // Fecha -
																	// Opcion 8
							s[7].setFechaString(cell.getStringCellValue());
							s[7].setNumOpcion(2);
						} else if (cell.getColumnIndex() == 22) { // Fecha -
																	// Opcion 9
							s[8].setFechaString(cell.getStringCellValue());
							s[8].setNumOpcion(3);
						}

						else if (cell.getColumnIndex() == 5) { // Aula - Opcion
																// 1 2 3
							s[0].setAulaString(cell.getStringCellValue());
							s[1].setAulaString(cell.getStringCellValue());
							s[2].setAulaString(cell.getStringCellValue());
						}

						else if (cell.getColumnIndex() == 12) { // Aula - Opcion
																// 4 5 6
							s[3].setAulaString(cell.getStringCellValue());
							s[4].setAulaString(cell.getStringCellValue());
							s[5].setAulaString(cell.getStringCellValue());
						}

						else if (cell.getColumnIndex() == 19) { // Aula - Opcion
																// 4 5 6
							s[6].setAulaString(cell.getStringCellValue());
							s[7].setAulaString(cell.getStringCellValue());
							s[8].setAulaString(cell.getStringCellValue());
						}

					}
				}
				for (int j = 0; j < s.length; j++) {
					if(encuestas.containsKey(s[j].clave())){
						int next =  encuestas.get(s[j].clave()).getFirstEmpty();
						if(next==0) encuestas.get(s[j].clave()).setLeft(s[j]);
						if(next==1) encuestas.get(s[j].clave()).setMidle(s[j]);
						if(next==2) encuestas.get(s[j].clave()).setRight(s[j]);
					} else {
						Triplet<Solicitud, Solicitud, Solicitud> aux = new Triplet<Solicitud, Solicitud, Solicitud>();
						aux.setLeft(s[j]);
						encuestas.put(s[j].clave(),aux);
					}
					
					if (s[j].estaCompleta()) {
						claves.put(s[j].clave(), true);
						
						if(s[j].getNumOpcion()==1)
							solicitudesPrioridad1.add(s[j]);
						else if(s[j].getNumOpcion()==2)
							solicitudesPrioridad2.add(s[j]);
						else if(s[j].getNumOpcion()==3)
							solicitudesPrioridad3.add(s[j]);
						// s[j].toString();
					} else {
						if(!s[j].isEmpty()){					
							solicitudesIncompletas.add(s[j]);
						}
					}
				}
			}			
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public int lee(){
		try {
			FileInputStream file = new FileInputStream(new File(fichero));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();

			Solicitud[] s = new Solicitud[9];

			while (rowIterator.hasNext()) {
				for (int j = 0; j < 9; j++) {
					s[j] = new Solicitud();
				}

				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_NUMERIC:

						if (cell.getColumnIndex() == 5) { // Aula - Opcion 1 2 3
							s[0].setAula((int) cell.getNumericCellValue());
							s[1].setAula((int) cell.getNumericCellValue());
							s[2].setAula((int) cell.getNumericCellValue());
						}

						else if (cell.getColumnIndex() == 12) { // Aula - Opcion
																// 4 5 6
							s[3].setAula((int) cell.getNumericCellValue());
							s[4].setAula((int) cell.getNumericCellValue());
							s[5].setAula((int) cell.getNumericCellValue());
						}

						else if (cell.getColumnIndex() == 19) { // Aula - Opcion
																// 4 5 6
							s[6].setAula((int) cell.getNumericCellValue());
							s[7].setAula((int) cell.getNumericCellValue());
							s[8].setAula((int) cell.getNumericCellValue());
						}

						break;
					case Cell.CELL_TYPE_STRING:

						if (cell.getColumnIndex() == 1) { // Nombre - Comun
							// System.out.println(" NOMBRE = " + cell.getColumnIndex());
							for (int j = 0; j < s.length; j++) {
								s[j].setNombre(cell.getStringCellValue());
								s[j].setNumLinea(cell.getRowIndex());
							}
						} else if (cell.getColumnIndex() == 2) { // Carrera -
																	// Opcion 1
																	// 2 3
							s[0].setCarrera(cell.getStringCellValue());
							s[1].setCarrera(cell.getStringCellValue());
							s[2].setCarrera(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 3) { // Asignatura -
																	// Opcion 1
																	// 2 3
							s[0].setAsignatura(cell.getStringCellValue());
							s[1].setAsignatura(cell.getStringCellValue());
							s[2].setAsignatura(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 4) { // Grupo -
																	// Opcion 1
																	// 2 3
							s[0].setGrupo(cell.getStringCellValue());
							s[1].setGrupo(cell.getStringCellValue());
							s[2].setGrupo(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 6) { // Fecha -
																	// Opcion 1
							s[0].setFechaString(cell.getStringCellValue());
							s[0].setNumOpcion(1);
						} else if (cell.getColumnIndex() == 7) { // Fecha -
																	// Opcion 2
							s[1].setFechaString(cell.getStringCellValue());
							s[1].setNumOpcion(2);
						} else if (cell.getColumnIndex() == 8) { // Fecha -
																	// Opcion 3
							s[2].setFechaString(cell.getStringCellValue());
							s[2].setNumOpcion(3);
						}

						else if (cell.getColumnIndex() == 9) { // Carrera -
																// Opcion 4 5 6
							s[3].setCarrera(cell.getStringCellValue());
							s[4].setCarrera(cell.getStringCellValue());
							s[5].setCarrera(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 10) { // Asignatura
																	// - Opcion
																	// 4 5 6
							s[3].setAsignatura(cell.getStringCellValue());
							s[4].setAsignatura(cell.getStringCellValue());
							s[5].setAsignatura(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 11) { // Grupo -
																	// Opcion 4
																	// 5 6
							s[3].setGrupo(cell.getStringCellValue());
							s[4].setGrupo(cell.getStringCellValue());
							s[5].setGrupo(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 13) { // Fecha -
																	// Opcion 4
							s[3].setFechaString(cell.getStringCellValue());
							s[3].setNumOpcion(1);
						} else if (cell.getColumnIndex() == 14) { // Fecha -
																	// Opcion 5
							s[4].setFechaString(cell.getStringCellValue());
							s[4].setNumOpcion(2);
						} else if (cell.getColumnIndex() == 15) { // Fecha -
																	// Opcion 6
							s[5].setFechaString(cell.getStringCellValue());
							s[5].setNumOpcion(3);
						}

						else if (cell.getColumnIndex() == 16) { // Carrera -
																// Opcion 7 8 9
							s[6].setCarrera(cell.getStringCellValue());
							s[7].setCarrera(cell.getStringCellValue());
							s[8].setCarrera(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 17) { // Asignatura
																	// - Opcion
																	// 7 8 9
							s[6].setAsignatura(cell.getStringCellValue());
							s[7].setAsignatura(cell.getStringCellValue());
							s[8].setAsignatura(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 18) { // Grupo -
																	// Opcion 7
																	// 8 9
							s[6].setGrupo(cell.getStringCellValue());
							s[7].setGrupo(cell.getStringCellValue());
							s[8].setGrupo(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 20) { // Fecha -
																	// Opcion 7
							s[6].setFechaString(cell.getStringCellValue());
							s[6].setNumOpcion(1);
						} else if (cell.getColumnIndex() == 21) { // Fecha -
																	// Opcion 8
							s[7].setFechaString(cell.getStringCellValue());
							s[7].setNumOpcion(2);
						} else if (cell.getColumnIndex() == 22) { // Fecha -
																	// Opcion 9
							s[8].setFechaString(cell.getStringCellValue());
							s[8].setNumOpcion(3);
						}

						else if (cell.getColumnIndex() == 5) { // Aula - Opcion
																// 1 2 3
							s[0].setAulaString(cell.getStringCellValue());
							s[1].setAulaString(cell.getStringCellValue());
							s[2].setAulaString(cell.getStringCellValue());
						}

						else if (cell.getColumnIndex() == 12) { // Aula - Opcion
																// 4 5 6
							s[3].setAulaString(cell.getStringCellValue());
							s[4].setAulaString(cell.getStringCellValue());
							s[5].setAulaString(cell.getStringCellValue());
						}

						else if (cell.getColumnIndex() == 19) { // Aula - Opcion
																// 4 5 6
							s[6].setAulaString(cell.getStringCellValue());
							s[7].setAulaString(cell.getStringCellValue());
							s[8].setAulaString(cell.getStringCellValue());
						}

					}
				}
				for (int j = 0; j < s.length; j++) {
					if(encuestas.containsKey(s[j].clave())){
						int next =  encuestas.get(s[j].clave()).getFirstEmpty();
						if(next==0) encuestas.get(s[j].clave()).setLeft(s[j]);
						if(next==1) encuestas.get(s[j].clave()).setMidle(s[j]);
						if(next==2) encuestas.get(s[j].clave()).setRight(s[j]);
					} else {
						Triplet<Solicitud, Solicitud, Solicitud> aux = new Triplet<Solicitud, Solicitud, Solicitud>();
						aux.setLeft(s[j]);
						encuestas.put(s[j].clave(),aux);
					}
					
					if (s[j].estaCompleta()) {
						claves.put(s[j].clave(), true);
						
						if(s[j].getNumOpcion()==1)
							solicitudesPrioridad1.add(s[j]);
						else if(s[j].getNumOpcion()==2)
							solicitudesPrioridad2.add(s[j]);
						else if(s[j].getNumOpcion()==3)
							solicitudesPrioridad3.add(s[j]);
						// s[j].toString();
					} else {
						if(!s[j].isEmpty()){					
							solicitudesIncompletas.add(s[j]);
						}
					}
				}
			}			
			file.close();
			
			return 0;
		} catch (Exception e) {
			return -2;
		}
	}

	public ArrayList<Solicitud> getSolicitudesIncompletas() {
		return new  ArrayList<Solicitud>(solicitudesIncompletas);
	}
	
	public ArrayList<Solicitud> getSolicitudesPrioridad1() {
		return new  ArrayList<Solicitud>(solicitudesPrioridad1);
	}
	
	public ArrayList<Solicitud> getSolicitudesPrioridad2() {
		return new  ArrayList<Solicitud>(solicitudesPrioridad2);
	}
	
	public ArrayList<Solicitud> getSolicitudesPrioridad3() {
		return new  ArrayList<Solicitud>(solicitudesPrioridad3);
	}

	public Hashtable<String, Boolean> getClaves() {
		return new Hashtable<String, Boolean>(claves);
	}
	
	public Hashtable<String,Triplet<Solicitud,Solicitud,Solicitud>> getEncuestas(){
		return new Hashtable<String,Triplet<Solicitud,Solicitud,Solicitud>>(encuestas);
	}

}
