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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

            List<Role> roleList = roleRepo.findByNameContainsIgnoreCase(role.getName());
            if (!roleList.isEmpty()) {
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
            roleDB.setListMenu(role.getListMenu());
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

    // 041 - 050
    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Role> page;
        List<ResRoleDTO> listDTO;
        Map<String, Object> data;
        try {
            if (!isValidSort(pageable.getSort().stream().toList().getFirst().getProperty())){
                return GlobalResponse.dataNotFound("BIZ01FV041", request);
            }
            page = switch (columnName) {
                case "name" -> roleRepo.findByNameContainsIgnoreCase(value, pageable);
                case "description" -> roleRepo.findByDescriptionContainsIgnoreCase(value, pageable);
                case "menu" -> roleRepo.findByListMenu_NameContainsIgnoreCase(value, pageable);
                default -> roleRepo.findAll(pageable);
            };
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ01FV042", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, columnName, value);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ01FE041", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 051 - 060
    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResRoleDTO response;
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ02FV051", request);
            }
            Optional<Role> opRole = roleRepo.findById(id);
            if (!opRole.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ02FV052", request);
            }
            Role roleDB = opRole.get();
            response = mapToDTO(roleDB);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ02FE051", request);
        }
        return GlobalResponse.foundData(response, request);
    }

    public Boolean isValidSort(String sort){
        return switch (sort) {
            case "id", "name", "description" -> true;
            default -> false;
        };
    }

    public Role mapToRole(ValRoleDTO valRoleDTO) {
        return modelMapper.map(valRoleDTO, Role.class);
    }

    public List<ResRoleDTO> mapToDTO(List<Role> listRole) {
        return modelMapper.map(listRole, new TypeToken<List<ResRoleDTO>>() {
        }.getType());
    }

    public ResRoleDTO mapToDTO(Role role) {
        return modelMapper.map(role, ResRoleDTO.class);
    }
}
