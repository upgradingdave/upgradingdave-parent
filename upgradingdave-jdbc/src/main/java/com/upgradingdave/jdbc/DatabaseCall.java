package mil.dfas.persistence.jdbc.provider;

import java.sql.Connection;

public interface DatabaseCall<T> {

    public T withConnection(Connection connection);

}
