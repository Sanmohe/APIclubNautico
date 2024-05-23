package com.tareaFinal.APIclubNautico.repository;

import com.tareaFinal.APIclubNautico.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioRepository extends JpaRepository <Socio, Integer>{     //<nombreEntidad,tipoPK>
    //La interfaz manejará automáticamente las operaciones CRUD (JPA) sobre la entidad Socio
}
