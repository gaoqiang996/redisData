package com.redisdata.gq.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public interface MysqlJdbcService {

    JdbcTemplate getConnect(HikariConfig config);
}
