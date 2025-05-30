package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.report.RepStatusDTO;
import com.biztrack.businessexpensetracker.dto.report.RepUserDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValStatusDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValUserDTO;
import com.biztrack.businessexpensetracker.handler.ResponseHandler;
import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.model.Status;
import com.biztrack.businessexpensetracker.model.User;
import com.biztrack.businessexpensetracker.repo.DepartmentRepo;
import com.biztrack.businessexpensetracker.repo.RoleRepo;
import com.biztrack.businessexpensetracker.repo.UserRepo;
import com.biztrack.businessexpensetracker.security.BcryptImpl;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserService implements IService<User> {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;
    
    @Override
    public ResponseEntity<Object> save(User user, HttpServletRequest request) {
        try{
            if(user == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT04FV001",request);
            }
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            userRepo.save(user);
        }catch (Exception e){
            return GlobalResponse.dataGagalDisimpan("AUT04FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }


    public ResponseEntity<Object> update(UUID id, User user, HttpServletRequest request) {
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT04FV011",request);
            }
            if(user == null){
                return GlobalResponse.objectIsNull("AUT04FV012",request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT04FV013",request);
            }

            Long roleId = user.getRole().getId();
            Optional<Role> opRole = roleRepo.findById(roleId);
            if (!opRole.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("AUT04FV014", request); // Role tidak ditemukan
            }
            Role role = opRole.get();

            Long departmentId = user.getDepartment().getId();
            Optional<Department> opDept = departmentRepo.findById(departmentId);
            if (!opDept.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("AUT04FV015", request); // Department tidak ditemukan
            }
            Department department = opDept.get();

            User userDB = opUser.get();
            userDB.setEmployeeNumber(user.getEmployeeNumber());
            userDB.setFullName(user.getFullName());
            userDB.setEmail(user.getEmail());
            userDB.setDepartment(department);
            userDB.setRole(role);
            userDB.setPassword(BcryptImpl.hash(user.getPassword()));

            userRepo.save(userDB);
        }catch (Exception e){
            return GlobalResponse.dataGagalDiubah("AUT04FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }


    public ResponseEntity<Object> delete(UUID id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("STA04FV021", request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if (!opUser.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("DEP04FV022", request);
            }
            userRepo.deleteById(id);

        } catch (Exception e) {
            return GlobalResponse.dataGagalDihapus("STA04FE021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        List<RepUserDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = userRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("STA04FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.terjadiKesalahan("STA04FE031", request);
        }
        return GlobalResponse.dataDitemukan(data, request);
    }

    public List<RepUserDTO> mapToDTO(List<User> listUser) {
        return modelMapper.map(listUser, new TypeToken<List<RepUserDTO>>() {
        }.getType());
    }

    public User mapToUser(ValUserDTO valUserDTO) {
        return modelMapper.map(valUserDTO, User.class);
    }
}
