package com.aura.qamm.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aura.qamm.dto.*;
import com.aura.qamm.util.JSONPathUtil;

public interface QuincenaTransformer {

    public static Colaborador transformConsultaSaldo(String rawDBJSON) {

        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("saldo");
        jsonPaths.add("fechaLimite");
        jsonPaths.add("idColaborador");
        jsonPaths.add("nombre");
        jsonPaths.add("apellido_pat");
        jsonPaths.add("apellido_mat");
        jsonPaths.add("importeMinimo");
        jsonPaths.add("importeMaximo");
        jsonPaths.add("mensajeApp");
        jsonPaths.add("tieneDeduccionPrevia");

        Map<String,String> pathValues = JSONPathUtil.evaluateJSONPath(rawDBJSON,jsonPaths);

        for (String mapPVkey : pathValues.keySet()){
            String mapPVval = pathValues.get(mapPVkey);
            //apiJSON = apiJSON.replace("$" + mapPVkey,mapPVval);
        }

        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(Integer.parseInt(pathValues.get("idColaborador")));
        //colaborador.setMontoDisponible(Double.parseDouble(pathValues.get("saldo")));
        colaborador.setMontoDisponible(pathValues.get("saldo"));
        colaborador.setNombre(pathValues.get("nombre"));
        colaborador.setaMaterno(pathValues.get("apellido_mat"));
        colaborador.setaPaterno(pathValues.get("apellido_pat"));
        colaborador.setFechaLimite(pathValues.get("fechaLimite"));
        colaborador.setMonto_min(pathValues.get("importeMinimo"));
        colaborador.setMonto_max(pathValues.get("importeMaximo"));
        colaborador.setMensajeApp(pathValues.get("mensajeApp"));
        colaborador.setTieneDeduccionPrevia(pathValues.get("tieneDeduccionPrevia"));
        //colaborador.set
        //colaborador.set
        //colaborador.set

        return colaborador;
    }

    public static ImporteAnticipo transformComisionAnticipo(String dbJSON) {
        //{"saldo": 1275.00, "total": 129.50, "comision": 9.50, "mensajeApp": "SUCCESS: RÃ¡scale mi JSON",
        // "fechaLimite": "2021-02-01 07:27:18.000000", "importeValido": 120.00}
        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("saldo");
        jsonPaths.add("total");
        jsonPaths.add("comision");
        jsonPaths.add("mensajeApp");
        jsonPaths.add("fechaLimite");
        jsonPaths.add("importeValido");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);

        ImporteAnticipo importeAnticipo = new ImporteAnticipo();
        importeAnticipo.setImporteComision(pathValues.get("comision"));
        importeAnticipo.setImporteTotal(pathValues.get("total"));
        importeAnticipo.setIdColaborador(null);
        importeAnticipo.setImporteSolicitado(null);

