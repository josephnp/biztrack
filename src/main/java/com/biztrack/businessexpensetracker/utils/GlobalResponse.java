package com.biztrack.businessexpensetracker.utils;

import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponse {

    public static ResponseEntity<Object> savingSuccess(HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data berhasil disimpan",
                HttpStatus.CREATED,
                null,
                null,
                request
        );
    }
    public static ResponseEntity<Object> savingSuccess(String message, HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                message,
                HttpStatus.CREATED,
                null,
                null,
                request
        );
    }
    public static ResponseEntity<Object> updatedSuccess(HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data berhasil diubah",
                HttpStatus.OK,
                null,
                null,
                request
        );
    }
    public static ResponseEntity<Object> deletedSuccess(HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data berhasil dihapus",
                HttpStatus.OK,
                null,
                null,
                request
        );
    }

    public static ResponseEntity<Object> savingFailed(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data gagal disimpan",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> updatedFailed(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data gagal diubah",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> deletedFailed(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data gagal dihapus",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> somethingWrong(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Terjadi kesalahan",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> foundData(Object data, HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data ditemukan",
                HttpStatus.OK,
                data,
                null,
                request
        );
    }

    public static ResponseEntity<Object> dataExists(String errorCode, HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data sudah ada",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> dataNotFound(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data not found",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode,
                request
        );
    }
    public static ResponseEntity<Object> objectIsNull(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "Data kosong",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> formatHarusExcel(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "FORMAT HARUS EXCEL",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> fileExcelKosong(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "FILE EXCEL KOSONG",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode,
                request
        );
    }
    public static ResponseEntity<Object> uploadFileExcelBerhasil(HttpServletRequest request){
        return new ResponseHandler().handleResponse(
                "UPLOAD FILE EXCEL BERHASIL",
                HttpStatus.CREATED,
                null,
                null,
                request
        );
    }

    public static void manualResponse(HttpServletResponse response, ResponseEntity<Object> resObject){
        try{
            response.getWriter().write(convertObjectToJson(resObject.getBody()));
            response.setStatus(resObject.getStatusCodeValue());
        }catch (Exception e){
        }
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if(object == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}