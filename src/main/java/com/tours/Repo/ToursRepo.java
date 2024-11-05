package com.tours.Repo;

import com.tours.Entities.Tours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToursRepo extends JpaRepository<Tours, Long> {

}