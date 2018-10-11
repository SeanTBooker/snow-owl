/*
 * Copyright 2011-2018 B2i Healthcare Pte Ltd, http://b2i.sg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.b2international.snowowl.core.internal;

import org.osgi.service.prefs.PreferencesService;

import com.b2international.commons.extension.Component;
import com.b2international.commons.platform.PlatformUtil;
import com.b2international.snowowl.core.CoreActivator;
import com.b2international.snowowl.core.config.SnowOwlConfiguration;
import com.b2international.snowowl.core.ft.FeatureToggles;
import com.b2international.snowowl.core.login.LoginConfiguration;
import com.b2international.snowowl.core.monitoring.MonitoringConfiguration;
import com.b2international.snowowl.core.setup.ConfigurationRegistry;
import com.b2international.snowowl.core.setup.Environment;
import com.b2international.snowowl.core.setup.Plugin;
import com.b2international.snowowl.core.terminology.TerminologyRegistry;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

/**
 * @since 3.3
 */
@Component
public final class SnowOwlPlugin extends Plugin {

	@Override
	public void init(SnowOwlConfiguration configuration, Environment env) {
		final PreferencesService preferences = env.preferences(); 
		
		final LoginConfiguration loginConfiguration = new LoginConfiguration(preferences);
		env.services().registerService(LoginConfiguration.class, loginConfiguration);
		
		env.services().registerService(TerminologyRegistry.class, TerminologyRegistry.INSTANCE);
		
		if (configuration.getModuleConfig(MonitoringConfiguration.class).isEnabled()) {
			final PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
			registry.config().commonTags("application", "Snow Owl");
			// configure default metrics
			new ClassLoaderMetrics().bindTo(registry);
			new JvmGcMetrics().bindTo(registry);
			new JvmMemoryMetrics().bindTo(registry);
			new JvmThreadMetrics().bindTo(registry);
			new UptimeMetrics().bindTo(registry);
			new ProcessorMetrics().bindTo(registry);
			env.services().registerService(MeterRegistry.class, registry);
		} else {
			// XXX this works like a NOOP registry if you do NOT register any additional registries to it
			env.services().registerService(MeterRegistry.class, new CompositeMeterRegistry());
		}
		
		// TODO support initial values for feature toggles
		env.services().registerService(FeatureToggles.class, new FeatureToggles());
	}

	@Override
	public void run(SnowOwlConfiguration configuration, Environment environment) {
		if (!environment.isEmbedded() && environment.isClient()) {
			PlatformUtil.enableSystemProxies(CoreActivator.getContext());
		}
	}
	
	@Override
	public void addConfigurations(ConfigurationRegistry registry) {
		registry.add("monitoring", MonitoringConfiguration.class);
	}

}