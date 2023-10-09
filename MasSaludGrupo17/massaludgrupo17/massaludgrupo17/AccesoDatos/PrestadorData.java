/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package massaludgrupo17.AccesoDatos;

/**
 *
 * @author Leroom
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import massaludgrupo17.Entidades.Especialidad;
import massaludgrupo17.Entidades.Prestador;

public class PrestadorData {

    private final Connection connection;
    private EspecialidadData especialidadData;

    public PrestadorData(Connection connection, EspecialidadData especialidadData) {
        this.connection = connection;
        this.especialidadData = especialidadData;
    }
    public void guardarPrestador(Prestador prestador) {
        try {

            if (prestador.getNombre() == null || prestador.getApellido() == null) {
                return;
            }

            String query = "INSERT INTO prestador (nombre, apellido, institucion, direccion, telefono, email, idespecialidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, prestador.getNombre());
                statement.setString(2, prestador.getApellido());
                statement.setString(3, prestador.getInstitucion());
                statement.setString(4, prestador.getDireccion());
                statement.setString(5, prestador.getTelefono());
                statement.setString(6, prestador.getEmail());
                statement.setInt(7, prestador.getEspecialidad().getIdEspecialidad());
                statement.setBoolean(8, prestador.Estado());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    prestador.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar el prestador: " + e.getMessage());
        }
    }

    public void actualizarPrestador(Prestador prestador) {
        try {

            if (prestador.getId() == 0) {
                return;
            }

            String query = "UPDATE prestador SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, email = ?, idespecialidad = ?, estado = ? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, prestador.getNombre());
                statement.setString(2, prestador.getApellido());
                statement.setString(3, prestador.getInstitucion());
                statement.setString(4, prestador.getDireccion());
                statement.setString(5, prestador.getTelefono());
                statement.setString(6, prestador.getEmail());
                statement.setInt(7, prestador.getEspecialidad().getIdEspecialidad());
                statement.setBoolean(8, prestador.Estado());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el prestador: " + e.getMessage());
        }
    }

    public void eliminarPrestador(int id) {
        try {
            String query = "DELETE FROM prestador WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el prestador: " + e.getMessage());
        }
    }

    public List<Prestador> obtenerPrestadores() {
        List<Prestador> prestadores = new ArrayList<>();

        try {
            String sql = "SELECT * FROM prestador";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    Prestador prestador = new Prestador();
                    prestador.setId(resultSet.getInt("id"));
                    prestador.setNombre(resultSet.getString("nombre"));
                    prestador.setApellido(resultSet.getString("apellido"));
                    prestador.setInstitucion(resultSet.getString("institucion"));
                    prestador.setDireccion(resultSet.getString("direccion"));
                    prestador.setTelefono(resultSet.getString("telefono"));
                    prestador.setEmail(resultSet.getString("email"));


                    int idEspecialidad = resultSet.getInt("idespecialidad");


                    Especialidad especialidad = especialidadData.buscarEspecialidadPorId(idEspecialidad);


                    prestador.setEspecialidad(especialidad);

                    prestador.setEstado(resultSet.getBoolean("estado"));
                    prestadores.add(prestador);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener prestadores: " + e.getMessage());
        }

        return prestadores;
    }
public Prestador buscarPrestador(int idABuscar) {
    Prestador prestador = null;

    try {
        String sql = "SELECT * FROM prestador WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idABuscar);

        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            prestador = new Prestador();
            prestador.setId(resultSet.getInt("id"));
            prestador.setNombre(resultSet.getString("nombre"));
            prestador.setApellido(resultSet.getString("apellido"));
            prestador.setInstitucion(resultSet.getString("institucion"));
            prestador.setDireccion(resultSet.getString("direccion"));
            prestador.setTelefono(resultSet.getString("telefono"));
            prestador.setEmail(resultSet.getString("email"));


            int idEspecialidad = resultSet.getInt("idespecialidad");
            

            Especialidad especialidad = especialidadData.buscarEspecialidadPorId(idEspecialidad);
            

            prestador.setEspecialidad(especialidad);

            prestador.setEstado(resultSet.getBoolean("estado"));
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener prestador: " + e.getMessage());
    }

    return prestador;
}
}

