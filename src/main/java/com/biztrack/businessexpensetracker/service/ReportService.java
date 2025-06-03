package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IExpenses;
import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResReportDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValReportDTO;
import com.biztrack.businessexpensetracker.model.*;
import com.biztrack.businessexpensetracker.repo.ReportDetailRepo;
import com.biztrack.businessexpensetracker.repo.ReportRepo;
import com.biztrack.businessexpensetracker.utils.GlobalFunction;
import com.biztrack.businessexpensetracker.utils.GlobalResponse;
import com.biztrack.businessexpensetracker.utils.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Kode Platform / Aplikasi : BIZ
 * Kode Modul : 06
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class ReportService implements IService<Report>, IExpenses {
    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ReportDetailRepo reportDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(Report report, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (report == null) {
                return GlobalResponse.objectIsNull("BIZ06FV001", request);
            }

            Status status = new Status();
            status.setId(3L);
            report.setStatus(status);
            report.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            report = reportRepo.save(report);

            for (ReportDetail reportDetail: report.getReportDetails()){
                reportDetail.getReport().setId(report.getId());
                reportDetail.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
                reportDetailRepo.save(reportDetail);
            }

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(
            Long id,
            Report report,
            HttpServletRequest request
    ) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ06FV011", request);
            }
            if (report == null) {
                return GlobalResponse.objectIsNull("BIZ06FV012", request);
            }
            Optional<Report> opReport = reportRepo.findById(id);
            if (!opReport.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ06FV013", request);
            }

            Report reportDB = opReport.get();
            if (reportDB.getStatus().getId() != 2L &&
                    reportDB.getStatus().getId() != 4L){
                return GlobalResponse.updatedFailed("BIZ05FV014", request);
            }

            Status status = new Status();
            status.setId(3L);
            report.setStatus(status);

            reportDB.setStatus(report.getStatus());
            reportDB.setAmount(report.getAmount());
            reportDB.setRefundAmount(report.getRefundAmount());
            reportDB.setRefundReceiptURL(report.getRefundReceiptURL());
            reportDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));

            for (ReportDetail reportDetail: report.getReportDetails()){
                Optional<ReportDetail> opReportDetail = reportDetailRepo.findById(reportDetail.getId());
                if (opReportDetail.isPresent()){
                    ReportDetail reportDetailDB = opReportDetail.get();
                    reportDetailDB.setAmount(reportDetail.getAmount());
                    reportDetailDB.setReceiptURL(reportDetail.getReceiptURL());
                    reportDetailDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
                }
            }
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE011", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ06FV021", request);
            }
            Optional<Report> opReport = reportRepo.findById(id);
            if (!opReport.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ06FV022", request);
            }

            Report repostDB = opReport.get();
            for (ReportDetail reportDetail: repostDB.getReportDetails()){
                reportDetailRepo.deleteById(reportDetail.getId());
            }
            reportRepo.deleteById(id);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 -040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Report> page;
        List<ResReportDTO> listDTO;
        Map<String, Object> data;
        try {
            page = reportRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ06FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 101 - 110
    @Override
    public ResponseEntity<Object> cancel(Long id, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Report> opReport = reportRepo.findById(id);
            if (!opReport.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ06FV101", request);
            }

            Report reportDB = opReport.get();
            if (reportDB.getStatus().getId() != 3L){
                return GlobalResponse.updatedFailed("BIZ06FV102", request);
            }

            Status status = new Status();
            status.setId(2L);

            reportDB.setStatus(status);
            reportDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE101", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 111 - 120
    @Override
    public ResponseEntity<Object> approve(Long id, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Report> opReport = reportRepo.findById(id);
            if (!opReport.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ06FV111", request);
            }

            Report reportDB = opReport.get();
            if (reportDB.getStatus().getId() != 3L){
                return GlobalResponse.updatedFailed("BIZ06FV112", request);
            }

            Status status = new Status();
            status.setId(5L);

            reportDB.setStatus(status);
            reportDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE111", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 121 - 130
    @Override
    public ResponseEntity<Object> reject(Long id, String comment, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Report> opReport = reportRepo.findById(id);
            if (!opReport.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ06FV121", request);
            }

            Report reportDB = opReport.get();
            if (reportDB.getStatus().getId() != 3L){
                return GlobalResponse.savingFailed("BIZ06FV122", request);
            }

            Status status = new Status();
            status.setId(4L);

            reportDB.setComment(comment);
            reportDB.setStatus(status);
            reportDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ06FE111", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    public Report mapToReport(ValReportDTO valReportDTO) {
        return modelMapper.map(valReportDTO, Report.class);
    }

    public List<ResReportDTO> mapToDTO(List<Report> listReport) {
        return modelMapper.map(listReport, new TypeToken<List<ResReportDTO>>() {
        }.getType());
    }
}
