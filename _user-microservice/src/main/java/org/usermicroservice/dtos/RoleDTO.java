package org.usermicroservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.usermicroservice.enums.ERole;

@Data
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private ERole role;

    public RoleDTO(ERole role) {
        this.role = role;
    }
}
