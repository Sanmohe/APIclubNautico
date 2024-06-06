package com.tareaFinal.APIclubNautico.repository;

import com.tareaFinal.APIclubNautico.entity.Barco;
import com.tareaFinal.APIclubNautico.entity.Patron;
import com.tareaFinal.APIclubNautico.entity.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalidaRepository extends JpaRepository<Salida, Integer> {

    //Consulta con Inversión de Control (Spring)
    Optional<Salida> findSalidaById(int id);
    List<Salida> findSalidasByFecha(String fecha);
    List<Salida> findSalidasByBarcoMatricula(String matricula);
    List<Salida> findSalidasByPatronId(int id);

}