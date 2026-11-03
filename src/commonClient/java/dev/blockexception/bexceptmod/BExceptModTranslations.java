package dev.blockexception.bexceptmod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.network.chat.Component;

final class BExceptModTranslations {
	private static final List<String> LANGUAGES = List.of("en_us", "de_de", "es_es", "fr_fr", "pl_pl");
	private static final Map<String, String> LANGUAGE_NAMES = Map.of(
			"en_us", "English",
			"de_de", "Deutsch",
			"es_es", "Español",
			"fr_fr", "Français",
			"pl_pl", "Polski"
	);
	private static final Map<String, Map<String, String>> TRANSLATIONS = loadTranslations();
	private static volatile String currentLanguage = "en_us";

	private BExceptModTranslations() {
	}

	static Component text(String key) {
		Map<String, String> selected = TRANSLATIONS.get(currentLanguage);
		Map<String, String> english = TRANSLATIONS.get("en_us");
		return Component.literal(selected.getOrDefault(key, english.getOrDefault(key, key)));
	}

	static String translate(String key) {
		Map<String, String> selected = TRANSLATIONS.get(currentLanguage);
		Map<String, String> english = TRANSLATIONS.get("en_us");
		return selected.getOrDefault(key, english.getOrDefault(key, key));
	}

	static void setLanguage(String language) {
		if (!isSupportedLanguage(language)) {
			throw new IllegalArgumentException("Unsupported BExceptMod language: " + language);
		}
		currentLanguage = language;
	}

	static boolean isSupportedLanguage(String language) {
		return LANGUAGES.contains(language);
	}

	static List<String> supportedLanguages() {
		return LANGUAGES;
	}

	static String languageName(String language) {
		if (language == null) {
			return LANGUAGE_NAMES.get("en_us");
		}
		return LANGUAGE_NAMES.getOrDefault(language, language);
	}

	private static Map<String, Map<String, String>> loadTranslations() {
		Map<String, Map<String, String>> translations = new LinkedHashMap<>();
		for (String language : LANGUAGES) {
			String resource = "/assets/bexceptmod/lang/" + language + ".json";
			try (InputStream input = BExceptModTranslations.class.getResourceAsStream(resource)) {
				if (input == null) {
					throw new IllegalStateException("Missing BExceptMod translation resource: " + resource);
				}
				JsonObject json = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
				Map<String, String> entries = new LinkedHashMap<>();
				json.entrySet().forEach(entry -> entries.put(entry.getKey(), entry.getValue().getAsString()));
				translations.put(language, Map.copyOf(entries));
			} catch (IOException exception) {
				throw new IllegalStateException("Could not load BExceptMod translations from " + resource, exception);
			}
		}
		return Map.copyOf(translations);
	}
}
