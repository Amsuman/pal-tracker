package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class PalTrackerApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(PalTrackerApplication.class, args);

    }

/*   @Bean
    DataSource dataSource(@Value("spring.datasource.url") String url) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(url);
        return mysqlDataSource;
    }*/

   @Bean
   TimeEntryRepository timeEntryRepository(DataSource datasource) {
       return new JdbcTimeEntryRepository(datasource);
   }


}