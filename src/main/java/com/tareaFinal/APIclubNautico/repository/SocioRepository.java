package com.tareaFinal.APIclubNautico.repository;

import com.tareaFinal.APIclubNautico.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SocioRepository extends JpaRepository <Socio, Integer>{     //<nombreEntidad,tipoPK>
    //La interfaz manejará automáticamente las operaciones CRUD (JPA) sobre la entidad Socio

    //CONSULTAS ESPECÍFICAS
    
    //Consulta con JPQL
    @Query("SELECT s FROM Socio s WHERE s.nombre = :nombre")    //:nombre es el parámetro que se le pasa al método
    Optional<Socio> findSocioByNombreWithJPQL(String nombre);

    //Consulta con Inversión de Control (Spring)
    Optional<Socio> findSocioById(int id);
    Optional<Socio> findSocioByNombreIgnoreCase(String nombre);


}
