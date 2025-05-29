package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.report.RepRoleDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValRoleDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.repo.RoleRepo;
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
public class RoleService implements IService<Role> {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    @Override
    public ResponseEntity<Object> save(Role role, HttpServletRequest request) {
        try {
            Optional<Role> opRole = roleRepo.findByName(role.getName());
            if (opRole.isPresent()) {
                return new ResponseHandler().handleResponse("Data Already Exist !!", HttpStatus.BAD_REQUEST, null, "ROL04FV001", request);
            }
            if (role == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "ROL04FV002", request);
            }
            roleRepo.save(role);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDisimpan("ROL04FE001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Role role, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("ROL04FV011", request);
            }
            if (role == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpROLtus.BAD_REQUEST, null, "DEP04FV012", request);
            }
            Optional<Role> opRole = roleRepo.findById(id);
            if (!opRole.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV013", request);
            }
            Role roleDB = opRole.get();
            roleDB.setName(role.getName());
            roleDB.setDescription(role.getDescription());
        } catch (Exception e) {
            return GlobalResponse.dataGagalDiubah("ROL04FE002", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("ROL04FV021", request);
            }
            Optional<Role> opRole = roleRepo.findById(id);
            if (!opRole.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV022", request);
            }
            roleRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDihapus("ROL04FE021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Role> page = null;
        List<Role> list = null;
        List<RepRoleDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = roleRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.terjadiKesalahan("ROL04FE031", request);
        }
        return GlobalResponse.dataDitemukan(data, request);
    }

    public Role mapToRole(ValRoleDTO valRoleDTO) {
        return modelMapper.map(valRoleDTO, Role.class);
    }

    public List<RepRoleDTO> mapToDTO(List<Role> listRole) {
        return modelMapper.map(listRole, new TypeToken<List<RepRoleDTO>>() {
        }.getType());
    }

}
