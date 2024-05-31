package com.tareaFinal.APIclubNautico.repository;

import com.tareaFinal.APIclubNautico.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository <Patron, Integer>{
    //La interfaz manejará automáticamente las operaciones CRUD (JPA) sobre la entidad Patron

    //CONSULTAS ESPECÍFICAS
    
    Optional<Patron> findPatronById(int id);
    Optional<Patron> findPatronByDniIgnoreCase(String dni);

}
