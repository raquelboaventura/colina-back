package com.example.colina.repository;

import com.example.colina.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.cliente.id = :idCliente AND e.status != 'CONCLUIDO' AND e.status != 'CANCELADO'")
            long contarEmprestimosPorCliente(@Param("idCliente") Long idCliente);
}
