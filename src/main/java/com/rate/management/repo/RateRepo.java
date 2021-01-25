package com.rate.management.repo;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rate.management.entity.Rate;

@Repository
public interface RateRepo extends JpaRepository<Rate, Long>{

	@Query("select k from Rate k where id=:id and :currentDate between k.rateEffectiveDate and k.rateExpirationDate")
	Optional<Rate> findByDateFilterandId(Date currentDate,Long id);
}
