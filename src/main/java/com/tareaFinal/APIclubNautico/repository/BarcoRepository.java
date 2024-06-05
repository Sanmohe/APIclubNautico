package com.tareaFinal.APIclubNautico.repository;

import com.tareaFinal.APIclubNautico.entity.Barco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarcoRepository extends JpaRepository<Barco, String> {

    //Consulta con Inversión de Control (Spring)
    Optional<Barco> findBarcoByMatriculaIgnoreCase(String matricula);
    Optional<Barco> findSocioByNombreIgnoreCase(String nombre);

}
