package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.MenuDTO;
import com.biztrack.businessexpensetracker.dto.request.AssignMenuToRoleDTO;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

//    public ResponseEntity<Object> getMenusByRoleId(Long roleId, Pageable pageable, HttpServletRequest request){
//        Page<Menu> page = null;
//        List<Menu> list = null;
//        List<MenuDTO> listDTO = null;
//        Map<String, Object> data = null;
//
//        try{
//            if (roleId == null) {
//                return GlobalResponse.objectIsNull("ROL04FV021", request);
//            }
//            Optional<Role> opRole = roleRepo.findById(roleId);
//            if (opRole.isEmpty()) {
//                return GlobalResponse.dataTidakDitemukan("ROL04FV031", request);
//            }
//
//            list = new ArrayList<>(opRole.get().getMenus());
//
//
//            int total = list.size();
//            int start = (int) pageable.getOffset();
//            int end = Math.min(start + pageable.getPageSize(), total);
//
//            if (start > end || start >= total) {
//                return GlobalResponse.dataTidakDitemukan("RMN04FV041", request);
//            }
//
//            List<Menu> pagedList = list.subList(start, end);
//            listDTO = mapToMenuDTO(pagedList);
//
//            page = new PageImpl<>(pagedList, pageable, total);
//            data = tp.transformPagination(listDTO, page, "id", "");
//        } catch (Exception e) {
//            return GlobalResponse.terjadiKesalahan("RMN04FE041", request);
//        }
//        return GlobalResponse.dataDitemukan(data, request);
//    }
//
//    @Transactional
//    public ResponseEntity<Object> assignMenusToRole(AssignMenuToRoleDTO assignMenuToRoleDTO, HttpServletRequest request) {
//        try {
//            Optional<Role> opRole = roleRepo.findById(assignMenuToRoleDTO.getRoleId());
//            if (opRole.isEmpty()) {
//                return GlobalResponse.dataTidakDitemukan("ROL04FV031", request);
//            }
//
//            List<Menu> menus = menuRepo.findAllById(assignMenuToRoleDTO.getMenuId());
//
//            if (menus.isEmpty()) {
//                return GlobalResponse.dataTidakDitemukan("RMN04FV051", request);
//            }
//
//            Role role = opRole.get();
//            // Replace seluruh menu yang dimiliki role dengan menu baru dari request
//            role.setMenus(new HashSet<>(menus));
//
//            roleRepo.save(role);  // Simpan perubahan relasi
//
//            return GlobalResponse.dataBerhasilDiubah(request);
//        } catch (Exception e) {
//            return GlobalResponse.terjadiKesalahan("RMN04FE051", request);
//        }
//    }

    public Role mapToRole(ValRoleDTO valRoleDTO) {
        return modelMapper.map(valRoleDTO, Role.class);
    }

    public List<ResRoleDTO> mapToDTO(List<Role> listRole) {
        return modelMapper.map(listRole, new TypeToken<List<ResRoleDTO>>() {
        }.getType());
    }

//    private List<MenuDTO> mapToMenuDTO(List<Menu> menus) {
//        return menus.stream()
//                .map(menu -> modelMapper.map(menu, MenuDTO.class))
//                .collect(Collectors.toList());
//    }

}
