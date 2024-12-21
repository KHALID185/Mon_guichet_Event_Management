package org.usermicroservice.mappers;

import org.usermicroservice.dtos.RoleDTO;
import org.usermicroservice.entities.Role;
import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDTO toDTO(Role role){
        if(role == null) return null;
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRole(role.getRole());
        return roleDTO;
    }
    /*
    public static RoleDTO toDTO(Role role){
    if(role == null) return null;
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setRole("[RoleDTO(id=" + role.getId() + ", role=" + role.getRole() + ")]");
    return roleDTO;
}

     */
    public static Role toRole(RoleDTO roleDTO){
        if(roleDTO == null) return null;
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRole(roleDTO.getRole());
        return role;
    }

    public static List<Role> toList(List<RoleDTO> roleDTOList){
        if(roleDTOList == null || roleDTOList.isEmpty()) return null;
        return roleDTOList.stream()
                .map(RoleMapper::toRole)
                .collect(Collectors.toList());
    }

    public static List<RoleDTO> roleDTOList(List<Role> roleList){
        if(roleList == null || roleList.isEmpty()) return null;
        return roleList.stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

}
