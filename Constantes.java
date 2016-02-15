package Principal;

public class Constantes {
	public static String TEXTO_VENTANA = "Asignador de encuestas Facultad de Derecho";
	
	public static String TEXTO_LBL_DIAS = "Introduce aqu\u00ED las fechas";
	public static String TEXTO_LBL_HORAS = "Introduce aqu\u00ED las horas";
	public static String TEXTO_LBL_NOMBRE_FICHERO = "Nombre del fichero";
	public static String TEXTO_LBL_SALIDA_ERROR = "Salida de errores:";
	public static String TEXTO_LBL_AUTOR = "Autor: @LorenteAlberto92";
	
	public static String TEXTO_JTXT_DIAS;
	public static String TEXTO_JTXT_HORAS;
	public static String TEXTO_JTXT_FICHERO = "Encuestas.xlsx";
	
	public static String TEXTO_BTN_DIAS = "Cargar fechas";
	public static String TEXTO_BTN_HORAS = "Cargar horas";
	public static String TEXTO_BTN_SALIR = "Salir";
	public static String TEXTO_BTN_CARGAR_Y_ASIGNAR = "<html><center>"+"Cargar fichero y"+"<br>"+"Asignar encuestas"+"</center></html>";
	
	public static String TEXTO_JOPTIONPANE_HORAS_CORRECTAS = "Horas cargadas";
	public static String TEXTO_JOPTIONPANE_HORAS_INCORRECTAS = "Formato de horas incorrecto";
	public static String TEXTO_JOPTIONPANE_DIAS_CORRECTAS = "Días cargados";
	public static String TEXTO_JOPTIONPANE_DIAS_INCORRECTAS = "Formato días incorrectos";
	public static String TEXTO_JOPTIONPANE_FICHERO = "Fichero config dañado. Volver a descargar por favor";
	
	public static String TEXTO_SALIDA_ERROR_1 = "Fichero NO encontrado\nERR1";
	public static String TEXTO_SALIDA_ERROR_2 = "Error leyendo el fichero\nRevisa su formato\nERR2";
	public static String TEXTO_SALIDA_ERROR_3 = "Error haciendo el reparto\nRevisa las solicitudes\nERR3";
	public static String TEXTO_SALIDA_ERROR_4 = "Error haciendo el horario\nERR4";
	public static String TEXTO_SALIDA_ERROR_5 = "Error escribiendo los ficcheros de solicitudes\nERR5";
	public static String TEXTO_SALIDA_ERROR_6 = "Error escribiendo el horario en excel\nERR6";
	public static String TEXTO_SALIDA_ERROR_10 = "Error en la asignacion\nERR10";
	
	public static String[] ficheros = {"solicitudesIncompletas.txt","solicitudesErroneas.txt"};
	
	public static int ERR_1 = -1;
	public static int ERR_2 = -2;
	public static int ERR_3 = -3;
	public static int ERR_4 = -4;
	public static int ERR_5 = -5;
	public static int ERR_6 = -6;
	public static int ERR_10 = -10;
}
