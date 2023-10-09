/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package massaludgrupo17.Entidades;

import java.time.LocalDate;

/**
 *
 * @author Cintia
 */
public class Orden {

    private int idOrden;
    private LocalDate fecha;
    private String formaDePago;
    private double importe;
    private Afiliado afiliado;
    private Prestador prestador;
  public Orden() {
  }

  public Orden(int idOrden, LocalDate fecha, String formaDePago, double importe, Afiliado afiliado, Prestador prestador) {
    this.idOrden = idOrden;
    this.fecha = fecha;
    this.formaDePago = formaDePago;
    this.importe = importe;
    this.afiliado = afiliado;
    this.prestador = prestador;
  }

  public Orden(LocalDate fecha, String formaDePago, double importe, Afiliado afiliado, Prestador prestador) {
    this.fecha = fecha;
    this.formaDePago = formaDePago;
    this.importe = importe;
    this.afiliado = afiliado;
    this.prestador = prestador;
  }

  public int getIdOrden() {
    return idOrden;
  }

  public void setIdOrden(int idOrden) {
    this.idOrden = idOrden;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getFormaDePago() {
    return formaDePago;
  }

  public void setFormaDePago(String formaDePago) {
    this.formaDePago = formaDePago;
  }

  public double getImporte() {
    return importe;
  }

  public void setImporte(double importe) {
    this.importe = importe;
  }

  public Afiliado getAfiliado() {
    return afiliado;
  }

  public void setAfiliado(Afiliado afiliado) {
    this.afiliado = afiliado;
  }

  public Prestador getPrestador() {
    return prestador;
  }

  public void setPrestador(Prestador prestador) {
    this.prestador = prestador;
  }

  
  @Override
  public String toString() {
    return "Orden{" + "idOrden=" + idOrden + ", fecha=" + fecha + '}';
  }

}
