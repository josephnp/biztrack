package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResDepartmentDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValDepartmentDTO;
import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.repo.DepartmentRepo;
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
 * Kode Modul : 02
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class DepartmentService implements IService<Department> {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(Department department, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (department == null) {
                return GlobalResponse.objectIsNull("BIZ02FV001", request);
            }

            List<Department> departmentList = departmentRepo.findByNameContainsIgnoreCase(department.getName());
            if (!departmentList.isEmpty()) {
                return GlobalResponse.dataExists("BIZ02FV002", request);
            }
            department.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            departmentRepo.save(department);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ02FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(Long id, Department department, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ02FV011", request);
            }
            if (department == null) {
                return GlobalResponse.objectIsNull("BIZ02FV012", request);
            }
            Optional<Department> opDepartment = departmentRepo.findById(id);
            if (!opDepartment.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ02FV013", request);
            }
            Department departmentDB = opDepartment.get();
            departmentDB.setName(department.getName());
            departmentDB.setDescription(department.getDescription());
            departmentDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ02FE011", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ02FV021", request);
            }
            Optional<Department> opDepartment = departmentRepo.findById(id);
            if (!opDepartment.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ02FV022", request);
            }
            departmentRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ02FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 -040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Department> page;
        List<ResDepartmentDTO> listDTO;
        Map<String, Object> data;
        try {
            page = departmentRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ02FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ02FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 041 - 050
    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Department> page;
        List<ResDepartmentDTO> listDTO;
        Map<String, Object> data;
        try {
            if (!isValidSort(pageable.getSort().stream().toList().getFirst().getProperty())){
                return GlobalResponse.dataNotFound("BIZ02FV041", request);
            }
            page = switch (columnName) {
                case "name" -> departmentRepo.findByNameContainsIgnoreCase(value, pageable);
                case "description" -> departmentRepo.findByDescriptionContainsIgnoreCase(value, pageable);
                default -> departmentRepo.findAll(pageable);
            };
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ02FV042", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, columnName, value);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ02FE041", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 051 - 060
    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResDepartmentDTO response;
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ02FV051", request);
            }
            Optional<Department> opDepartment = departmentRepo.findById(id);
            if (!opDepartment.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ02FV052", request);
            }
            Department departmentDB = opDepartment.get();
            response = mapToDTO(departmentDB);
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

    public Department mapToDepartment(ValDepartmentDTO valDepartmentDTO) {
        return modelMapper.map(valDepartmentDTO, Department.class);
    }

    public List<ResDepartmentDTO> mapToDTO(List<Department> listDepartment) {
        return modelMapper.map(listDepartment, new TypeToken<List<ResDepartmentDTO>>() {
        }.getType());
    }

    public ResDepartmentDTO mapToDTO(Department department) {
        return modelMapper.map(department, ResDepartmentDTO.class);
    }
}
