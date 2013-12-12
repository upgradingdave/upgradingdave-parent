package com.upgradingdave.jdbc.dao;

import com.upgradingdave.models.Model;
import com.upgradingdave.models.ModelDao;
import com.upgradingdave.models.PageContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class SpringJdbcModelDaoImpl<T extends Model, ID> extends JdbcDaoSupport implements ModelDao<T, ID> {

  protected static final Logger log = LoggerFactory.getLogger(SpringJdbcModelDaoImpl.class);

  public SpringJdbcModelDaoImpl(DataSource dataSource) {
    super();
    setDataSource(dataSource);
  }

  abstract public Class getClazz();

  abstract public String getTableName();

  public T create(T model) {

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

  @Override
  public T findById(ID id) {
    log.debug("Attempting to find by id {}", id);

    List<T> results = getJdbcTemplate().query(String.format("SELECT * from %s WHERE ID=%d", getTableName(), id),
      BeanPropertyRowMapper.newInstance(getClazz()));

    return (results == null || results.size() <= 0) ? null : results.get(0);

  }

  public T update(T model) {

    log.debug("Attempting to update {}", model);

    String sql = String.format(getUpdateSql(getClazz(), Arrays.asList("ID")), getTableName());

    SqlParameterSource parameters = new BeanPropertySqlParameterSource(model);

    int result = getJdbcTemplate().update(sql, parameters);

    if(result == 1) {
      return model;
    } else {
      log.error("Update was not successful: {}", sql);
      return null;
    }
  }

  private String whereClauseFromFilters(List<Map<String, String>> filters) {
    StringBuilder sb = new StringBuilder();
    if(filters != null && filters.size() > 0) {
      sb.append("WHERE ");
      for(Map<String, String> filter : filters) {
        sb.append("(");

        for(String key : filter.keySet()) {
          sb.append(key).append("=").append(filter.get(key));
        }

        sb.append(") ");
      }
    }
    return sb.toString();
  }

  public List<T> findAll(PageContext<List<Map<String, String>>, List<String>> page) {
    log.debug("Attempting to find all with limit {}, offset {}", page.getSize(), page.getPage()*page.getSize());

    List<Map<String, String>> filters = page.getFilters();
    String where = whereClauseFromFilters(filters);

    String orderDirection = page.getSortDirection() ? "ASC" : "DESC";

    StringBuilder orderedBy = new StringBuilder("");
    if(page.getSortOrder() != null) {
      orderedBy.append("ORDER BY ").append(StringUtils.join(page.getSortOrder(), " ")).append(" ").append(orderDirection);
    } else {
      orderedBy.append("ORDER BY ID ").append(orderDirection);
    }

    return getJdbcTemplate().query(String.format("SELECT * FROM %s %s %s LIMIT %d OFFSET %d", getTableName(), where, orderedBy, page.getSize(), page.getPage()*page.getSize()),
            BeanPropertyRowMapper.newInstance(getClazz()));
  }

  @Override
  public long getTotal() {
    return getJdbcTemplate().queryForLong(String.format("SELECT count(*) FROM %s", getTableName()));
  }

  public void delete(T model) {
    log.debug("Attempting to delete {}", model);
    getJdbcTemplate().execute(String.format("DELETE FROM %s WHERE ID = %d", getTableName(), model.getId()));
  }

  private String getUpdateSql(Class clazz, List<String> whereClauseKeys){

    StringBuilder sqlBuilder = new StringBuilder("UPDATE %s SET ");

    boolean first = true;
    for (Field field : clazz.getDeclaredFields()) {
      if (!first) {
        sqlBuilder.append(", ");
      }
      first = false;

      sqlBuilder.append(field.getName().toUpperCase());
      sqlBuilder.append(" = ?");
    }

    first = true;
    for (String key : whereClauseKeys) {
      if (first) {
        sqlBuilder.append(" WHERE ");
      } else {
        sqlBuilder.append(" AND ");
      }
      first = false;

      sqlBuilder.append(key.toUpperCase());
      sqlBuilder.append(" = ?");
    }

    return sqlBuilder.toString();
  }
}