package com.aura.qamm.dao;

import com.aura.qamm.config.DataSourceConfig;
import com.aura.qamm.dto.facturacion.*;
import com.aura.qamm.exception.BusinessException;
import com.aura.qamm.util.FacturaEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class FacturacionDao {
    Logger LOGGER = LoggerFactory.getLogger(FacturacionDao.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${facturacion.tipoFactura}")
    private String tipoFactura;
    @Value("${facturacion.version}")
    private String version;
    @Value("${facturacion.serie}")
    private String serie;
    @Value("${facturacion.folio}")
    private long folio;
    @Value("${facturacion.tipoCambio}")
    private String tipoCambio;
    @Value("${facturacion.formaPago}")
    private String formaPago;
    @Value("${facturacion.moneda}")
    private String moneda;
    @Value("${facturacion.tipoComprobante}")
    private String tipoComprobante;
    @Value("${facturacion.metodoPago}")
    private String metodoPago;
    @Value("${facturacion.lugarExpedicion}")
    private String lugarExpedicion;
    @Value("${facturacion.regimenFiscal}")
    private String regimenFiscal;

    @Value("${facturacion.tipoFactura.desc}")
    private String tipoFacturaDesc;
    @Value("${facturacion.formaPago.desc}")
    private String formaPagoDesc;
    @Value("${facturacion.moneda.desc}")
    private String monedaDesc;
    @Value("${facturacion.tipoComprobante.desc}")
    private String tipoComprobanteDesc;
    @Value("${facturacion.metodoPago.desc}")
    private String metodoPagoDesc;
    @Value("${facturacion.regimenFiscal.desc}")
    private String regimenFiscalDesc;

    @Value("${facturacion.cliente.idEmpleadoSTP}")
    private String idEmpleadoSTP; //TODO find real empleadostp
    @Value("${facturacion.cliente.CFDI}")
    private String CFDI;
    @Value("${facturacion.cliente.CFDI.desc}")
    private String CFDIDesc;



    @Value("${facturacion.concepto.concepto}")
    private String cConcepto;
    @Value("${facturacion.concepto.unidad}")
    private String cClaveUnidad;
    @Value("${facturacion.concepto.unidad.desc}")
    private String cClaveUnidadDesc;
    @Value("${facturacion.concepto.claveProdServ}")
    private String cClaveProdServ;
    @Value("${facturacion.concepto.cantidad}")
    private String cCantidad;
    @Value("${facturacion.concepto.descripcion}")
    private String cDescripcion;

    @Value("${facturacion.concepto.impuestosDescripcion}")
    private String ciDescripcion;
    @Value("${facturacion.concepto.codigoSat}")
    private String cCodigoSat;
    @Value("${facturacion.concepto.descripcionSat}")
    private String cDescripcionSat;

    @Value("${facturacion.impuesto.iva}")
    private Double iva;
    @Value("${facturacion.impuesto.clave}")
    private String iClave;
    @Value("${facturacion.impuesto.desc}")
    private String iDesc;


    @Autowired
    private DataSourceConfig dataSourceConfig;

    public FacturaDTO generarFactura(String email, int months) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call GET_FACTURA_COMISION(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,months);
            cStmt.setString(2,email);

            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();
            resultSet.next();
            return generarFacturaDTO(resultSet);

        }catch (SQLException e){
            LOGGER.error("Error obteniendo datos para generar la factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public int generarStatusFactura(FacturaDTO facturaDTO) throws BusinessException {


        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call INSERT_FACTURA(?,?,?,?,?,?,?);";

        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setString(1, facturaDTO.getInicioPeriodo() );
            cStmt.setDouble(2, facturaDTO.getTotales().getSubtotal().doubleValue()*iva);
            cStmt.setDouble(3, facturaDTO.getTotales().getSubtotal().doubleValue());
            cStmt.setInt(4, FacturaEnum.FACTURANDO.getId());
            cStmt.setInt(5, facturaDTO.getClientId());
            cStmt.setInt(6, facturaDTO.getPersId());
            cStmt.setString(7,facturaDTO.getFecIngreso());

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            rs.next();
            int id = rs.getInt("ID_EXTRAER");
            return id;
        }catch (SQLException e){
            LOGGER.error("Error creando tabla factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }

    }

    public void actualizarStatusFactura(int idFactura, int status) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call UPDATE_FACTURA_STATUS(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idFactura);
            cStmt.setInt(2,status);

            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error actualizando el status de la factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public void actualizaArchivoFactura(int idFactura, int status, int idArchivo) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call UPDATE_FACTURA_ARCHIVO(?,?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setInt(1,idFactura);
            cStmt.setInt(2,status);
            cStmt.setInt(3,idArchivo);

            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error insertando el id del archivo factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public boolean checarSiExisteFactura(String email, int months)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call CHECAR_FACTURA(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,months);
            cStmt.setString(2,email);

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            rs.next();
            int id = rs.getInt("EXISTS_FACT");
            LOGGER.error(id+" EXIST:"+(id==1));
            return id==1;

        }catch (SQLException e){
            LOGGER.error("Error checando si la factura existe: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public boolean validarArchivoFactura(int idArchivo,String email)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call VALIDAR_FACTURA_ARCHIVO(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setInt(1,idArchivo);
            cStmt.setString(2,email);

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            rs.next();
            int id = rs.getInt("EXISTS_FACT");
            LOGGER.error(id+" EXIST:"+(id==1));
            return id==1;

        }catch (SQLException e){
            LOGGER.error("Error checando si la factura existe: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public int obtenerArchivo(String email, int months) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call OBTENER_FACTURA_ARCHIVO(?,?);";
        try(Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setInt(1,months);
            cStmt.setString(2,email);


            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            rs.next();
            int id = rs.getInt("id_arch");
            return id;

        }catch (SQLException e){
            LOGGER.error("Error obteniendo el id del archivo de la factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    private FacturaDTO generarFacturaDTO(ResultSet resultSet) throws BusinessException {
        try {

            Properties properties = new Properties();
            properties.load(new InputStreamReader(resourceLoader.getResource("classpath:application.properties").getInputStream(), Charset.forName("UTF-8")));


            BigDecimal comision = resultSet.getBigDecimal("comision").setScale(2, RoundingMode.CEILING);//subtotal
            BigDecimal ivaComision = comision.multiply( new BigDecimal(iva)).setScale(2, RoundingMode.CEILING); //impuesto
            BigDecimal ivaYcomision = comision.add(ivaComision).setScale(2, RoundingMode.CEILING);


            FacturaDTO facturaDTO = new FacturaDTO();

            facturaDTO.setPacTimbrado(resultSet.getString("timbrador"));
            facturaDTO.setTipoFactura(tipoFactura);
            facturaDTO.setTipoFacturaDesc(tipoFacturaDesc);
            facturaDTO.setVersion(version);
            facturaDTO.setSerie(serie);
            facturaDTO.setFolio(folio);
            facturaDTO.setTipoCambio(tipoCambio);
            facturaDTO.setFormaPago(formaPago);
            facturaDTO.setFormaPagoDesc(formaPagoDesc);
            facturaDTO.setMoneda(moneda);
            facturaDTO.setMonedaDesc(monedaDesc);
            facturaDTO.setTipoComprobante(tipoComprobante);
            facturaDTO.setTipoComprobanteDesc(tipoComprobanteDesc);
            facturaDTO.setMetodoPago(metodoPago);
            facturaDTO.setMetodoPagoDesc(properties.getProperty("facturacion.metodoPago.desc"));
            facturaDTO.setLugarExpedicion(lugarExpedicion);
            facturaDTO.setRegimenFiscal(regimenFiscal);
            facturaDTO.setRegimenFiscalDesc(regimenFiscalDesc);
            facturaDTO.setInicioPeriodo(resultSet.getString("firstDayPeriodo"));
            facturaDTO.setFinPeriodo(resultSet.getString("lastDayPeriodo"));
            facturaDTO.setClientId(resultSet.getInt("id1"));
            facturaDTO.setPersId(resultSet.getInt("id2"));
            facturaDTO.setFecIngreso(resultSet.getString("id3"));



            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setSegundoApellido(resultSet.getString("apellido_mat"));
            clienteDTO.setPrimerApellido(resultSet.getString("apellido_pat"));
            clienteDTO.setNombre(resultSet.getString("nombre"));
            clienteDTO.setNombreCompleto(resultSet.getString("nombre_completo"));
            clienteDTO.setNss(resultSet.getString("nss"));
            clienteDTO.setCurp(resultSet.getString("curp"));
            clienteDTO.setCorreoElectronico(resultSet.getString("email"));
            clienteDTO.setRfc(resultSet.getString("rfc"));
            clienteDTO.setPeriodicidad(resultSet.getString("periodicidad"));
            clienteDTO.setIdEmpleadoSTP(idEmpleadoSTP);
            clienteDTO.setUsoCFDI(CFDI);
            clienteDTO.setUsoCFDIDesc(CFDIDesc);


            ConceptoDTO conceptoDTO = new ConceptoDTO();
            conceptoDTO.setCantidad(new BigDecimal(cCantidad).setScale(2, RoundingMode.CEILING));
            conceptoDTO.setClaveProdServ(cClaveProdServ);
            conceptoDTO.setConcepto(cConcepto);
            conceptoDTO.setUnidad(cClaveUnidad);
            conceptoDTO.setUnidadDesc(cClaveUnidadDesc);
            conceptoDTO.setCodigoSat(cCodigoSat);
            conceptoDTO.setImpuestosDescripcion(ciDescripcion);
            conceptoDTO.setDescripcionSat(cDescripcionSat);
            conceptoDTO.setImporteTotal(comision);
            conceptoDTO.setImporte(comision);
            conceptoDTO.setDescripcion(properties.getProperty("facturacion.concepto.descripcion"));

            List<ImpuestoDTO> impuestos = new ArrayList<>();
            ImpuestoDTO impuestoDTO = new ImpuestoDTO();
            impuestoDTO.setIdImpuesto(new Integer(1));
            impuestoDTO.setImpuestoClave(iClave);
            impuestoDTO.setImpuestoDescripcion(iDesc);
            impuestoDTO.setPorcentajeImpuesto(new BigDecimal(iva).setScale(6, RoundingMode.FLOOR));
            impuestoDTO.setTotalImpuesto(ivaComision);
            impuestos.add(impuestoDTO);
            conceptoDTO.setImpuestos(impuestos);

            byte[] bytes = cDescripcion.getBytes(StandardCharsets.UTF_8);

            TotalDTO totalDTO = new TotalDTO();
            totalDTO.setSubtotal(comision);
            totalDTO.setSumaImpuestos(ivaComision);
            totalDTO.setTotal(ivaYcomision);

            List<ConceptoDTO> conceptos = new ArrayList<>();
            conceptos.add(conceptoDTO);
            facturaDTO.setConceptos(conceptos);
            facturaDTO.setCliente(clienteDTO);
            facturaDTO.setTotales(totalDTO);

            //facturaDTO.setIdConsar();
            facturaDTO.setFechaDispersion(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            return facturaDTO;
        }catch (SQLException | IOException e){
            LOGGER.error("Error obteniendo datos para generar la factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }

    }
}
