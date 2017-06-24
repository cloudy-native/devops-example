package org.harrison.devops.spring;

import java.util.List;
import java.util.Random;

import org.harrison.devops.RandomUtil;
import org.harrison.devops.domain.ScoredBrandRecommendation;
import org.harrison.devops.domain.ScoredProductRecommendation;
import org.springframework.stereotype.Service;

@Service
public class PersonalizationService {
	/**
	 * Fake a list of recommended brands for a member
	 *
	 * @param id
	 * @return
	 */
	public List<ScoredBrandRecommendation> memberBrands(final String memberId) {
		return RandomUtil.randomData(RandomUtil.random("brands" + memberId), 1, 5, this::randomBrand);
	}

	private ScoredBrandRecommendation randomBrand(final Random random) {
		return new ScoredBrandRecommendation("brand-" + RandomUtil.randomString(random), random.nextFloat());
	}

	/**
	 * Fake a list of recommended products for a member
	 *
	 * @param id
	 * @return
	 */
	public List<ScoredProductRecommendation> memberProducts(final String memberId) {
		return RandomUtil.randomData(RandomUtil.random("products" + memberId), 2, 10, this::randomProduct);
	}

	private ScoredProductRecommendation randomProduct(final Random random) {
		return new ScoredProductRecommendation("product-" + RandomUtil.randomString(random), random.nextFloat());
	}

}
