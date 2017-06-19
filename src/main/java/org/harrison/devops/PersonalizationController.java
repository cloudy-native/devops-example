package org.harrison.devops;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class PersonalizationController {
	/**
	 * Handy trivial test endpoint
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "name") Optional<String> name) {
		return "hello " + name.orElse("there");
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
		final Supplier<String> randomString = () -> randomString(random);

		return Stream.generate(randomString).limit(random.nextInt(10)).collect(toList());
	}

	private String randomString(final Random random) {
		final Supplier<String> fragment = () -> new BigInteger(30, random).toString(Character.MAX_RADIX);

		return Stream.generate(fragment).limit(4).collect(joining("-"));
	}

	private static long asSeed(final String text) {
		return new BigInteger(text.getBytes()).longValue();
	}
}
