package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResMenuDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValMenuDTO;
import com.biztrack.businessexpensetracker.model.Menu;
import com.biztrack.businessexpensetracker.repo.MenuRepo;
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
 * Kode Modul : 07
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class MenuService implements IService<Menu> {
    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (menu == null) {
                return GlobalResponse.objectIsNull("BIZ07FV001", request);
            }

            List<Menu> menuList = menuRepo.findByNameContainsIgnoreCase(menu.getName());
            if (!menuList.isEmpty()) {
                return GlobalResponse.dataExists("BIZ07FV002", request);
            }
            menu.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            menuRepo.save(menu);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ07FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ07FV011", request);
            }
            if (menu == null) {
                return GlobalResponse.objectIsNull("BIZ07FV012", request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if (!opMenu.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ07FV013", request);
            }
            Menu menuDB = opMenu.get();
            menuDB.setName(menu.getName());
            menuDB.setDescription(menu.getDescription());
            menuDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ07FE011", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ07FV021", request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if (!opMenu.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ07FV022", request);
            }
            menuRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ07FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 -040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Menu> page;
        List<ResMenuDTO> listDTO;
        Map<String, Object> data;
        try {
            page = menuRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ07FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ07FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 041 - 050
    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Menu> page;
        List<ResMenuDTO> listDTO;
        Map<String, Object> data;
        try {
            if (!isValidSort(pageable.getSort().stream().toList().getFirst().getProperty())){
                return GlobalResponse.dataNotFound("BIZ07FV041", request);
            }
            page = switch (columnName) {
                case "name" -> menuRepo.findByNameContainsIgnoreCase(value, pageable);
                case "description" -> menuRepo.findByDescriptionContainsIgnoreCase(value, pageable);
                default -> menuRepo.findAll(pageable);
            };
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ07FV042", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, columnName, value);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ07FE041", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 051 - 060
    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResMenuDTO response;
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ07FV051", request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if (!opMenu.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ07FV052", request);
            }
            Menu menuDB = opMenu.get();
            response = mapToDTO(menuDB);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ07FE051", request);
        }
        return GlobalResponse.foundData(response, request);
    }

    public Boolean isValidSort(String column){
        return switch (column) {
            case "id", "name", "description" -> true;
            default -> false;
        };
    }

    public Menu mapToMenu(ValMenuDTO valMenuDTO) {
        return modelMapper.map(valMenuDTO, Menu.class);
    }

    public List<ResMenuDTO> mapToDTO(List<Menu> listMenu) {
        return modelMapper.map(listMenu, new TypeToken<List<ResMenuDTO>>() {
        }.getType());
    }

    public ResMenuDTO mapToDTO(Menu menu) {
        return modelMapper.map(menu, ResMenuDTO.class);
    }
}
