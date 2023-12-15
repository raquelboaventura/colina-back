package com.example.colina.repository;

import com.example.colina.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
