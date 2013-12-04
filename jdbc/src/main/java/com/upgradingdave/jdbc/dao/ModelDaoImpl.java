package com.upgradingdave.jdbc.dao;

import com.upgradingdave.models.Model;
import com.upgradingdave.models.PageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

public abstract class ModelDaoImpl<T extends Model, ID> extends JdbcDaoSupport {

  protected static final Logger log = LoggerFactory.getLogger(ModelDaoImpl.class);

  public ModelDaoImpl(DataSource dataSource) {
    super();
    setDataSource(dataSource);
  }

  abstract public Class getClazz();

  abstract public String getTableName();

  public T create(T model) throws Exception {

    log.debug("Attempting to create {}", model);

    SqlParameterSource parameters = new BeanPropertySqlParameterSource(model);

    if(model.getId() == null) {

      SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(getDataSource()).withTableName(getTableName())
        .usingGeneratedKeyColumns("ID");

      Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
      model.setId(id.intValue());

    } else {
      SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(getDataSource()).withTableName(getTableName());
      simpleJdbcInsert.execute(parameters);
    }

    return model;
  }

  public T findById(ID id) {
    log.debug("Attempting to find by id {}", id);

    List<T> results = getJdbcTemplate().query(String.format("SELECT * from %s WHERE ID=%d", getTableName(), id),
      BeanPropertyRowMapper.newInstance(getClazz()));

    return (results == null || results.size() <= 0) ? null : results.get(0);

  }

  public T update(T model) {

    log.debug("Attempting to update {}", model);

    String sql = String.format(UpdateUtil.getUpdateSql(getClazz(), Arrays.asList("ID")), getTableName());

    SqlParameterSource parameters = new BeanPropertySqlParameterSource(model);

    int result = getJdbcTemplate().update(sql, parameters);

    if(result == 1) {
      return model;
    } else {
      log.error("Update was not successful: {}", sql);
      return null;
    }
  }

  public List<T> findAll(PageContext page) {
    log.debug("Attempting to find all with limit {}, offset {}", page.getSize(), page.getPage()*page.getSize());

    return getJdbcTemplate().query(String.format("SELECT * FROM %s LIMIT %d OFFSET %d", getTableName(), page.getSize(), page.getPage()*page.getSize()),
      BeanPropertyRowMapper.newInstance(getClazz()));
  }

  public void delete(T model) {
    log.debug("Attempting to delete {}", model);
    getJdbcTemplate().execute(String.format("DELETE FROM %s WHERE ID = %d", getTableName(), model.getId()));
  }
}