package camp.nextstep.jdbc.transaction;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class DataSourceTransactionManagerTest {

    private final DataSource dataSource = mock(DataSource.class);
    private final Connection connection = mock(Connection.class);
    private final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

    @Test
    void autoCommit이_false인_connection을_생성한다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        doNothing().when(connection).setAutoCommit(false);

        transactionManager.getTransaction();
        verify(connection).setAutoCommit(false);
    }

    @Test
    void commit후_Connection을_close처리한다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        doNothing().when(connection).commit();
        doNothing().when(connection).close();

        transactionManager.commit();
        verify(connection).commit();
        verify(connection).close();
    }

    @Test
    void rollback후_Connection을_close처리한다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        doNothing().when(connection).rollback();
        doNothing().when(connection).close();

        transactionManager.rollback();
        verify(connection).rollback();
        verify(connection).close();
    }
}
