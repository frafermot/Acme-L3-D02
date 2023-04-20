
package acme.features.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerController {

	@Autowired
	private BannerService service;


	@ModelAttribute("banner")
	public Banner getBanner() {
		Banner result;

		result = this.service.findRandomBanner();

		return result;
	}
}