        return importeAnticipo;
    }

    public static CuentaBancaria transformCuentaBancaria(String dbJSON) {
        //{"banco": "BANCOPPEL", "mensajeApp": "SUCCESS: RÃ¡scale mi JSON", "etiquetaTCAQ": "\"TÃ©rminos y Condiciones Anticipo de Quincena.\"", "numeroCuenta": "3784056779",
        // "valorParametroTCAQ": "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
        // Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit
        // in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
        // Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\""}
        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("banco");
        //jsonPaths.add("mensajeApp");
        jsonPaths.add("numeroCuenta");
        //jsonPaths.add("valorParametroTCAQ");
        //jsonPaths.add("");
        //jsonPaths.add("");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);

        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setCuentaBanco(pathValues.get("numeroCuenta"));
        //cuentaBancaria.setIdBanco(null);
        //cuentaBancaria.setIdColaborador(null);
        cuentaBancaria.setNombreBanco(pathValues.get("banco"));

        return cuentaBancaria;
    }

    public static SolicitudAnticipo transformSolitiudAnticipo(String dbJSON){
        //{"mensajeApp": "Ã‰xito total Judah!"}
        //{"clnt_id": 186, "id_anti": 125, "pers_id": 225, "mensajeApp": "?xito total Judah!",
        // "fchSolicita": "2021-02-28 19:13:54.000000", "fch_ingreso": "0001-01-21",
        // "conceptoPago": "\"Adelanto de N?mina\"",
        // "folio_origen": "AQ-1-125", "claveAutorizacion": "En proceso..",
        // "cuentaBeneficiario": "002996789012345678", "nombreBeneficiario": "Ulises Contreras Carrera",
        // "referenciaNumerica": 125, "rfcCurpBeneficiario": "COCU920206NR2"}
        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("mensajeApp");
        jsonPaths.add("fchSolicita");
        jsonPaths.add("claveAutorizacion");

        jsonPaths.add("clnt_id");
        jsonPaths.add("id_anti");
        jsonPaths.add("pers_id");
        jsonPaths.add("fch_ingreso");
        jsonPaths.add("conceptoPago");
        jsonPaths.add("folio_origen");
        jsonPaths.add("cuentaBeneficiario");
        jsonPaths.add("nombreBeneficiario");
        jsonPaths.add("referenciaNumerica");
        jsonPaths.add("rfcCurpBeneficiario");
        jsonPaths.add("banco");
        jsonPaths.add("dispersor");
        jsonPaths.add("montoAculumadoDistribucionArgyle");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);

        SolicitudAnticipo solicitudAnticipo = new SolicitudAnticipo();
        solicitudAnticipo.setMensajeApp(pathValues.get("mensajeApp"));
        solicitudAnticipo.setFechaOperacion(pathValues.get("fchSolicita"));
        solicitudAnticipo.setClaveAutorizacion(pathValues.get("claveAutorizacion"));

        solicitudAnticipo.setClnt_id(pathValues.get("clnt_id"));
        solicitudAnticipo.setId_anti(pathValues.get("id_anti"));
        solicitudAnticipo.setPers_id(pathValues.get("pers_id"));
        solicitudAnticipo.setFch_ingreso(pathValues.get("fch_ingreso"));
        solicitudAnticipo.setConceptoPago(pathValues.get("conceptoPago"));
        solicitudAnticipo.setFolio_origen(pathValues.get("folio_origen"));
        solicitudAnticipo.setCuentaBeneficiario(pathValues.get("cuentaBeneficiario"));
        solicitudAnticipo.setNombreBeneficiario(pathValues.get("nombreBeneficiario"));
        solicitudAnticipo.setReferenciaNumerica(pathValues.get("referenciaNumerica"));
        solicitudAnticipo.setRfcCurpBeneficiario(pathValues.get("rfcCurpBeneficiario"));

        solicitudAnticipo.setInstitucionContraparte(pathValues.get("banco"));
        //solicitudAnticipo.setDispersor(Integer.parseInt(pathValues.get("dispersor")));
        solicitudAnticipo.setDispersor(0);
        solicitudAnticipo.setMontoAculumadoDistribucionArgyle(pathValues.get("montoAculumadoDistribucionArgyle"));

        return solicitudAnticipo;
    }

    public static Movimiento transformMovimiento(String dbJSON){
        //{"total": 7700, "mensajeApp": "Ã‰xito total Judah!",
        // "esPeriodoAnterior": false, "fch_transferencia": null, "clave_autorizacion": null}

        Movimiento movimiento = new Movimiento();
        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("total");
        jsonPaths.add("esPeriodoAnterior");
        jsonPaths.add("mensajeApp");
        jsonPaths.add("fch_transferencia");
        jsonPaths.add("clave_autorizacion");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);
        System.out.println("pathValues:" + pathValues);
        movimiento = new Movimiento();
        //movimiento.setIdMovimiento();
        //movimiento.setIdColaborador();
        movimiento.setClaveAutorizacion(pathValues.get("clave_autorizacion"));
        movimiento.setFechaOperacion(pathValues.get("fch_transferencia"));
        movimiento.setImporteTotal(Double.parseDouble(pathValues.get("total")));
        //movimiento.setImporteComision();
        //movimiento.setImporteTotal();
        //movimiento.setImporteSolicitado();
        movimiento.setPeriodoAnterior(Boolean.parseBoolean(pathValues.get("esPeriodoAnterior")));

        return movimiento;
    }

    public static Colaborador transformaColaboradoLoginr(String dbJSON){
        //{"saldo": null, "nombre": "Jerome", "mensajeApp": null, "fechaLimite": "2021-02-09 09:15:30.000000",
        // "apellido_mat": "Barton", "apellido_pat": "Mercer", "idColaborador": 1,
        // "nombreCompleto": "Jerome Mercer Barton"}

        Colaborador colaborador = new Colaborador();
        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("nombre");
        jsonPaths.add("apellido_mat");
        jsonPaths.add("apellido_pat");
        jsonPaths.add("idColaborador");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);
        System.out.println("pathValues:" + pathValues);
        colaborador.setaMaterno(pathValues.get("apellido_mat"));
        colaborador.setaPaterno(pathValues.get("apellido_pat"));
        colaborador.setNombre(pathValues.get("nombre"));
        colaborador.setIdColaborador(Integer.parseInt(pathValues.get("idColaborador")));

        return colaborador;
    }

    public static QUser transformQUser(String dbJSON){
        QUser quser = new QUser();
        //{"saldo": null, "nombre": "Jerome", "passWord": "123456", "mensajeApp": null,
        // "fechaLimite": "2021-02-10 13:05:27.000000", "apellido_mat": "Barton",
        // "apellido_pat": "Mercer", "idColaborador": 51,
        // "nombreCompleto": "Jerome Mercer Barton", "claveColaborador": 1}

        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("nombre");
        jsonPaths.add("passWord");
        jsonPaths.add("apellido_mat");
        jsonPaths.add("apellido_pat");
        jsonPaths.add("claveColaborador");

        jsonPaths.add("celular");
        jsonPaths.add("rfc");
        jsonPaths.add("argyle_user_id");



        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);
        quser.setAmaterno(pathValues.get("apellido_mat"));
        quser.setNombre(pathValues.get("nombre"));
        quser.setApaterno(pathValues.get("apellido_pat"));
        quser.setIdColaborador(pathValues.get("claveColaborador"));
        quser.setPassword(pathValues.get("passWord"));

        quser.setCelular(pathValues.get("celular"));
        quser.setRfc(pathValues.get("rfc"));
        quser.setArgyle_user_id(pathValues.get("argyle_user_id"));

        return quser;
    }

    public static QUser transformUseReg(String dbJSON){
        QUser quser = new QUser();
        //

        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("nombre");
        jsonPaths.add("apellido_mat");
        jsonPaths.add("apellido_pat");
        jsonPaths.add("mensajeApp");

        jsonPaths.add("errorCode");
        jsonPaths.add("mensajeApp_es");
        jsonPaths.add("mensajeApp_en");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);

        quser.setAmaterno(pathValues.get("apellido_mat"));
        quser.setApaterno(pathValues.get("apellido_pat"));
        quser.setNombre(pathValues.get("nombre"));
        quser.setMensajeApp(pathValues.get("mensajeApp"));

        quser.setErrorCode(pathValues.get("errorCode"));
        quser.setMensajeApp_es(pathValues.get("mensajeApp_es"));
        quser.setMensajeApp_en(pathValues.get("mensajeApp_en"));
        ////quser.setMensajeApp(pathValues.get("celular"));
        ////quser.setMensajeApp(pathValues.get("rfc"));

        return quser;
    }

    public static ResultExecution transformResultExec(String dbJSON){
        ResultExecution resultExecution = new ResultExecution();

        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("mensajeApp");
        jsonPaths.add("idColaborador");
        jsonPaths.add("claveColaborador");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);

        resultExecution.setMensajeApp(pathValues.get("mensajeApp"));
        resultExecution.setIdColaborador(Integer.parseInt(pathValues.get("claveColaborador")));

        return resultExecution;
    }

    public static Pago_STP transformResponsePagoSTP(String dbJSON){
        Pago_STP pago_stp = new Pago_STP();

        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("$['cadenaCifrada']");
        jsonPaths.add("$['cadena']");
        jsonPaths.add("$['response'].['resultado'].['id']");
        jsonPaths.add("$['response']");

        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);

        pago_stp.setCadenaSellada(pathValues.get("$['cadenaCifrada']"));
        pago_stp.setResultadoSTP(pathValues.get("$['response'].['resultado'].['id']"));
        pago_stp.setCadenaOriginal(pathValues.get("$['cadena']"));
        pago_stp.setResponse(pathValues.get("$['response']"));

        return pago_stp;
    }

    public static Map<String,String> getValueMap(String jsonString, List<String> jsonPaths){
        Map<String,String> pathValues = JSONPathUtil.evaluateJSONPath(jsonString,jsonPaths);

        for (String mapPVkey : pathValues.keySet()){
            String mapPVval = pathValues.get(mapPVkey);
        }
        return pathValues;
     }

    public static Pago_STP transformResponsePagoSTPBP(String dbJSON){


        List<String> jsonPaths = new ArrayList<String>();
        jsonPaths.add("$['cadenaSellada']");
        jsonPaths.add("$['cadenaOriginal']");
        jsonPaths.add("$['claveRastreo']");


        Map<String,String> pathValues = getValueMap(dbJSON,jsonPaths);
        Pago_STP pago_stp = new Pago_STP();
        pago_stp.setCadenaSellada(pathValues.get("$['cadenaSellada']"));
        pago_stp.setResultadoSTP(pathValues.get("$['claveRastreo']"));
        pago_stp.setCadenaOriginal(pathValues.get("$['cadenaOriginal']"));
        pago_stp.setResponse(dbJSON);

        return pago_stp;
    }
}
