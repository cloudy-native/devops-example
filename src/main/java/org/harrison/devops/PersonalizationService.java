package org.harrison.devops;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalizationService {
	/**
	 * Handy trivial test endpoint
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "there") String name) {
		return "hello " + name;
	}

	/**
	 * Fake a list of similar brand ids
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/brand/{brandId}/similar")
	public List<String> brandSimilar(@PathVariable String brandId) {
		final Random random = new Random(asSeed("brand" + brandId));

		return Stream.generate(() -> randomString(random)).limit(random.nextInt(5)).collect(toList());
	}

	/**
	 * Fake a list of recommended brand ids for a member
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/member/{memberId}/brands")
	public List<String> memberRecommendedBrands(@PathVariable String memberId) {
		final Random random = new Random(asSeed("member" + memberId));

		return Stream.generate(() -> randomString(random)).limit(random.nextInt(10)).collect(toList());
	}

	private String randomString(final Random random) {
		return Stream.generate(() -> randomFragment(random, 12)).limit(3).collect(joining("-"));
	}

	private String randomFragment(final Random random, int numBits) {
		return new BigInteger(numBits, random).toString(Character.MAX_RADIX);
	}

	private long asSeed(final String text) {
		return new BigInteger(text.getBytes()).longValue();
	}

}
