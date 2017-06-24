package org.harrison.devops;

import org.harrison.devops.spring.PersonalizationConfig;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
	/**
	 * Implements singleton pattern
	 */
	private static class Singleton {
		static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler = instance();

		static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> instance() {
			try {
				return SpringLambdaContainerHandler.getAwsProxyHandler(PersonalizationConfig.class);
			} catch (final ContainerInitializationException e) {
				throw new RuntimeException("Cannot get Spring Lambda Handler", e);
			}
		}
	}

	@Override
	public AwsProxyResponse handleRequest(final AwsProxyRequest awsProxyRequest, final Context context) {
		return Singleton.handler.proxy(awsProxyRequest, context);
	}
}