package Principal;

public class Solicitud {
	private String nombre;
	private String asignatura;
	private String grupo;
	private String carrera;
	private int aula;
	private int dia;
	private String SAula;
	private int mes;
	private double hora;
	private int numLinea,numOpcion;

	public int getNumLinea() {
		return numLinea;
	}

	public void setNumLinea(int numLinea) {
		this.numLinea = numLinea;
	}

	public Solicitud(Solicitud s) {
		nombre = s.getNombre();
		asignatura = s.getAsignatura();
		grupo = s.getGrupo();
		carrera = s.getCarrera();
		aula = 0;
		dia = s.getDia();
		SAula  = s.getAula();
		mes = s.getMes();
		hora = s.getHora();
		numLinea = s.getNumLinea();
	}
	
	public Solicitud() {
		numOpcion = aula = dia = mes = 0;
		hora = 0.0;

		SAula = nombre = asignatura = grupo = carrera = "";
	}
	
	public int getNumOpcion() {
		return numOpcion;
	}

	public void setNumOpcion(int numOpcion) {
		this.numOpcion = numOpcion;
	}

	public Boolean isEmpty(){
		return aula == 0 && dia == 0 && mes == 0 && hora == 0 && asignatura.isEmpty() && grupo.isEmpty();
	}

	private void parseFecha(String s) {
		//System.out.println(s + " FECHA");
		String[] fecha = s.split(" ");
		setMes(Integer.parseInt(fecha[0].split("/")[1]));
		dia = Integer.parseInt(fecha[0].split("/")[0]);

		int min = Integer.parseInt(fecha[1].split(":")[0]);
		int seg = Integer.parseInt(fecha[1].split(":")[1]);
		double segD = seg / 100.0;

		hora = (double) min + segD;
	}

	public Boolean esIgualQue(Solicitud s) {
		if (s == null)
			return false;
		return mes == s.getMes() && dia == s.getDia() && hora == s.getHora();
	}

	public Boolean estaCompleta() {
		return !nombre.isEmpty() && !asignatura.isEmpty() && !grupo.isEmpty() && !carrera.isEmpty() && (aula != 0 || !SAula.isEmpty())
				&& dia != 0 && hora != 0.0 && mes != 0;

	}

	public String completeToString() {
		int aux = numLinea + 1;
		if(SAula.isEmpty()){
			return "ERROR-Linea = " + aux + "|| " + carrera + " N: " + nombre + " As: " + asignatura + " G: " + grupo + " A: " + aula + " M: " + mes + " D: " + dia + " H: "
					+ hora;
		}else{
			return "ERROR-Linea = " + aux + "|| " + carrera + " N: " + nombre + " As: " + asignatura + " G: " + grupo + " A: " + SAula + " M: " + mes + " D: " + dia + " H: "
					+ hora;
		}
	}
	
	public String toString() {
		if(SAula.isEmpty()){
			return "Día: " + dia + " Mes: " + mes + " Hora: " + hora  + " Carrera: "  + carrera + " Nombre: " + nombre 
					+ " Asig.: " + asignatura + " Grup.: " + grupo + " Aula: " + 
					aula + "||Opción = " + numOpcion;
		}else{
			return  "Día: " + dia + " Mes: " + mes + " Hora: " + hora  + " Carrera: "  + carrera + " Nombre: " + nombre 
					+ " Asig.: " + asignatura + " Grup.: " + grupo + " Aula: " + 
					SAula + " ||Opción = " + numOpcion;
		}
	}

	/**
	 * 
	 * @return Mes-Dia-Hora-Profesor-Aula
	 */
	public String getFechaCompleta() {
		if(SAula.isEmpty()){
			return mes + " " + dia + "-" + hora + "-" + nombre + "-" + aula;
		}else{
			return mes + " " + dia + "-" + hora + "-" + nombre + "-" + SAula;
		}
	}

	public String clave() {
		return carrera + "-" + asignatura + "-" + grupo;
	}
	
	public String claveProfesor() {
		if(SAula.isEmpty()){
			return "Aula: " + aula + "\nTitulación: " + carrera + "\nAsignatura: " + asignatura + "\nGrupo: " + grupo + "\nN: " + nombre;
		}else{
			return "Aula: " + SAula + "\nTitulación: " + carrera + "\nAsignatura: " + asignatura + "\nGrupo: " + grupo + "\nN: " + nombre;
		}
		
	}

	public String salida() {
		if(SAula.isEmpty()){
			return carrera + "-" + asignatura + "-" + grupo + "-" + nombre + "-" + aula;
		}else{
			return carrera + "-" + asignatura + "-" + grupo + "-" + nombre + "-" + SAula;
		}
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura.toLowerCase().replaceAll(" ", "");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toLowerCase();
	}

	public String getAula() {
		return SAula;
	}

	public void setAula(int aula) {
		this.aula = aula;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public double getHora() {
		return hora;
	}

	public void setHora(double hora) {
		int fraccion = 20;
		if (((int) hora * 100) % fraccion == 0)
			this.hora = hora;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo.toUpperCase();
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera.toLowerCase().replaceAll(" ", "");
	}

	public void setFechaString(String s) {
		parseFecha(s);
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public void setAulaString(String s){
		this.SAula = s;
	}
}
