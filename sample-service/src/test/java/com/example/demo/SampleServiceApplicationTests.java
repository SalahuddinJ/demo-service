package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.SampleEntity;
import com.example.demo.entity.Translation;
import com.example.demo.repository.SampleRepository;

@SpringBootTest
class SampleServiceApplicationTests {

	@Autowired
	private SampleRepository repository;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	public void before() {
		SampleEntity entity = repository.save(new SampleEntity());
		assertThat(entity.getId()).isNotNull();

		Translation translationAr = new Translation();
		translationAr.setLocaleISOCode("ar");
		translationAr.setTranslation("مرحبًا");

		Translation translationFr = new Translation();
		translationFr.setLocaleISOCode("fr");
		translationFr.setTranslation("Bonjour");

		Translation translationEn = new Translation();
		translationEn.setLocaleISOCode("en");
		translationEn.setTranslation("Hello");

		entity.getField1TranslationList().add(translationAr);
		entity.getField1TranslationList().add(translationFr);
		entity.getField1TranslationList().add(translationEn);

		translationAr = new Translation();
		translationAr.setLocaleISOCode("ar");
		translationAr.setTranslation("ما أخبارك");

		translationFr = new Translation();
		translationFr.setLocaleISOCode("fr");
		translationFr.setTranslation("quoi de neuf");

		translationEn = new Translation();
		translationEn.setLocaleISOCode("en");
		translationEn.setTranslation("whatsup");

		entity.getField2TranslationList().add(translationAr);
		entity.getField2TranslationList().add(translationFr);
		entity.getField2TranslationList().add(translationEn);
		
		entity.setName("Sample");
		repository.saveAndFlush(entity);
	}
	
	@Transactional
	@Test
	void contextLoads() {
		runTest1("en", "Hello");
		runTest1("ar", "مرحبًا");
		runTest1("fr", "Bonjour");
		//
		runTest2("en", "whatsup");
		runTest2("ar", "ما أخبارك");
		runTest2("fr", "quoi de neuf");
	}

	@SuppressWarnings("deprecation")
	public void runTest1(String lang, String translation) {
		entityManager.clear();
		LocaleContextHolder.setLocale(new Locale(lang));
		SampleEntity entity = repository.getOne(1L);
		assertThat(entity.getField1TranslationList()).hasSize(1);
		assertThat(entity.getField1TranslationList().get(0).getTranslation()).isEqualTo(translation);
	}
	
	@SuppressWarnings("deprecation")
	public void runTest2(String lang, String translation) {
		entityManager.clear();
		LocaleContextHolder.setLocale(new Locale(lang));
		SampleEntity entity = repository.getOne(1L);
		assertThat(entity.getField2TranslationList()).hasSize(1);
		assertThat(entity.getField2TranslationList().get(0).getTranslation()).isEqualTo(translation);
	}

}
