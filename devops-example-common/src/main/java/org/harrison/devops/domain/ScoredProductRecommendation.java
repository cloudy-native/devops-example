package org.harrison.devops.domain;

public class ScoredProductRecommendation {
	private String productId;
	private float score;

	public ScoredProductRecommendation(String productId, float score) {
		this.productId = productId;
		this.score = score;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(final float score) {
		this.score = score;
	}
}
