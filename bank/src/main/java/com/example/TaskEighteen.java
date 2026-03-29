package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TaskEighteen {
  public static void main(String[] args) {
    String role = System.getenv("APP_ROLE");

    if (role == null || role.isBlank()) {
      role = "default";
    }

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    try {
      ctx.getEnvironment().setActiveProfiles(role);

      ctx.scan("com.example");

      ctx.refresh();

      System.out.println("Profile \"" + role + "\" started");

      Object lock = new Object();
      synchronized (lock) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    } finally {
      if (ctx.isActive()) {
        ctx.close();
      }
    }
  }
}
