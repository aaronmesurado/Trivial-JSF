package ejb;

import java.util.List;

import modelo.Usuario;;

public interface UsuarioFacade extends AbstractFacade<Usuario>{
	public List<Usuario> findAll();
	public Usuario usuarioPorID(int idUsuario);
	public Usuario usuarioPorNombre(String nombre);
	public String contrasena(String nombre);
}