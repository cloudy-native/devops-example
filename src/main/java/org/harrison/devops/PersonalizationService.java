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
		return randomString(asSeed("brand" + brandId), 1, 5);
	}

	/**
	 * Fake a list of recommended brand ids for a member
	 * 
	 * @param id
	 * @return
	 */
	public List<String> memberRecommendedBrands(final String memberId) {
		return randomString(asSeed("member" + memberId), 2, 10);
	}

	private static List<String> randomString(long seed, int min, int max) {
		final Random random = new Random(seed);
		final int limit = min + random.nextInt(max - min);
		final Supplier<String> fragment = () -> randomString(random);

		return Stream.generate(fragment).limit(limit).collect(toList());
	}

	private static String randomString(final Random random) {
		final Supplier<String> fragment = () -> new BigInteger(30, random).toString(Character.MAX_RADIX);

		return Stream.generate(fragment).limit(4).collect(joining("-"));
	}

	private static long asSeed(final String text) {
		return new BigInteger(text.getBytes()).longValue();
	}
}
