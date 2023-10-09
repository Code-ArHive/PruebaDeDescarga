
package massaludgrupo17.AccesoDatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import massaludgrupo17.Entidades.Afiliado;
import massaludgrupo17.Entidades.Empleado;

/**
 *
 * @author DANILO
 */
public class AfiliadoData {

    private Connection con = null;

    public AfiliadoData() {

        con = Conexion.getConexion();
    }

    public void guardarAfiliado(Afiliado afiliado) {
        String sql = "INSERT INTO afiliado (nombre, apellido, dni,  domicilio, telefono, idempleado, estado)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, afiliado.getNombre());
            ps.setString(2, afiliado.getApellido());
            ps.setInt(3, afiliado.getDni());            
            ps.setString(4, afiliado.getDomicilio());
            ps.setInt(5, afiliado.getTelefono());
            ps.setInt(6, afiliado.getEmpleado().getIdEmpleado());
            ps.setBoolean(7, afiliado.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                afiliado.setIdafiliaado(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Afiliado añadido con exito.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Afiliado" + ex.getMessage());
        }
    }
    
    public void modificarAfiliado(Afiliado afiliado)
  {
  String sql = "UPDATE afiliado SET  nombre = ?,apellido = ?, dni = ? , domicilio=?, telefono=?, idempleado = ?, estado=? "
          + "WHERE idafiliaado = ?"; 
 PreparedStatement ps = null;  
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, afiliado.getNombre());
            ps.setString(2, afiliado.getApellido());
            ps.setInt(3, afiliado.getDni());
            ps.setString(4, afiliado.getDomicilio());
            ps.setInt(5, afiliado.getTelefono());
            ps.setInt(6, afiliado.getEmpleado().getIdEmpleado());           
            ps.setBoolean(7, afiliado.isEstado());
            ps.setInt(8, afiliado.getIdafiliaado());
            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Afiliado Modificado Exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El Afiliado no existe");
            }
            
            ps.close();
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Afiliado" + ex.getMessage());
        }
    }
    
     public void eliminarAfiliado(int id) {
        String sql = "UPDATE afiliado SET estado = 0 WHERE idafiliaado = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                JOptionPane.showMessageDialog(null, " Se eliminó el Afiliado.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla Afiliado");
        }
  }
     
      public Afiliado buscarAfiliado(int id) {
    Afiliado afiliado = null;
    Empleado e=null;
    String sql = "SELECT * FROM afiliado WHERE idafiliaado = ?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
          if (rs.getBoolean("estado") == true) {
              afiliado = new Afiliado();
              EmpleadoData eD= new EmpleadoData();
              afiliado.setIdafiliaado(id);
              afiliado.setDni(rs.getInt("dni"));
              afiliado.setApellido(rs.getString("apellido"));
              afiliado.setNombre(rs.getString("nombre"));
              e=eD.buscarEmpleado(rs.getInt("idempleado"));
              afiliado.setEmpleado(e);
              afiliado.setDomicilio(rs.getString("domicilio"));
              afiliado.setTelefono(rs.getInt("telefono"));              
              afiliado.setEstado(rs.getBoolean("estado"));
          } else {
               afiliado = new Afiliado();
                EmpleadoData eD= new EmpleadoData();
              afiliado.setIdafiliaado(id);
              afiliado.setDni(rs.getInt("dni"));
              afiliado.setApellido(rs.getString("apellido"));
              afiliado.setNombre(rs.getString("nombre"));
              e=eD.buscarEmpleado(rs.getInt("idempleado"));
              afiliado.setEmpleado(e);
              afiliado.setDomicilio(rs.getString("domicilio"));
              afiliado.setTelefono(rs.getInt("telefono"));              
              afiliado.setEstado(rs.getBoolean("estado"));
              JOptionPane.showMessageDialog(null, "Afiliado esta dado de BAJA");
              
          }
        
      }
      else {
        JOptionPane.showMessageDialog(null, "No existe el Afiliado");
      }
        ps.close();
      } catch (SQLException ex) { 
 JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Afiliado "+ex.getMessage()); 
 }
      return afiliado;
    }
      
      public List<Afiliado> listarAfiliado(){
      
            String sql="select idafiliaado, nombre, apellido, dni,  domicilio, telefono, idempleado "
                    + "from afiliado "
                    + "where estado=1";
            ArrayList<Afiliado> afi= new ArrayList<>();
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                Afiliado a=new Afiliado();
                 EmpleadoData eD= new EmpleadoData();
                 a.setIdafiliaado(rs.getInt("idafiliaado"));
              a.setDni(rs.getInt("dni"));
              a.setApellido(rs.getString("apellido"));
              a.setNombre(rs.getString("nombre"));
              Empleado e=eD.buscarEmpleado(rs.getInt("idempleado"));
              a.setEmpleado(e);
              a.setDomicilio(rs.getString("domicilio"));
              a.setTelefono(rs.getInt("telefono"));              
              a.setEstado(rs.getBoolean("estado"));
              afi.add(a);
            
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Afiliado "+ex.getMessage());
        }
            
       return afi;
      }

       public Afiliado buscarAfiliadoPorDni(int dni) {
    Afiliado afiliado = null;
    String sql = "SELECT * FROM afiliado WHERE dni = ?";
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, dni);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
          if (rs.getBoolean("estado") == true) {
              afiliado = new Afiliado();
               EmpleadoData eD= new EmpleadoData();
              afiliado.setIdafiliaado(rs.getInt("idafiliaado"));
              afiliado.setDni(dni);
              afiliado.setApellido(rs.getString("apellido"));
              afiliado.setNombre(rs.getString("nombre"));
              Empleado  e=eD.buscarEmpleado(rs.getInt("idempleado"));
              afiliado.setEmpleado(e);
              afiliado.setDomicilio(rs.getString("domicilio"));
              afiliado.setTelefono(rs.getInt("telefono"));              
              afiliado.setEstado(rs.getBoolean("estado"));
          } else {
               afiliado = new Afiliado(); 
                EmpleadoData eD= new EmpleadoData();
              afiliado.setIdafiliaado(rs.getInt("idafiliaado"));
              afiliado.setDni(dni);
              afiliado.setApellido(rs.getString("apellido"));
              afiliado.setNombre(rs.getString("nombre"));
              Empleado e=eD.buscarEmpleado(rs.getInt("idempleado"));
              afiliado.setEmpleado(e);
              afiliado.setDomicilio(rs.getString("domicilio"));
              afiliado.setTelefono(rs.getInt("telefono"));              
              afiliado.setEstado(rs.getBoolean("estado"));
              JOptionPane.showMessageDialog(null, "Afiliado esta dado de BAJA");
              
          }
        
      }
      else {
        JOptionPane.showMessageDialog(null, "No existe el Afiliado");
      }
        ps.close();
      } catch (SQLException ex) { 
 JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Afiliado "+ex.getMessage()); 
 }
      return afiliado;
    }
      
}
