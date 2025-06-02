package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResRoleDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValRoleDTO;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.repo.RoleRepo;
import com.biztrack.businessexpensetracker.utils.GlobalFunction;
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

/**
 * Kode Platform / Aplikasi : BIZ
 * Kode Modul : 01
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class RoleService implements IService<Role> {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(Role role, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (role == null) {
                return GlobalResponse.objectIsNull("BIZ01FV001", request);
            }

            Optional<Role> opRole = roleRepo.findByName(role.getName());
            if (opRole.isPresent()) {
                return GlobalResponse.dataExists("BIZ01FV002", request);
            }

            role.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            roleRepo.save(role);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ01FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(Long id, Role role, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ01FV011", request);
            }
            if (role == null) {
                return GlobalResponse.objectIsNull("BIZ01FV012", request);
            }
            Optional<Role> opRole = roleRepo.findById(id);
            if (!opRole.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ01FV013", request);
            }
            Role roleDB = opRole.get();
            roleDB.setName(role.getName());
            roleDB.setDescription(role.getDescription());
            roleDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ01FE011", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ01FV021", request);
            }
            Optional<Role> opRole = roleRepo.findById(id);
            if (!opRole.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ01FV022", request);
            }
            roleRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ01FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 - 040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Role> page;
        List<ResRoleDTO> listDTO;
        Map<String, Object> data;
        try {
            page = roleRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ01FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ01FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    public Role mapToRole(ValRoleDTO valRoleDTO) {
        return modelMapper.map(valRoleDTO, Role.class);
    }

    public List<ResRoleDTO> mapToDTO(List<Role> listRole) {
        return modelMapper.map(listRole, new TypeToken<List<ResRoleDTO>>() {
        }.getType());
    }

}
