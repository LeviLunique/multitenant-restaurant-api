package com.restauranthub.multitenant_restaurant_api.infra.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class InjecaoDependenciaConfigurationTest {

	@Test
	void shouldInstantiateDependencyInjectionConfiguration() {
		assertNotNull(new InjecaoDependenciaConfiguration());
	}
}
