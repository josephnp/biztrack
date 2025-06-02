package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.MenuDTO;
import com.biztrack.businessexpensetracker.dto.report.RepRoleDTO;
import com.biztrack.businessexpensetracker.dto.request.AssignMenuToRoleDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValRoleDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
//import com.biztrack.businessexpensetracker.model.Menu;
import com.biztrack.businessexpensetracker.model.Role;
//import com.biztrack.businessexpensetracker.repo.MenuRepo;
import com.biztrack.businessexpensetracker.repo.RoleRepo;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService implements IService<Role> {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private MenuRepo menuRepo;

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
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "DEP04FV012", request);
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

    public ResponseEntity<Object> getMenusByRoleId(Long roleId, Pageable pageable, HttpServletRequest request){
        Page<Menu> page = null;
        List<Menu> list = null;
        List<MenuDTO> listDTO = null;
        Map<String, Object> data = null;

        try{
            if (roleId == null) {
                return GlobalResponse.objectIsNull("ROL04FV021", request);
            }
            Optional<Role> opRole = roleRepo.findById(roleId);
            if (opRole.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV031", request);
            }

            list = new ArrayList<>(opRole.get().getMenus());


            int total = list.size();
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), total);

            if (start > end || start >= total) {
                return GlobalResponse.dataTidakDitemukan("RMN04FV041", request);
            }

            List<Menu> pagedList = list.subList(start, end);
            listDTO = mapToMenuDTO(pagedList);

            page = new PageImpl<>(pagedList, pageable, total);
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.terjadiKesalahan("RMN04FE041", request);
        }
        return GlobalResponse.dataDitemukan(data, request);
    }

    @Transactional
    public ResponseEntity<Object> assignMenusToRole(AssignMenuToRoleDTO assignMenuToRoleDTO, HttpServletRequest request) {
        try {
            Optional<Role> opRole = roleRepo.findById(assignMenuToRoleDTO.getRoleId());
            if (opRole.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV031", request);
            }

            List<Menu> menus = menuRepo.findAllById(assignMenuToRoleDTO.getMenuId());

            if (menus.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("RMN04FV051", request);
            }

            Role role = opRole.get();
            // Replace seluruh menu yang dimiliki role dengan menu baru dari request
            role.setMenus(new HashSet<>(menus));

            roleRepo.save(role);  // Simpan perubahan relasi

            return GlobalResponse.dataBerhasilDiubah(request);
        } catch (Exception e) {
            return GlobalResponse.terjadiKesalahan("RMN04FE051", request);
        }
    }



    public Role mapToRole(ValRoleDTO valRoleDTO) {
        return modelMapper.map(valRoleDTO, Role.class);
    }

    public List<RepRoleDTO> mapToDTO(List<Role> listRole) {
        return modelMapper.map(listRole, new TypeToken<List<RepRoleDTO>>() {
        }.getType());
    }

//    private List<MenuDTO> mapToMenuDTO(List<Menu> menus) {
//        return menus.stream()
//                .map(menu -> modelMapper.map(menu, MenuDTO.class))
//                .collect(Collectors.toList());
//    }

}
