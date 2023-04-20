
package acme.features.banner;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("SELECT COUNT(b) FROM Banner b WHERE b.periodStart < CURRENT_TIMESTAMP AND b.periodEnd > CURRENT_TIMESTAMP")
	int countBanners();

	@Query("SELECT b FROM Banner b WHERE b.periodStart < CURRENT_TIMESTAMP AND b.periodEnd > CURRENT_TIMESTAMP")
	List<Banner> findDisplayBanners(PageRequest pageRequest);
}
