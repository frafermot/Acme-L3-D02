
package acme.features.banner;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.helpers.MessageHelper;

@Service
public class BannerService {

	@Autowired
	private BannerRepository repository;


	public Banner findRandomBanner() {
		int bannerCount;
		int bannerIndex;
		Banner defaultBanner;
		PageRequest pageRequest;
		String defaultSlogan;
		final Banner result;
		Optional<Banner> firstBanner;

		defaultBanner = new Banner();
		defaultSlogan = MessageHelper.getMessage("master.banner.alt");
		defaultBanner.setSlogan(defaultSlogan);
		defaultBanner.setLinkPicture("images/banner.png");
		defaultBanner.setLinkTarget("https://www.us.es/");

		bannerCount = this.repository.countBanners();
		if (bannerCount == 0)
			return defaultBanner;

		bannerIndex = ThreadLocalRandom.current().nextInt(bannerCount);
		pageRequest = PageRequest.of(bannerIndex, 1);
		firstBanner = this.repository.findDisplayBanners(pageRequest).stream().findFirst();
		if (!firstBanner.isPresent())
			result = defaultBanner;
		else
			result = firstBanner.get();

		return result;
	}
}
