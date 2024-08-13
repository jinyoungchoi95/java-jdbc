package camp.nextstep.transaction.support;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class TransactionSynchronizationManagerTest {

    @Test
    void 요청된_DataSource에_connection이_없는_경우_empty를_반환한다() {
        DataSource dataSource = mock(DataSource.class);
        Optional<Connection> actual = TransactionSynchronizationManager.findResource(dataSource);
        assertThat(actual).isEmpty();
    }

    @Test
    void 요청된_DataSource에_해당하는_connection이_있는_경우_반환한다() {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        TransactionSynchronizationManager.bindResource(dataSource, connection);

        Optional<Connection> actual = TransactionSynchronizationManager.findResource(dataSource);
        assertThat(actual).isEqualTo(Optional.of(connection));
    }

    @Test
    void 요청된_DataSource로_값을_바인딩한다() {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);

        Connection actual = TransactionSynchronizationManager.bindResource(dataSource, connection);
        assertThat(actual).isEqualTo(connection);
    }

    @Test
    void 이미_바인딩된_DataSource로_바인딩하려는_경우_예외를_던진다() {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        TransactionSynchronizationManager.bindResource(dataSource, connection);

        assertThatThrownBy(() -> TransactionSynchronizationManager.bindResource(dataSource, connection))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 바인딩된 DataSource입니다.");
    }
}
