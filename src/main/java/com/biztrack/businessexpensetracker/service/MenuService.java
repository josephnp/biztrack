package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.report.RepMenuDTO;
import com.biztrack.businessexpensetracker.dto.report.RepRoleDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValMenuDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.Menu;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.repo.MenuRepo;
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
public class MenuService implements IService<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        try {
            Optional<Menu> opMenu = menuRepo.findByName(menu.getName());
            if (opMenu.isPresent()) {
                return new ResponseHandler().handleResponse("Data Already Exist !!", HttpStatus.BAD_REQUEST, null, "ROL04FV001", request);
            }
            if (menu == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "ROL04FV002", request);
            }
            menuRepo.save(menu);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDisimpan("ROL04FE001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("ROL04FV011", request);
            }
            if (menu == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "DEP04FV012", request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if (!opMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV013", request);
            }
            Menu menuDB = opMenu.get();
            menuDB.setName(menu.getName());
            menuDB.setDescription(menu.getDescription());
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
            Optional<Menu> opMenu = menuRepo.findById(id);
            if (!opMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("ROL04FV022", request);
            }
            menuRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDihapus("ROL04FE021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        List<RepMenuDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = menuRepo.findAll(pageable);
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

    public Menu mapToMenu(ValMenuDTO valMenuDTO) {
        return modelMapper.map(valMenuDTO, Menu.class);
    }

    public List<RepMenuDTO> mapToDTO(List<Menu> listMenu) {
        return modelMapper.map(listMenu, new TypeToken<List<RepMenuDTO>>() {
        }.getType());
    }



}
