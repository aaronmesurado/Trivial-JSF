package modelo;

import java.util.Arrays;

public class Jugador {
	
	public String nombre;
	public boolean[] acertados;
	public boolean turno;

	public Jugador() {
		acertados = new boolean[7];
		turno = false;
	}

	public boolean isTurno() {
		return turno;
	}

	public void setTurno(boolean turno) {
		this.turno = turno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean[] getAcertados() {
		return acertados;
	}

	public void setAcertados(boolean[] acertados) {
		this.acertados = acertados;
	}
	
	public boolean isAcertado(int n) {
		return acertados[n];
	}
	
	public void cambiaValor(int n, boolean v) {
		acertados[n] = v;
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", acertados=" + Arrays.toString(acertados) + ", turno=" + turno + "]";
	}
}
