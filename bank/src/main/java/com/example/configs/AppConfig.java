package com.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.repositories.AccountRepository;
import com.example.repositories.TransactionRepository;
import com.example.services.ConsumerService;
import com.example.services.ProducerService;

@Configuration
@ComponentScan(basePackages = "com.example")
@EnableScheduling
public class AppConfig {
  @Bean
  public AccountRepository accountRepository(JdbcTemplate jdbcTemplate) {
    return new AccountRepository(jdbcTemplate);
  }

  @Bean
  public TransactionRepository transactionRepository(JdbcTemplate jdbcTemplate) {
    return new TransactionRepository(jdbcTemplate);
  }

  @Bean
  @Profile("producer")
  public ProducerService producerService(
          AccountRepository accountRepository,
          KafkaTemplate<String, String> kafkaTemplate) {
    return new ProducerService(accountRepository, kafkaTemplate);
  }

  @Bean
  @Profile("consumer")
  public ConsumerService consumerService(
          AccountRepository accountRepository,
          TransactionRepository transactionRepository,
          PlatformTransactionManager transactionManager) {
    return new ConsumerService(accountRepository, transactionRepository, transactionManager);
  }
}
