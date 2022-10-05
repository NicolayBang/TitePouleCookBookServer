//package com.example.RecipeBook.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//@Configuration
//public class DBConfig {
////    @Bean
////    public DataSource dataSource(){
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("org.mariadb.jdbc");
////        dataSource.setUrl("jdbc:mariadb://localhost:3306");
////        dataSource.setUsername( "root" );
////        dataSource.setPassword( "Roslyn06" );
////        return dataSource;
////
////    }
//
////    @Bean
////    public Connection connection() {
////        Connection connection = null;
////        try {
////            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/recipe_db_v2", "root", "Roslyn06");
////
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        return connection;
////    }
//
////        @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurer() {
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/**")
////
////                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
//////                        .allowedOrigins("*")
//////                        .allowedHeaders("*")
//////                        .allowCredentials(false);
////            }
////        };
////    }
//}
