package com.plazoleta.plazoleta.infraestructure.out.jpa.repository;

import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.EnumSet;

public interface IPedidoRepository extends JpaRepository<PedidoEntity,Long> {
    //existePedidoActivoPorCliente
    @Query("""
    SELECT CASE WHEN COUNT(p)>0 THEN true ELSE false END
    FROM PedidoEntity p
    WHERE p.idCliente = :idCliente AND p.estado IN :estados
  """)
    Boolean existsPedidoActivoPorCliente(Long idCliente, EnumSet<Estado> estados);
}
