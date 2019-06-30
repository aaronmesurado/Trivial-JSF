package ejb;

import java.util.List;

import modelo.Puntuacion;;

public interface PuntuacionFacade extends AbstractFacade<Puntuacion>{
	public List<Puntuacion> findAll();
	public List<Puntuacion> puntuacionCategoria(int idCat);
	public List<Puntuacion> puntuacionUsuario(int idUs);
	public Puntuacion puntosUsuarioCategoria(int idUs, int idCat);
}