package org.harrison.devops;

import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public final class RandomUtil {
	private RandomUtil() {
		// Empty OK
	}

	public static <T> List<T> randomData(final Random random, final int min, final int max,
			final Function<Random, T> factory) {
		return Stream.generate(() -> factory.apply(random)).limit(randomRange(random, min, max)).collect(toList());
	}

	public static int randomRange(final Random random, final int min, final int max) {
		return min + random.nextInt(max - min);
	}

	public static String randomString(final Random random) {
		return new BigInteger(30, random).toString(Character.MAX_RADIX);
	}

	public static Random random(final String text) {
		return new Random(new BigInteger(text.getBytes()).longValue());
	}

}
