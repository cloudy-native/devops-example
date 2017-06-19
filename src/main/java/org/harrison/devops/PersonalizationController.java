package org.harrison.devops;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class PersonalizationController {
	private final PersonalizationService service;

	@Autowired
	public PersonalizationController(final PersonalizationService service) {
		this.service = service;
	}

	public String hello(@RequestParam(value = "name") Optional<String> name) {
		return "hello " + name.orElse("there");
	}

	/**
	 * Return list of similar brand ids
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/brand/{brandId}/similar")
	public List<String> brandSimilar(@PathVariable String brandId) {
		return service.brandSimilar(brandId);
	}

	/**
	 * Return list of recommended brand ids for a member
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/member/{memberId}/brands")
	public List<String> memberRecommendedBrands(@PathVariable String memberId) {
		return service.memberRecommendedBrands(memberId);
	}
}
