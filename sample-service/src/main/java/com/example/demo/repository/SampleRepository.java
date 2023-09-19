package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.SampleEntity;

@Transactional
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

}
