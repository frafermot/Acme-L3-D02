/*
 * AdvertisementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("SELECT COUNT(b) FROM Banner b WHERE b.periodStart < :date AND b.periodEnd > :date")
	int countBanners(Date date);

	@Query("SELECT b FROM Banner b WHERE b.periodStart < :date AND b.periodEnd > :date")
	List<Banner> findManyBanners(@Param("pageable") Pageable pageable, @Param("date") Date date);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		ThreadLocalRandom random;
		PageRequest page;
		List<Banner> list;
		Date date;
		date = MomentHelper.getCurrentMoment();
		count = this.countBanners(date);
		if (count == 0)
			result = null;
		else {
			random = ThreadLocalRandom.current();
			index = random.nextInt(0, count);

			page = PageRequest.of(index, 1);
			list = this.findManyBanners(page, date);

			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}
}
