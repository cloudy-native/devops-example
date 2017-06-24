package org.harrison.devops.domain;

public class ScoredBrandRecommendation {
	private String brandId;
	private float score;

	public ScoredBrandRecommendation(String brandId, float score) {
		this.brandId = brandId;
		this.score = score;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(final String brandId) {
		this.brandId = brandId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(final float score) {
		this.score = score;
	}
}
