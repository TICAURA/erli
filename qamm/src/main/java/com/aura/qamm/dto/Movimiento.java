package com.aura.qamm.dto;

public class Movimiento {
    private Integer idColaborador;

    private Long idMovimiento;

    private String claveAutorizacion;
    private String fechaOperacion;

    private Double importeSolicitado;
    private Double importeComision;
    private Double promocion;
    private Double importeTotal;
    private int promocionId;

    private Boolean periodoAnterior;

    private String fechaSolicita;
    private String fechaTransferencia;
    private String periodicidad;
    private String centroCostos;
    private String idEmpresa;

    private String status;


    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getClaveAutorizacion() {
        return claveAutorizacion;
    }

    public void setClaveAutorizacion(String claveAutorizacion) {
        this.claveAutorizacion = claveAutorizacion;
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Double getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImporteSolicitado(Double importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public Double getImporteComision() {
        return importeComision;
    }

    public void setImporteComision(Double importeComision) {
        this.importeComision = importeComision;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getFechaSolicita() {
        return fechaSolicita;
    }

    public void setFechaSolicita(String fechaSolicita) {
        this.fechaSolicita = fechaSolicita;
    }

    public String getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(String fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Movimiento(Integer idColaborador, Long idMovimiento, String claveAutorizacion, String fechaOperacion, Double importeSolicitado, Double importeComision, Double importeTotal) {
        this.idColaborador = idColaborador;
        this.idMovimiento = idMovimiento;
        this.claveAutorizacion = claveAutorizacion;
        this.fechaOperacion = fechaOperacion;
        this.importeSolicitado = importeSolicitado;
        this.importeComision = importeComision;
        this.importeTotal = importeTotal;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "idColaborador=" + idColaborador +
                ", idMovimiento=" + idMovimiento +
                ", claveAutorizacion='" + claveAutorizacion + '\'' +
                ", fechaOperacion='" + fechaOperacion + '\'' +
                ", importeSolicitado=" + importeSolicitado +
                ", importeComision=" + importeComision +
                ", promocion=" + promocion +
                ", importeTotal=" + importeTotal +
                ", promocionId=" + promocionId +
                ", periodoAnterior=" + periodoAnterior +
                ", fechaSolicita='" + fechaSolicita + '\'' +
                ", fechaTransferencia='" + fechaTransferencia + '\'' +
                ", periodicidad='" + periodicidad + '\'' +
                ", centroCostos='" + centroCostos + '\'' +
                ", idEmpresa='" + idEmpresa + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Movimiento() {
    }

    public Boolean getPeriodoAnterior() {
        return periodoAnterior;
    }

    public void setPeriodoAnterior(Boolean periodoAnterior) {
        this.periodoAnterior = periodoAnterior;
    }

    public Double getPromocion() {
        return promocion;
    }

    public void setPromocion(Double promocion) {
        this.promocion = promocion;
    }

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }
}
