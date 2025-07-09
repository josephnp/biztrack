package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.config.OtherConfig;
import com.biztrack.businessexpensetracker.dto.validation.ValMenuDTO;
import com.biztrack.businessexpensetracker.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO valMenuDTO,
                                       HttpServletRequest request){
        return menuService.save(menuService.mapToMenu(valMenuDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValMenuDTO valMenuDTO,
                                         HttpServletRequest request){
        return menuService.update(id,menuService.mapToMenu(valMenuDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return menuService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return menuService.findAll(pageable,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable;
        if (sort.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy));
        }
        return menuService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return menuService.findById(id,request);
    }
}
