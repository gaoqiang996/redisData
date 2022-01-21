package com.redisdata.gq.service.impl;

import com.redisdata.gq.service.MysqlJdbcService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class MysqlJdbcServiceImpl implements MysqlJdbcService {
    @Override
    public JdbcTemplate getConnect(HikariConfig hikariConfig) {
        hikariConfig.setUsername("");
        hikariConfig.setPassword("");
        hikariConfig.setJdbcUrl("");
        hikariConfig.setDriverClassName("");
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
        return null;
    }
}
