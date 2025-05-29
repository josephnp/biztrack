package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.report.RepStatusDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValStatusDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.Status;
import com.biztrack.businessexpensetracker.repo.StatusRepo;
import com.biztrack.businessexpensetracker.utils.GlobalResponse;
import com.biztrack.businessexpensetracker.utils.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StatusService implements IService<Status> {

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    @Override
    public ResponseEntity<Object> save(Status status, HttpServletRequest request) {
        try {
            Optional<Status> opStatus = statusRepo.findByName(status.getName());
            if (opStatus.isPresent()) {
                return new ResponseHandler().handleResponse("Data Already Exist !!", HttpStatus.BAD_REQUEST, null, "DEP04FV001", request);
            }
            if (status == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "DEP04FV002", request);
            }
            statusRepo.save(status);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDisimpan("STA04FE001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Status status, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("STA04FV011", request);
            }
            if (status == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "DEP04FV012", request);
            }
            Optional<Status> opStatus = statusRepo.findById(id);
            if (!opStatus.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("STA04FV013", request);
            }
            Status StatusDB = opStatus.get();
            StatusDB.setName(status.getName());
            StatusDB.setDescription(status.getDescription());
        } catch (Exception e) {
            return GlobalResponse.dataGagalDiubah("STA04FE002", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("STA04FV021", request);
            }
            Optional<Status> opStatus = statusRepo.findById(id);
            if (!opStatus.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("DEP04FV022", request);
            }
            statusRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDihapus("DEP04FE021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Status> page = null;
        List<Status> list = null;
        List<RepStatusDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = statusRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("AUT04FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.terjadiKesalahan("AUT04FE031", request);
        }
        return GlobalResponse.dataDitemukan(data, request);
    }

    public Status mapToStatus(ValStatusDTO valStatusDTO) {
        return modelMapper.map(valStatusDTO, Status.class);
    }

    public List<RepStatusDTO> mapToDTO(List<Status> listStatus) {
        return modelMapper.map(listStatus, new TypeToken<List<RepStatusDTO>>() {
        }.getType());
    }

}
