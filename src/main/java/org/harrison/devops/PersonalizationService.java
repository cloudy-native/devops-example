package org.harrison.devops;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class PersonalizationService {
	/**
	 * Fake a list of similar brand ids
	 * 
	 * @param id
	 * @return
	 */
	public List<String> brandSimilar(final String brandId) {
		final Random random = new Random(asSeed("brand" + brandId));

		return Stream.generate(() -> randomString(random)).limit(random.nextInt(5)).collect(toList());
	}

	/**
	 * Fake a list of recommended brand ids for a member
	 * 
	 * @param id
	 * @return
	 */
	public List<String> memberRecommendedBrands(final String memberId) {
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
