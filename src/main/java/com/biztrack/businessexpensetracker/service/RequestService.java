package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IExpenses;
import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResDepartmentDTO;
import com.biztrack.businessexpensetracker.dto.response.ResRequestDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValDepartmentDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValRequestDTO;
import com.biztrack.businessexpensetracker.model.*;
import com.biztrack.businessexpensetracker.repo.DepartmentRepo;
import com.biztrack.businessexpensetracker.repo.RequestDetailRepo;
import com.biztrack.businessexpensetracker.repo.RequestRepo;
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
 * Kode Modul : 05
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class RequestService implements IService<Request>, IExpenses {
    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private RequestDetailRepo requestDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(Request expensesRequest, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);

            if (expensesRequest == null) {
                return GlobalResponse.objectIsNull("BIZ05FV001", request);
            }

            User user = new User();
            user.setId(Long.parseLong(tokenData.get("id").toString()));

            Status status = new Status();
            status.setId(1L);

            expensesRequest.setUser(user);
            expensesRequest.setStatus(status);
            expensesRequest.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            expensesRequest = requestRepo.save(expensesRequest);

            for (RequestDetail requestDetail: expensesRequest.getRequestDetails()){
                Request relRequest = new Request();
                relRequest.setId(expensesRequest.getId());
                requestDetail.setRequest(relRequest);
                requestDetail.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));

                requestDetailRepo.save(requestDetail);
            }

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(
            Long id,
            Request expensesRequest,
            HttpServletRequest request
    ) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ05FV011", request);
            }
            if (expensesRequest == null) {
                return GlobalResponse.objectIsNull("BIZ05FV012", request);
            }
            Optional<Request> opRequest = requestRepo.findById(id);
            if (!opRequest.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ05FV013", request);
            }

            Request requestDB = opRequest.get();
            if (requestDB.getStatus().getId() != 2L &&
                    requestDB.getStatus().getId() != 4L){
                return GlobalResponse.updatedFailed("BIZ05FV014", request);
            }

            Status status = new Status();
            status.setId(1L);

            requestDB.setStatus(status);
            requestDB.setPurpose(expensesRequest.getPurpose());
            requestDB.setAmount(expensesRequest.getAmount());
            requestDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));

            for (RequestDetail requestDetail: expensesRequest.getRequestDetails()){
                Optional<RequestDetail> opRequestDetail = requestDetailRepo.findById(requestDetail.getId());
                if (opRequestDetail.isPresent()){
                    RequestDetail requestDetailDB = opRequestDetail.get();
                    requestDetailDB.setAmount(requestDetail.getAmount());
                    requestDetailDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
                }
            }
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE011", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ05FV021", request);
            }
            Optional<Request> opRequest = requestRepo.findById(id);
            if (!opRequest.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ05FV022", request);
            }

            Request requestDB = opRequest.get();
            for (RequestDetail requestDetail: requestDB.getRequestDetails()){
                requestDetailRepo.deleteById(requestDetail.getId());
            }
            requestRepo.deleteById(id);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 -040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Request> page;
        List<ResRequestDTO> listDTO;
        Map<String, Object> data;
        try {
            page = requestRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ05FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 101 - 110
    @Override
    public ResponseEntity<Object> cancel(Long id, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Request> opRequest = requestRepo.findById(id);
            if (!opRequest.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ05FV101", request);
            }

            Request requestDB = opRequest.get();
            if (requestDB.getStatus().getId() != 1L){
                return GlobalResponse.updatedFailed("BIZ05FV102", request);
            }

            Status status = new Status();
            status.setId(2L);
            status.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));

            requestDB.setStatus(status);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE101", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 111 - 120
    @Override
    public ResponseEntity<Object> approve(Long id, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Request> opRequest = requestRepo.findById(id);
            if (!opRequest.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ05FV111", request);
            }

            Request requestDB = opRequest.get();
            if (requestDB.getStatus().getId() != 3L){
                return GlobalResponse.updatedFailed("BIZ05FV112", request);
            }

            Status status = new Status();
            status.setId(5L);

            requestDB.setStatus(status);
            requestDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE111", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 121 - 130
    @Override
    public ResponseEntity<Object> reject(Long id, String comment, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Request> opRequest = requestRepo.findById(id);
            if (!opRequest.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ05FV121", request);
            }

            Request requestDB = opRequest.get();
            if (requestDB.getStatus().getId() != 1L){
                return GlobalResponse.savingFailed("BIZ05FV122", request);
            }

            Status status = new Status();
            status.setId(4L);

            requestDB.setComment(comment);
            requestDB.setStatus(status);
            requestDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE111", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 131 - 140
    public ResponseEntity<Object> approveManager(Long id, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            Optional<Request> opRequest = requestRepo.findById(id);
            if (!opRequest.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ05FV131", request);
            }

            Request requestDB = opRequest.get();
            if (requestDB.getStatus().getId() != 1L){
                return GlobalResponse.updatedFailed("BIZ05FV132", request);
            }

            Status status = new Status();
            status.setId(3L);

            requestDB.setStatus(status);
            requestDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ05FE131", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    public Request mapToRequest(ValRequestDTO valRequestDTO) {
        return modelMapper.map(valRequestDTO, Request.class);
    }

    public List<ResRequestDTO> mapToDTO(List<Request> listRequest) {
        return modelMapper.map(listRequest, new TypeToken<List<ResRequestDTO>>() {
        }.getType());
    }
}
