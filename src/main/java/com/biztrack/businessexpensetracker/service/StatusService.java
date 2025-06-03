package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResStatusDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValStatusDTO;
import com.biztrack.businessexpensetracker.model.Status;
import com.biztrack.businessexpensetracker.repo.StatusRepo;
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
 * Kode Modul : 04
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class StatusService implements IService<Status> {

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(Status status, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (status == null) {
                return GlobalResponse.objectIsNull("BIZ04FV001", request);
            }

            Optional<Status> opStatus = statusRepo.findByName(status.getName());
            if (opStatus.isPresent()) {
                return GlobalResponse.dataExists("BIZ04FV002", request);
            }
            status.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            statusRepo.save(status);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ04FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(Long id, Status status, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ04FV011", request);
            }
            if (status == null) {
                return GlobalResponse.objectIsNull("BIZ04FV012", request);
            }
            Optional<Status> opStatus = statusRepo.findById(id);
            if (!opStatus.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ04FV013", request);
            }
            Status statusDB = opStatus.get();
            statusDB.setName(status.getName());
            statusDB.setDescription(status.getDescription());
            statusDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ04FE001", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ04FV021", request);
            }
            Optional<Status> opStatus = statusRepo.findById(id);
            if (!opStatus.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ04FV022", request);
            }
            statusRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ04FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 - 040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Status> page;
        List<ResStatusDTO> listDTO;
        Map<String, Object> data;
        try {
            page = statusRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ04FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ04FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    public Status mapToStatus(ValStatusDTO valStatusDTO) {
        return modelMapper.map(valStatusDTO, Status.class);
    }

    public List<ResStatusDTO> mapToDTO(List<Status> listStatus) {
        return modelMapper.map(listStatus, new TypeToken<List<ResStatusDTO>>() {
        }.getType());
    }

}
