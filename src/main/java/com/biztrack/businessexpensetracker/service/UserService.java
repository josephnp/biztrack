package com.biztrack.businessexpensetracker.service;

import com.biztrack.businessexpensetracker.core.IService;
import com.biztrack.businessexpensetracker.dto.response.ResUserDTO;
import com.biztrack.businessexpensetracker.dto.validation.ValUserDTO;
import com.biztrack.businessexpensetracker.model.User;
import com.biztrack.businessexpensetracker.repo.UserRepo;
import com.biztrack.businessexpensetracker.security.BcryptCustom;
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
 * Kode Modul : 03
 * Kode Validation / Error  : FV - FE
 */

@Service
@Transactional
public class UserService implements IService<User> {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BcryptCustom bcryptCustom;

    @Autowired
    private TransformPagination tp;

    // 001 - 010
    @Override
    public ResponseEntity<Object> save(User user, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (user == null) {
                return GlobalResponse.objectIsNull("BIZ03FV001", request);
            }

            user.setPassword(bcryptCustom.hash("Password@1234" + user.getEmail()));
            user.setCreatedBy(Long.parseLong(tokenData.get("id").toString()));
            userRepo.save(user);

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE001", request);
        }
        return GlobalResponse.savingSuccess(request);
    }

    // 011 - 020
    @Override
    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
        try {
            Map<String,Object> tokenData = GlobalFunction.extractToken(request);
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ03FV011", request);
            }
            if (user == null) {
                return GlobalResponse.objectIsNull("BIZ03FV012", request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if (!opUser.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ03FV013", request);
            }

            User userDB = opUser.get();
            userDB.setRole(user.getRole());
            userDB.setDepartment(user.getDepartment());
            userDB.setEmployeeNumber(user.getEmployeeNumber());
            userDB.setFullName(user.getFullName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(bcryptCustom.hash("Password@1234" + user.getEmail()));
            userDB.setModifiedBy(Long.parseLong(tokenData.get("id").toString()));

        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE011", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }

    // 021 - 030
    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ03FV021", request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if (!opUser.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ03FV022", request);
            }
            userRepo.deleteById(id);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE021", request);
        }
        return GlobalResponse.deletedSuccess(request);
    }

    // 031 -040
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<User> page;
        List<ResUserDTO> listDTO;
        Map<String, Object> data;
        try {
            page = userRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ03FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE031", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 041 - 050
    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<User> page;
        List<ResUserDTO> listDTO;
        Map<String, Object> data;
        try {
            if (!isValidSort(pageable.getSort().stream().toList().getFirst().getProperty())){
                return GlobalResponse.dataNotFound("BIZ03FV041", request);
            }
            page = switch (columnName) {
                case "name" -> userRepo.findByFullNameContainsIgnoreCase(value, pageable);
                case "email" -> userRepo.findByEmailContainsIgnoreCase(value, pageable);
                case "employeeNumber" -> userRepo.findByEmployeeNumberContainsIgnoreCase(value, pageable);
                case "role" -> userRepo.findByRole_NameContainsIgnoreCase(value, pageable);
                default -> userRepo.findAll(pageable);
            };
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("BIZ03FV042", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, columnName, value);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE041", request);
        }
        return GlobalResponse.foundData(data, request);
    }

    // 051 - 060
    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResUserDTO response;
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ03FV051", request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if (!opUser.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ03FV052", request);
            }
            User userDB = opUser.get();
            response = mapToDTO(userDB);
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE051", request);
        }
        return GlobalResponse.foundData(response, request);
    }

    public Boolean isValidSort(String column){
        return switch (column) {
            case "id", "fullName", "employeeNumber", "email",  "role.name"-> true;
            default -> false;
        };
    }

    public User mapToUser(ValUserDTO valUserDTO) {
        return modelMapper.map(valUserDTO, User.class);
    }

    public List<ResUserDTO> mapToDTO(List<User> listUser) {
        return modelMapper.map(listUser, new TypeToken<List<ResUserDTO>>() {
        }.getType());
    }

    public ResUserDTO mapToDTO(User user) {
        return modelMapper.map(user, ResUserDTO.class);
    }

    // 041 - 050
    public ResponseEntity<Object> changePassword(Long id, String password, HttpServletRequest request) {
        try {
            if (id == null) {
                return GlobalResponse.objectIsNull("BIZ03FV041", request);
            }
            if (password == null) {
                return GlobalResponse.objectIsNull("BIZ03FV042", request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if (!opUser.isPresent()) {
                return GlobalResponse.dataNotFound("BIZ03FV043", request);
            }

            User userDB = opUser.get();
            userDB.setPassword(bcryptCustom.hash(password + userDB.getEmail()));
        } catch (Exception e) {
            return GlobalResponse.somethingWrong("BIZ03FE041", request);
        }
        return GlobalResponse.updatedSuccess(request);
    }
}
