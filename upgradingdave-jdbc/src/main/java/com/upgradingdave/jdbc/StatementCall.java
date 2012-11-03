package mil.dfas.persistence.jdbc.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public interface StatementCall<T> {

    public T withConnection(Connection connection, PreparedStatement statement);

}
