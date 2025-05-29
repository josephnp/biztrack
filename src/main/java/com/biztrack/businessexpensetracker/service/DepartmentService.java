package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.report.RepDepartmentDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValDepartmentDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.repo.DepartmentRepo;
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
public class DepartmentService implements IService<Department> {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    @Override
    public ResponseEntity<Object> save(Department department, HttpServletRequest request) {
        try {
            Optional<Department> opDepartment = departmentRepo.findByName(department.getName());
            if (opDepartment.isPresent()) {
                return new ResponseHandler().handleResponse("Data Already Exist !!", HttpStatus.BAD_REQUEST, null, "DEP04FV001", request);
            }
            if (department == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "DEP04FV002", request);
            }
            departmentRepo.save(department);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDisimpan("DEP04FE001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Department department, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("DEP04FV011", request);
            }
            if (department == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "DEP04FV012", request);
            }
            Optional<Department> opDepartment = departmentRepo.findById(id);
            if (!opDepartment.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("DEP04FV013", request);
            }
            Department DepartmentDB = opDepartment.get();
            DepartmentDB.setName(department.getName());
            DepartmentDB.setDescription(department.getDescription());
        } catch (Exception e) {
            return GlobalResponse.dataGagalDiubah("DEP04FE002", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("DEP04FV021", request);
            }
            Optional<Department> opDepartment = departmentRepo.findById(id);
            if (!opDepartment.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("DEP04FV022", request);
            }
            departmentRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDihapus("DEP04FE021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Department> page = null;
        List<Department> list = null;
        List<RepDepartmentDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = departmentRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("DEP04FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.terjadiKesalahan("DEP04FE031", request);
        }
        return GlobalResponse.dataDitemukan(data, request);
    }


    public Department mapToDepartment(ValDepartmentDTO valDepartmentDTO) {
        return modelMapper.map(valDepartmentDTO, Department.class);
    }

    public List<RepDepartmentDTO> mapToDTO(List<Department> listDepartment) {
        return modelMapper.map(listDepartment, new TypeToken<List<RepDepartmentDTO>>() {
        }.getType());
    }


}
