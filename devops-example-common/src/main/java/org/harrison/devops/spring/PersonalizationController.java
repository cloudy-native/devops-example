package org.harrison.devops.spring;

import java.util.List;
import java.util.Optional;

import org.harrison.devops.domain.ScoredBrandRecommendation;
import org.harrison.devops.domain.ScoredProductRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class PersonalizationController {
	private final PersonalizationService service;

	@Autowired
	public PersonalizationController(final PersonalizationService service) {
		this.service = service;
	}

	public String hello(@RequestParam(value = "name") final Optional<String> name) {
		return "hello " + name.orElse("there");
	}

	/**
	 * Return list of similar brand ids
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/{memberId}/brands")
	public List<ScoredBrandRecommendation> memberBrands(@PathVariable final String memberId) {
		return service.memberBrands(memberId);
	}

	/**
	 * Return list of recommended brand ids for a member
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/{memberId}/products")
	public List<ScoredProductRecommendation> memberRecommendedBrands(@PathVariable final String memberId) {
		return service.memberProducts(memberId);
	}
}
